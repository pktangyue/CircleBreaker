package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Baffle implements Drawable{
	float left;
	float bottom;
	float v = 0;// 速度
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

	@Override
	public void init(Canvas canvas) {
		this.bottom = view.height - 50;
		this.left = (view.width - WIDTH) / 2;
		drawSelf(canvas);
	}

	@Override
	public void drawSelf(Canvas canvas) {
		canvas.drawRect(left, bottom - HEIGHT, left + WIDTH, bottom, paint);
	}
}
