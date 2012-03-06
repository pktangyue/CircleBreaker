package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball {
	float left;
	float top;
	float yv = -0.5f; // 负数表示向上
	float xv = -0.2f;
	boolean isAlive = false;
	final static float RADIUS = 10;
	private BreakerView view;
	private Paint paint = null;

	public Ball(BreakerView view) {
		this.view = view;
		this.isAlive = true;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
	}

	public void init(Canvas canvas) {
		this.left = view.width / 2;
		this.top = view.baffle.bottom - RADIUS - Baffle.HEIGHT;
		drawSelf(canvas);
	}

	public void drawSelf(Canvas canvas) {
		canvas.drawCircle(left, top, RADIUS, paint);
	}
}
