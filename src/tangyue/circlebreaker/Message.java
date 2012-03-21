package tangyue.circlebreaker;

import tangyue.circlebreaker.interfaces.Drawable;
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
		if (view.getBall().isLose()) {
			paint.setTextSize(50);
			paint.setTextAlign(Paint.Align.CENTER);
			canvas.drawText(loseMsg, view.getWidth() / 2.0f,
					view.getHeight() / 2.0f, paint);
		}
	}
}
