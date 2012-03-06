package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Circle {
	float x;
	float y;
	float radius;

	private Paint paint;

	public Circle(float x, float y, float radius, int color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		paint.setColor(color);
	}

	public void drawSelf(Canvas canvas) {
		canvas.drawCircle(x, y, radius, paint);
	}
}
