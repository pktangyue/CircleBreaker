package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Ball {
	float left;
	float top;
	long time;
	float v = -0.5f; //负数表示向上
	private static float RADIUS = 10;
	private BreakerView view;
	private BallThread thread;

	public Ball(BreakerView view) {
		this.view = view;
		thread = new BallThread(this, view.width, view.height);
	}

	public void init(Canvas canvas, Paint paint) {
		this.left = view.width / 2;
		this.top = view.height - 30;
		drawSelf(canvas, paint);
		this.startMove();
	}

	public void drawSelf(Canvas canvas, Paint paint) {
		canvas.drawCircle(left, top, RADIUS, paint);
	}

	public void startMove() {
		time = System.nanoTime();
		if (!thread.isAlive()) {
			thread.start();
		}
	}
}
