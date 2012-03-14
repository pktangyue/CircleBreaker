package tangyue.circlebreaker;

public class CircleThread extends Thread {
	private BreakerView view;
	private Circle circle;
	private long current;
	private long start;
	private float[] ptsV;;
	private final static float DURATION = 0.8f;
	private final static float PointV = 20;

	public boolean flag = false;

	public CircleThread(BreakerView view, Circle circle) {
		this.view = view;
		this.circle = circle;
		this.flag = true;
		initPointsV();
	}

	public void run() {
		long firstTime = start = System.nanoTime();
		while (flag) {
			current = System.nanoTime();
			if ((current - firstTime) / 1000 / 1000 / 1000 > DURATION) {
				view.removeCircle(circle, this);
			}
			float timespan = (float) (current - start) / 1000 / 1000 / 100;
			movePoints(timespan);
			changeTextSize(timespan);
			start = current;
		}
	}

	public void initPointsV() {
		ptsV = new float[circle.getPts().length];
		for (int i = 0; i < ptsV.length; i++) {
			ptsV[i] = (int) (Math.random() * PointV * 2 - PointV);
		}
	}

	public void movePoints(float timespan) {
		for (int i = 0; i < circle.getPts().length; i++) {
			circle.getPts()[i] = circle.getPts()[i] + timespan * ptsV[i];
		}
	}

	public void changeTextSize(float timespan) {
		circle.setTextSize(circle.getTextSize() + timespan * 2);
	}
}
