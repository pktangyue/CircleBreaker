package tangyue.circlebreaker.elements;

import tangyue.circlebreaker.R;
import tangyue.circlebreaker.interfaces.Drawable;
import tangyue.circlebreaker.threads.BaseThread;
import tangyue.circlebreaker.view.BreakerView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Message implements Drawable {
	private BreakerView view;
	private Paint paint = null;

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
			paint.setTextAlign(Paint.Align.CENTER);
			Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(),
					R.drawable.your_lost);
			canvas.drawBitmap(bitmap,
					(view.getWidth() - bitmap.getWidth()) / 2,
					view.getHeight() / 3, paint);
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
