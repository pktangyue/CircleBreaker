package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Baffle implements Drawable {
	final static float WIDTH = 100;
	final static float HEIGHT = 5;

	private float left;
	private float bottom;
	private float v = 0;// 速度
	private BreakerView view;
	private Paint paint = null;

	private static Baffle baffle;

	private Baffle(BreakerView view) {
		this.view = view;
		paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setAntiAlias(true);
	}

	public static Baffle getInstance(BreakerView view) {
		if (baffle == null) {
			return new Baffle(view);
		}
		return baffle;
	}

	@Override
	public void init(Canvas canvas) {
		this.bottom = view.getHeight() - 50;
		this.left = (view.getWidth() - WIDTH) / 2;
		drawSelf(canvas);
	}

	@Override
	public void drawSelf(Canvas canvas) {
		canvas.drawRect(left, bottom - HEIGHT, left + WIDTH, bottom, paint);
	}

	public float getLeft() {
		return left;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getV() {
		return v;
	}

	public void setV(float v) {
		this.v = v;
	}

	public float getBottom() {
		return bottom;
	}
}
