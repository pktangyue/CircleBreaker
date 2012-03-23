package tangyue.circlebreaker.view;

import java.util.ArrayList;

import tangyue.circlebreaker.elements.*;
import tangyue.circlebreaker.interfaces.*;
import tangyue.circlebreaker.levels.*;
import tangyue.circlebreaker.threads.*;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Debug;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BreakerView extends SurfaceView implements SurfaceHolder.Callback {
	public final static float BOTTOM_BLANK = 140f;
	public Baffle baffle;
	public Ball ball;
	public BreakerSensor sensor;

	private SurfaceHolder holder;
	private Message message;
	private GameScore score;
	private ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	private DrawThread drawThread;
	private Canvas canvas = null;
	private boolean isStart = false;
	private Context context;
	private int level;

	public String fps = "FPS:N/A";

	public BreakerView(Context context, int level) {
		super(context);
		this.sensor = BreakerSensor.getInstance(context);
		this.context = context;
		this.level = level;
		holder = getHolder();
		holder.addCallback(this);
		initCircles();
		initBaffle();
		initBall();
		initMessage();
		initGameScore();
		drawThread = new DrawThread(this);
	}

	private void initBaffle() {
		baffle = Baffle.getInstance(this);
		drawables.add(baffle);
	}

	private void initBall() {
		ball = new Ball(this);
		drawables.add(ball);
	}

	private void initCircles() {
		try {
			GameLevel gamelevel = LevelFactory.creator(level);
			drawables.addAll(gamelevel.getCirclesList(this));
		} catch (NullPointerException ex) {
			((GameActivity) context).startMenu();
			((GameActivity) context).finish();
		}
	}

	private void initMessage() {
		message = new Message(this);
		drawables.add(message);
	}

	private void initGameScore() {
		score = new GameScore(this);
		drawables.add(score);
	}

	private void destoryBall() {
		drawables.remove(ball);
		ball = null;
	}

	public void reset() {
		isStart = false;
		baffle.reset();
		destoryBall();
		initBall();
		GameTime.resetInterval();
		GameScore.resetIndex();
	}

	public void pause() {
		for (int i = drawables.size() - 1; i >= 0; i--) {
			try {
				drawables.get(i).getThread().pauseThread();
			} catch (NullPointerException e) {
				continue;
			}
		}
		drawThread.pauseThread();
	}

	public void resume() {
		for (int i = drawables.size() - 1; i >= 0; i--) {
			try {
				drawables.get(i).getThread().resumeThread();
			} catch (NullPointerException e) {
				continue;
			}
		}
		drawThread.resumeThread();
	}

	public void doDraw() {
		canvas = holder.lockCanvas();
		// 清空canvas
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		for (int i = 0; i < drawables.size(); i++) {
			Drawable drawable = drawables.get(i);
			if (drawable == null) {
				continue;
			}
			if (!isStart) {
				drawable.init(canvas);
			} else {
				drawable.drawSelf(canvas);
			}
		}
		printFPS(canvas);
		if (canvas != null) {
			holder.unlockCanvasAndPost(canvas);
		}
	}

	// 检查球和圆圈的碰撞
	public void checkEliminate(float x, float y) {
		for (int i = 0; i < drawables.size(); i++) {
			Drawable drawable = drawables.get(i);
			if (drawable != null && drawable instanceof Circle) {
				Circle circle = (Circle) drawable;
				if (circle.isEliminated()) {
					continue;
				}
				float deltaX = circle.getX() - x;
				float deltaY = circle.getY() - y;
				float span = Ball.RADIUS + circle.getRadius();
				if (deltaX * deltaX + deltaY * deltaY <= span * span) {
					circle.breakout();
					circle.getThread().start();
					break;// 发生一次碰撞就退出(不可能同时碰到2个)
				}
			}
		}
	}

	public void removeCircle(Circle circle) {
		drawables.set(drawables.indexOf(circle), null);// 设为null,而不是删除，避免多线程的问题
	}

	public boolean checkLevelComplete() {
		int size = drawables.size();
		for (int i = size - 1; i >= 0; i--) {
			Drawable drawable = drawables.get(i);
			if (drawable != null && drawable instanceof Circle
					&& ((Circle) drawable).isEliminated() == false) {
				return false;
			}
		}
		return true;
	}

	public void goLevelComplete() {
		((GameActivity) context).startLevelComplete(GameScore.getTotalScore(),
				level);
	}

	public void goFailDialog() {
		((GameActivity) context).startFail(level);
		((GameActivity) context).finish();
	}

	public void printFPS(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		Object[] arr = { fps, sensor.ratioY, GameScore.getTotalScore() };
		for (int i = 0; i < arr.length; i++) {
			canvas.drawText(arr[i].toString(), 30, 30 * (i + 1), paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			isStart = true;
			ball.getThread().startThread();
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		drawThread.startThread();
		baffle.getThread().startThread();
		Debug.startMethodTracing("yourActivityTrace");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		drawThread.flag = false;
		drawThread = null;
		for (int i = drawables.size() - 1; i >= 0; i--) {
			try {
				drawables.get(i).disableThread();
			} catch (NullPointerException e) {
				continue;
			}
		}
		GameTime.resetInterval();
		GameScore.resetAll();
		Debug.stopMethodTracing();
	}

}
