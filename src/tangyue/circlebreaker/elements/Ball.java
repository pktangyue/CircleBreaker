package tangyue.circlebreaker.elements;

import tangyue.circlebreaker.interfaces.Drawable;
import tangyue.circlebreaker.threads.BallThread;
import tangyue.circlebreaker.threads.BaseThread;
import tangyue.circlebreaker.view.BreakerView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball implements Drawable {
	public final static float RADIUS = 10;
	public final static float BASE_VY = -30f;// 负数表示向上
	public final static float G = 3.0f;

	private BreakerView view;
	private Paint paint = null;
	private float x;
	private float y;
	private float vx = 0f;
	private float vy = BASE_VY - 15f;
	private boolean isLose = false;
	private float[] pathPoints;
	private BallThread thread;

	public Ball(BreakerView view) {
		this.view = view;
		this.thread = new BallThread(view, this);
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(2);
	}

	@Override
	public void init(Canvas canvas) {
		this.x = view.baffle.getLeft() + Baffle.WIDTH / 2;
		this.y = view.baffle.getBottom() - RADIUS - Baffle.HEIGHT;
		drawSelf(canvas);
	}

	@Override
	public void drawSelf(Canvas canvas) {
		canvas.drawCircle(x, y, RADIUS, paint);
		if (pathPoints != null) {
			canvas.drawPoints(pathPoints, paint);
		}
	}

	@Override
	public BaseThread getThread() {
		return thread;
	}

	@Override
	public void disableThread() {
		thread.flag = false;
		thread = null;
	}

	public void setPathPoints(float[] pathPoints) {
		this.pathPoints = pathPoints;
	}

	public final float getX() {
		return x;
	}

	public final void setX(float x) {
		this.x = x;
	}

	public final float getY() {
		return y;
	}

	public final void setY(float y) {
		this.y = y;
	}

	public final float getVY() {
		return vy;
	}

	public final void setVY(float vy) {
		this.vy = vy;
	}

	public final void reverseVY() {
		this.vy = -vy;
	}

	public final float getVX() {
		return vx;
	}

	public final void setVX(float vx) {
		this.vx = vx;
	}

	public final void reverseVX() {
		this.vx = -vx;
	}

	public final boolean isLose() {
		return isLose;
	}

	public final void setLose(boolean isLose) {
		this.isLose = isLose;
	}
}
