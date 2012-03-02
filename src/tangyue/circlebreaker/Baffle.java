package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Baffle {
	private float left;
	private float bottom;
	private static float WIDTH = 100;
	private static float HEIGHT = 5;
	private BreakerView view;

	public Baffle(BreakerView view) {
		this.view = view;
	}

	public void init(Canvas canvas, Paint paint) {
		this.bottom = view.height;
		moveTo(view.width / 2, view.height, canvas, paint);
	}

	public void drawSelf(Canvas canvas, Paint paint) {
		left = left < 0 ? 0 : left;
		left = left + WIDTH > view.width ? view.width - WIDTH : left;
		canvas.drawRect(left, bottom - HEIGHT, left + WIDTH, bottom, paint);
	}

	public void moveTo(float x, float y, Canvas canvas, Paint paint) {
		this.left = x - WIDTH / 2;
		drawSelf(canvas, paint);
	}
}
