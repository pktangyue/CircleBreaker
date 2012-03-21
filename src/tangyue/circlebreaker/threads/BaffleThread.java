package tangyue.circlebreaker.threads;

import tangyue.circlebreaker.Baffle;
import tangyue.circlebreaker.BreakerSensor;
import tangyue.circlebreaker.BreakerView;
import tangyue.circlebreaker.GameTime;

public class BaffleThread extends BaseThread {
	private BreakerView view;
	private Baffle baffle;
	private BreakerSensor sensor;

	public BaffleThread(BreakerView view) {
		this.view = view;
		this.baffle = view.getBaffle();
		this.sensor = view.getSensor();
		this.flag = true;
	}

	public void run() {
		start = System.currentTimeMillis();
		while (flag) {
			current = System.currentTimeMillis();
			float timespan = (current - start) / GameTime.getTimeInterval();
			calculateV();
			calculateLeft(timespan);
			start = current;
		}
	}

	public void calculateV() {
		if (sensor == null) {
			baffle.setV(0.0f);
			return;
		}
		baffle.setV(sensor.ratioX * 30.0f);
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