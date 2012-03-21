package tangyue.circlebreaker.elements;

import tangyue.circlebreaker.interfaces.Drawable;
import tangyue.circlebreaker.threads.BaseThread;
import tangyue.circlebreaker.view.BreakerView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Message implements Drawable {
	private BreakerView view;
	private Paint paint = null;
	private final String loseMsg = "You Lose!";

	public Message(BreakerView view) {
		this.view = view;
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);
	}

	@Override
	public void init(Canvas canvas) {
	}

	@Override
	public void drawSelf(Canvas canvas) {
		if (view.ball.isLose()) {
			paint.setTextSize(50);
			paint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText(loseMsg, view.getWidth() / 2.0f,
					view.getHeight() / 2.0f, paint);
		}
	}

	@Override
	public BaseThread getThread() {
		return null;
	}

	@Override
	public void disableThread() {
	}
}
