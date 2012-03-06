package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Baffle {
	float left;
	float bottom;
	final static float WIDTH = 100;
	final static float HEIGHT = 5;
	private BreakerView view;
	private Paint paint = null;

	public Baffle(BreakerView view) {
		this.view = view;
		paint = new Paint();
		paint.setColor(Color.GREEN);
		paint.setAntiAlias(true);
	}

	public void init(Canvas canvas) {
		this.bottom = view.height - 20;
		moveTo(view.width / 2, this.bottom, canvas);
	}

	public void drawSelf(Canvas canvas) {
		left = left < 0 ? 0 : left;
		left = left + WIDTH > view.width ? view.width - WIDTH : left;
		canvas.drawRect(left, bottom - HEIGHT, left + WIDTH, bottom, paint);
	}

	public void moveTo(float x, float y, Canvas canvas) {
		this.left = x - WIDTH / 2;
		drawSelf(canvas);
	}
}
