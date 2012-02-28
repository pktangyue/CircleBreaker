package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Baffle {
	private float left;
	private float bottom;
	private static int WIDTH = 20;
	private static int HEIGHT = 10;

	public Baffle(float left, float bottom) {
		this.left = left;
		this.bottom = bottom;
	}

	public void init(Canvas canvas) {
		drawSelf(canvas);
	}

	public void drawSelf(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		canvas.drawRect(left, bottom - HEIGHT, left + WIDTH, bottom, paint);
	}

	public void moveTo(float x, float y, Canvas canvas) {
		this.left = x ;//- WIDTH / 2;
		drawSelf(canvas);
		Log.v("xxxxxxxxxx", "" + x);
		Log.v("yyyyyyyyyy", "" + y);
	}
}
