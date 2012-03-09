package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball implements Drawable {
	final static float RADIUS = 10;

	private BreakerView view;
	private Paint paint = null;
	private float x;
	private float y;
	private float vy = -50f; // 负数表示向上
	private float vx = 0;
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
		this.x = view.getWidth() / 2;
		this.y = view.getBaffle().getBottom() - RADIUS - Baffle.HEIGHT;
		drawSelf(canvas);
	}

	@Override
	public void drawSelf(Canvas canvas) {
		canvas.drawCircle(x, y, RADIUS, paint);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getVY() {
		return vy;
	}

	public void setVY(float vy) {
		this.vy = vy;
	}

	public void reverseVY() {
		this.vy = -vy;
	}

	public float getVX() {
		return vx;
	}

	public void setVX(float vx) {
		this.vx = vx;
	}

	public void reverseVX() {
		this.vx = -vx;
	}

	public boolean isLose() {
		return isLose;
	}

	public void setLose(boolean isLose) {
		this.isLose = isLose;
	}
}
