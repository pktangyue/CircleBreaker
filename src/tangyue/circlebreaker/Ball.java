package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball implements Drawable {
	final static float RADIUS = 10;
	final static float BASE_VY = -30f;// 负数表示向上
	final static float G = 3.0f;

	private BreakerView view;
	private Paint paint = null;
	private float x;
	private float y;
	private float vx = 0f;
	private float vy = BASE_VY;
	private boolean isLose = false;
	private static Ball ball;

	public Ball(BreakerView view) {
		this.view = view;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
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
