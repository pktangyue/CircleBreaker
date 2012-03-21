package tangyue.circlebreaker.elements;

import tangyue.circlebreaker.interfaces.Drawable;
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
	private float vx = 5f;
	private float vy = BASE_VY;
	private boolean isLose = false;
	private static Ball ball;
	private float[] pathPoints;

	public Ball(BreakerView view) {
		this.view = view;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(2);
	}

	public static Ball getInstance(BreakerView view) {
		if (ball == null) {
			return new Ball(view);
		}
		return ball;
	}

	@Override
	public void init(Canvas canvas) {
		this.x = view.getBaffle().getLeft() + Baffle.WIDTH / 2;
		this.y = view.getBaffle().getBottom() - RADIUS - Baffle.HEIGHT;
		drawSelf(canvas);
	}

	@Override
	public void drawSelf(Canvas canvas) {
		canvas.drawCircle(x, y, RADIUS, paint);
		if (pathPoints != null) {
			canvas.drawPoints(pathPoints, paint);
		}
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
