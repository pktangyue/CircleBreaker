package tangyue.circlebreaker;

public class CircleThread extends Thread {
	private BreakerView view;
	private Circle circle;
	private long current;
	private long start;
	private float[] ptsV;;
	private float[] pts;
	private final static float DURATION = 0.5f;
	private final static float PointV = 30.0f;

	public boolean flag = false;

	public CircleThread(BreakerView view, Circle circle) {
		this.view = view;
		this.circle = circle;
		this.pts = circle.getPts();
		this.flag = true;
		initPointsV();
	}

	public void run() {
		long firstTime = start = System.currentTimeMillis();
		while (flag) {
			current = System.currentTimeMillis();
			if ((current - firstTime) / 1000.0f > DURATION) {
				view.removeCircle(circle, this);
				return;
			}
			float timespan = (float) (current - start) / 100.0f;
			movePoints(timespan);
			changeTextSize(timespan);
			start = current;
		}
	}

	public void initPointsV() {
		ptsV = new float[pts.length];
		for (int i = 0; i < ptsV.length; i++) {
			ptsV[i] = (int) (Math.random() * PointV * 2.0f - PointV);
		}
	}

	public void movePoints(float timespan) {
		for (int i = 0; i < pts.length; i++) {
			pts[i] = pts[i] + timespan * ptsV[i];
		}
	}

	public void changeTextSize(float timespan) {
		circle.setTextSize(circle.getTextSize() + timespan * 3.0f);
	}
}
