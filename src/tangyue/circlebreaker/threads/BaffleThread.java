package tangyue.circlebreaker.threads;

import tangyue.circlebreaker.elements.*;
import tangyue.circlebreaker.view.BreakerView;

public class BaffleThread extends BaseThread {
	private BreakerView view;
	private Baffle baffle;

	public BaffleThread(BreakerView view, Baffle baffle) {
		this.view = view;
		this.baffle = baffle;
		this.flag = true;
	}

	public void run() {
		start = System.currentTimeMillis();
		while (flag) {
			if (isPause) {
				continue;
			}
			current = System.currentTimeMillis();
			float timespan = (current - start) / GameTime.getTimeInterval();
			calculateV();
			calculateLeft(timespan);
			start = current;
		}
	}

	public void calculateV() {
		if (view.sensor == null) {
			baffle.setV(0.0f);
			return;
		}
		baffle.setV(view.sensor.ratioX * 30.0f);
	}

	public void calculateLeft(float timespan) {
		float tmpLeft = baffle.getLeft() + timespan * baffle.getV();
		if (tmpLeft < 0.0f) {
			tmpLeft = 0.0f;
		} else if (tmpLeft > view.getWidth() - Baffle.WIDTH) {
			tmpLeft = view.getWidth() - Baffle.WIDTH;
		}
		baffle.setLeft(tmpLeft);
	}
}
