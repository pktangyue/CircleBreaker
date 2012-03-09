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
		this.baffle = view.getBaffle();
		this.sensor = view.getSensor();
		this.flag = true;
	}

	public void run() {
		start = System.nanoTime();
		while (flag) {
			current = System.nanoTime();
			float timespan = (float) (current - start) / 1000 / 1000 / 100;
			calculateV();
			calculateLeft(timespan);
			start = current;
		}
	}

	public void calculateV() {
		if (sensor == null) {
			baffle.setV(0);
			return;
		}
		baffle.setV(sensor.ratioX * 30);
	}

	public void calculateLeft(float timespan) {
		float tmpLeft = baffle.getLeft() + timespan * baffle.getV();
		if (tmpLeft < 0) {
			tmpLeft = 0;
		}
		if (tmpLeft > view.getWidth() - Baffle.WIDTH) {
			tmpLeft = view.getWidth() - Baffle.WIDTH;
		}
		baffle.setLeft(tmpLeft);
	}
}
