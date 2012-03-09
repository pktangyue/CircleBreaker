package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball {
	float x;
	float y;
	float yv = -50f; // 负数表示向上
	float xv = 0;
	boolean isLose = false;
	final static float RADIUS = 10;
	private BreakerView view;
	private Paint paint = null;

	public Ball(BreakerView view) {
		this.view = view;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
	}

	public void init(Canvas canvas) {
		this.x = view.width / 2;
		this.y = view.baffle.bottom - RADIUS - Baffle.HEIGHT;
		drawSelf(canvas);
	}

	public void drawSelf(Canvas canvas) {
		canvas.drawCircle(x, y, RADIUS, paint);
	}
}
