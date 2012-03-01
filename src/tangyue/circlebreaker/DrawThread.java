package tangyue.circlebreaker;

import android.graphics.Canvas;

public class DrawThread extends Thread {
	public BreakerView view;
	public Canvas canvas;
	public boolean flag = false;
	public float prevX = 0;
	public float prevY = 0;
	Baffle baffle;

	public DrawThread(BreakerView view) {
		this.view = view;
		this.flag = true;
		baffle = new Baffle(0, 400);
	}

	public void run() {

		while (flag) {
			synchronized (view.holder) {
				try {
					canvas = view.holder.lockCanvas();
					baffle.moveTo(view.x, view.y, canvas);
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
}
