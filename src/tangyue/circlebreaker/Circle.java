package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Circle implements Drawable {
	private float x;
	private float y;
	private float radius;
	private Paint paint;
	private Paint textPaint;
	private boolean isEliminated = false;
	private float textSize = 10;
	private float[] pts = new float[40];// 20个点

	public Circle(float x, float y, float radius, int color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		for (int i = 0; i < pts.length; i += 2) {
			pts[i] = x;
			pts[i + 1] = y;
		}
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		paint.setColor(color);
		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setColor(color);
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

	public boolean isEliminated() {
		return isEliminated;
	}

	public float[] getPts() {
		return pts;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	@Override
	public void drawSelf(Canvas canvas) {
		if (isEliminated) {
			canvas.drawPoints(pts, paint);
			textPaint.setTextSize(textSize);
			canvas.drawText("50", x, y, textPaint);
		} else {
			canvas.drawCircle(x, y, radius, paint);
		}
	}

	@Override
	public void init(Canvas canvas) {
		drawSelf(canvas);
	}

	public void breakout() {
		isEliminated = true;
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(2);
	}

}
