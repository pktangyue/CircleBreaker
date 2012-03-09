package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Circle implements Drawable {
	private float x;
	private float y;
	private float radius;
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

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getRadius() {
		return radius;
	}

	@Override
	public void drawSelf(Canvas canvas) {
		canvas.drawCircle(x, y, radius, paint);
	}

	@Override
	public void init(Canvas canvas) {
		drawSelf(canvas);
	}

}
