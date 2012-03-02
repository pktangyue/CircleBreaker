package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DrawThread extends Thread {
	public BreakerView view;
	public Canvas canvas;
	public boolean flag = false;
	public float prevX = 0;
	public float prevY = 0;
	private Paint paint = null;
	Baffle baffle;

	public DrawThread(BreakerView view) {
		this.view = view;
		this.flag = true;
		paint = new Paint();
		paint.setColor(Color.WHITE);
		baffle = new Baffle(view);
	}

	public void run() {
		while (flag) {
			synchronized (view.holder) {
				canvas = view.holder.lockCanvas();
				boolean isInit = view.x == 0 && view.y == 0 && prevX == 0
						&& prevY == 0;
				if (prevX != view.x || prevY != view.y || !isInit) {
					prevX = view.x;
					prevY = view.y;
					baffle.moveTo(view.x, view.y, canvas, paint);
				} else if (isInit) {
					baffle.init(canvas, paint);
				}
				if (canvas != null) {
					view.holder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
