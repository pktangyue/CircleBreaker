package tangyue.circlebreaker;

public class BaffleThread extends Thread {
	private BreakerView view;
	private Baffle baffle;
	private BreakerSensor sensor;
	private long current;
	private long start;

	public boolean flag = false;

	public BaffleThread(BreakerView view) {
		this.view = view;
		this.baffle = view.baffle;
		this.sensor = view.sensor;
		this.flag = true;
	}

	public void run() {
		start = System.nanoTime();
		while (flag) {
			current = System.nanoTime();
			float timespan = (float) (current - start) / 1000 / 1000 / 100;
			baffle.v = calculateV();
			baffle.left = calculateLeft(timespan);
			start = current;
		}
	}

	public float calculateV() {
		if (sensor == null)
			return 0;
		return sensor.ratioX * 30;
	}

	public float calculateLeft(float timespan) {
		float tmpLeft = baffle.left + timespan * baffle.v;
		if (tmpLeft < 0) {
			return 0;
		}
		if (tmpLeft > view.width - Baffle.WIDTH) {
			return view.width - Baffle.WIDTH;
		}
		return tmpLeft;
	}
}
