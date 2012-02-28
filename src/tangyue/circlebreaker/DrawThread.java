package tangyue.circlebreaker;

import android.graphics.Canvas;

public class DrawThread extends Thread {
	public BreakerView view;
	public Canvas canvas;
	public boolean flag = false;

	public DrawThread(BreakerView view) {
		this.view = view;
		this.flag = true;
	}

	public void run() {
		Baffle baffle = new Baffle(0, 400);

		while (flag) {
			try {
				canvas = view.holder.lockCanvas();
				synchronized (view.holder) {
					baffle.moveTo(view.x, view.y, canvas);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					view.holder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
