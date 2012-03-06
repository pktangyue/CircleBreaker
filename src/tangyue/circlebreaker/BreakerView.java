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
	SurfaceHolder holder;
	int width;
	int height;
	String fps = "FPS:N/A";
	Baffle baffle;
	Ball ball;
	ArrayList<Circle> circles = new ArrayList<Circle>();

	private DrawThread drawThread;
	private BallThread ballThread;
	private float x;
	private float y;
	private Canvas canvas = null;
	private boolean isStart = false;

	public BreakerView(Context context, int width, int height) {
		super(context);
		// TODO Auto-generated constructor stub
		this.width = width;
		this.height = height;
		holder = getHolder();
		holder.addCallback(this);
		drawThread = new DrawThread(this);
		initBaffle();
		initBall();
		initCircle();
	}

	private void initBaffle() {
		baffle = new Baffle(this);
	}

	private void initBall() {
		ball = new Ball(this);
		ballThread = new BallThread(this);
	}

	private void initCircle() {
		circles.add(new Circle(50, 420, 30, Color.RED));
		circles.add(new Circle(145, 350, 30, Color.RED));
		circles.add(new Circle(240, 300, 30, Color.RED));
		circles.add(new Circle(335, 350, 30, Color.RED));
		circles.add(new Circle(430, 420, 30, Color.RED));
	}

	private void destoryBall() {
		ball = null;
		ballThread.flag = false;
		ballThread = null;
	}

	public void reset() {
		isStart = false;
		destoryBall();
		initBall();
	}

	public void doDraw() {
		canvas = holder.lockCanvas();
		// 清空canvas
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		if (!isStart) {
			baffle.init(canvas);
			ball.init(canvas);
		} else {
			baffle.moveTo(x, y, canvas);
			ball.drawSelf(canvas);
		}
		for (Circle circle : circles) {
			circle.drawSelf(canvas);
		}
		printFPS(canvas);
		if (canvas != null) {
			holder.unlockCanvasAndPost(canvas);
		}
	}

	public void printFPS(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		canvas.drawText(fps, 30, 30, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		x = event.getX();
		y = event.getY();
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
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (!drawThread.isAlive()) {
			drawThread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		ballThread.flag = drawThread.flag = false;
		ballThread = null;
		drawThread = null;
	}

}
