package tangyue.circlebreaker;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BreakerView extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder holder;
	private Baffle baffle;
	private Ball ball;
	private BreakerSensor sensor;
	private Message message;
	private ArrayList<Drawable> drawables = new ArrayList<Drawable>();
	private DrawThread drawThread;
	private BallThread ballThread;
	private BaffleThread baffleThread;
	private Canvas canvas = null;
	private boolean isStart = false;

	String fps = "FPS:N/A";

	public BreakerView(Context context) {
		super(context);
		this.sensor = BreakerSensor.getInstance(context);
		holder = getHolder();
		holder.addCallback(this);
		initCircles();
		initBaffle();
		initBall();
		initMessage();
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
		drawables.add(new Circle(50, 420, 30, Color.RED));
		drawables.add(new Circle(145, 350, 30, Color.RED));
		drawables.add(new Circle(240, 300, 30, Color.RED));
		drawables.add(new Circle(335, 350, 30, Color.RED));
		drawables.add(new Circle(430, 420, 30, Color.RED));
		drawables.add(new Circle(50, 520, 30, Color.RED));
		drawables.add(new Circle(145, 450, 30, Color.RED));
		drawables.add(new Circle(240, 400, 30, Color.RED));
		drawables.add(new Circle(335, 450, 30, Color.RED));
		drawables.add(new Circle(430, 520, 30, Color.RED));
	}

	private void initMessage() {
		message = new Message(this);
		drawables.add(message);
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
	}

	public void doDraw() {
		canvas = holder.lockCanvas();
		// 清空canvas
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		try {
			synchronized (drawables) {
				for (int i = 0; i < drawables.size(); i++) {
					Drawable drawable = drawables.get(i);
					if (!isStart) {
						drawable.init(canvas);
					} else {
						drawable.drawSelf(canvas);
					}
				}
			}
		} catch (IndexOutOfBoundsException ec) {
		}
		printFPS(canvas);
		if (canvas != null) {
			holder.unlockCanvasAndPost(canvas);
		}
	}

	// 检查球和圆圈的碰撞
	public void checkEliminate(float x, float y) {
		synchronized (drawables) {
			try {
				for (int i = 0; i < drawables.size(); i++) {
					Drawable drawable = drawables.get(i);
					if (drawable instanceof Circle) {
						Circle circle = (Circle) drawable;
						if (circle.isEliminated()) {
							continue;
						}
						if (Math.pow(circle.getX() - x, 2)
								+ Math.pow(circle.getY() - y, 2) <= Math.pow(
								Ball.RADIUS + circle.getRadius(), 2)) {
							circle.breakout();
							CircleThread circleThread = new CircleThread(this,
									circle);
							circleThread.start();
							break;// 发生一次碰撞就退出(不可能同时碰到2个)
						}
					}
				}
			} catch (IndexOutOfBoundsException ex) {
			}
		}
	}

	public void removeCircle(Circle circle, CircleThread thread) {
		thread.flag = false;
		thread = null;
		drawables.remove(circle);
	}

	public void printFPS(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		Object[] arr = { fps, ball.getX(), ball.getY(), ball.getVX(),
				ball.getVY(), ball.isLose() };
		for (int i = 0; i < arr.length; i++) {
			canvas.drawText(arr[i].toString(), 30, 30 * (i + 1), paint);
		}
	}

	public Baffle getBaffle() {
		return baffle;
	}

	public Ball getBall() {
		return ball;
	}

	public BreakerSensor getSensor() {
		return sensor;
	}

	public ArrayList<Drawable> getDrawables() {
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
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		baffleThread.flag = ballThread.flag = drawThread.flag = false;
		ballThread = null;
		drawThread = null;
		baffleThread = null;
	}

}
