package tangyue.circlebreaker.elements;

import tangyue.circlebreaker.interfaces.Drawable;
import tangyue.circlebreaker.threads.BaffleThread;
import tangyue.circlebreaker.threads.BaseThread;
import tangyue.circlebreaker.view.BreakerView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Baffle implements Drawable {
	public final static float WIDTH = 140.0f;
	public final static float HEIGHT = 5.0f;

	private float left;
	private float bottom;
	private float v = 0.0f;// 速度
	private BreakerView view;
	private Paint paint = null;
	private BaffleThread thread;

	private static Baffle baffle;

	private Baffle(BreakerView view) {
		this.view = view;
		this.thread = new BaffleThread(view, this);
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
		if (this.bottom == 0.0f) {
			reset();
		}
		drawSelf(canvas);
	}

	@Override
	public void drawSelf(Canvas canvas) {
		canvas.drawRect(left, bottom - HEIGHT, left + WIDTH, bottom, paint);
	}

	public void reset() {
		this.bottom = view.getHeight() - BreakerView.BOTTOM_BLANK;
		this.left = (view.getWidth() - WIDTH) / 2.0f;
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

	public final float getLeft() {
		return left;
	}

	public final void setLeft(float left) {
		this.left = left;
	}

	public final float getV() {
		return v;
	}

	public final void setV(float v) {
		this.v = v;
	}

	public final float getBottom() {
		return bottom;
	}
}