package tangyue.circlebreaker.view;

import java.util.ArrayList;

import tangyue.circlebreaker.elements.*;
import tangyue.circlebreaker.interfaces.*;
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

	private SurfaceHolder holder;
	private Baffle baffle;
	private Ball ball;
	private BreakerSensor sensor;
	private Message message;
	private GameScore score;
	private ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	private DrawThread drawThread;
	private BallThread ballThread;
	private BaffleThread baffleThread;
	private Canvas canvas = null;
	private boolean isStart = false;
	private Context context;

	public String fps = "FPS:N/A";

	public BreakerView(Context context) {
		super(context);
		this.sensor = BreakerSensor.getInstance(context);
		this.context = context;
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
		baffleThread = new BaffleThread(this);
		drawables.add(baffle);
	}

	private void initBall() {
		ball = Ball.getInstance(this);
		ballThread = new BallThread(this);
		drawables.add(ball);
	}

	private void initCircles() {
		drawables.add(new Circle(50, 420, 30, Color.BLUE));
		drawables.add(new Circle(145, 350, 30, Color.BLUE));
		drawables.add(new Circle(240, 300, 30, Color.BLUE));
		drawables.add(new Circle(335, 350, 30, Color.BLUE));
		drawables.add(new Circle(430, 420, 30, Color.BLUE));

		drawables.add(new Circle(50, 520, 30, Color.RED));
		drawables.add(new Circle(145, 450, 30, Color.RED));
		drawables.add(new Circle(240, 400, 30, Color.RED));
		drawables.add(new Circle(335, 450, 30, Color.RED));
		drawables.add(new Circle(430, 520, 30, Color.RED));

		drawables.add(new Circle(50, 320, 30, Color.GREEN));
		drawables.add(new Circle(145, 250, 30, Color.GREEN));
		drawables.add(new Circle(240, 200, 30, Color.GREEN));
		drawables.add(new Circle(335, 250, 30, Color.GREEN));
		drawables.add(new Circle(430, 320, 30, Color.GREEN));
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
		ballThread.flag = false;
		ballThread = null;
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
	}

	public void resume() {
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
					CircleThread circleThread = new CircleThread(this, circle);
					circleThread.start();
					break;// 发生一次碰撞就退出(不可能同时碰到2个)
				}
			}
		}
	}

	public void removeCircle(Circle circle, CircleThread thread) {
		thread.flag = false;
		thread = null;
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
		((GameActivity) context).startLevelComplete(GameScore.getTotalScore());
	}

	public void printFPS(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		Object[] arr = { fps, ball.getVY(), sensor.ratioY,
				GameScore.getTotalScore() };
		for (int i = 0; i < arr.length; i++) {
			canvas.drawText(arr[i].toString(), 30, 30 * (i + 1), paint);
		}
	}

	public final Baffle getBaffle() {
		return baffle;
	}

	public final Ball getBall() {
		return ball;
	}

	public final BreakerSensor getSensor() {
		return sensor;
	}

	public final ArrayList<Drawable> getDrawables() {
		return drawables;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			isStart = true;
			if (!ballThread.isAlive()) {
				ballThread.start();
			}
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
		if (!drawThread.isAlive()) {
			drawThread.start();
		}
		if (!baffleThread.isAlive()) {
			baffleThread.start();
		}
		Debug.startMethodTracing("yourActivityTrace");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		baffleThread.flag = ballThread.flag = drawThread.flag = false;
		ballThread = null;
		drawThread = null;
		baffleThread = null;
		GameTime.resetInterval();
		GameScore.resetAll();
		Debug.stopMethodTracing();
	}

}
