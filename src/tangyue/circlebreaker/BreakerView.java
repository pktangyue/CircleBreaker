package tangyue.circlebreaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BreakerView extends SurfaceView implements SurfaceHolder.Callback {
	public SurfaceHolder holder;
	public int width;
	public int height;
	public String fps="FPS:N/A";

	private DrawThread thread;
	private float x;
	private float y;
	private float prevX = 0;
	private float prevY = 0;
	private Canvas canvas = null;
	private Paint paint = null;
	private boolean isStart = false;
	private Baffle baffle;
	private Ball ball;

	public BreakerView(Context context, int width, int height) {
		super(context);
		// TODO Auto-generated constructor stub
		this.width = width;
		this.height = height;
		holder = getHolder();
		holder.addCallback(this);
		baffle = new Baffle(this);
		ball = new Ball(this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		thread = new DrawThread(this);
	}

	public void doDraw() {
		canvas = holder.lockCanvas();
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		if (prevX != x || prevY != y || isStart) {
			prevX = x;
			prevY = y;
			baffle.moveTo(x, y, canvas, paint);
			ball.drawSelf(canvas, paint);
		} else if (!isStart) {
			baffle.init(canvas, paint);
			ball.init(canvas, paint);
		}
		canvas.drawText(fps, 30, 30, paint);
		if (canvas != null) {
			holder.unlockCanvasAndPost(canvas);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		x = event.getX();
		y = event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			isStart = true;
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
		if (!thread.isAlive()) {
			thread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread.flag = false;
		thread = null;
	}

}
