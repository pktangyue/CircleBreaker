package tangyue.circlebreaker.threads;

import tangyue.circlebreaker.elements.*;
import tangyue.circlebreaker.view.BreakerView;

public class CircleThread extends BaseThread {
	private BreakerView view;
	private Circle circle;
	private float[] ptsV;;
	private float[] pts;
	private final static float PointV = 30.0f;

	public CircleThread(BreakerView view, Circle circle) {
		this.view = view;
		this.circle = circle;
		this.pts = circle.getPts();
		this.flag = true;
		initPointsV();
	}

	public void run() {
		long firstTime = System.currentTimeMillis();
		start = firstTime;
		boolean isLevelComplete = view.checkLevelComplete();
		if (isLevelComplete) {
			GameTime.slowTime();
		}
		while (flag) {
			if (isPause) {
				continue;
			}
			current = System.currentTimeMillis();
			if ((current - firstTime - pauseDuration)
					/ GameTime.getTimeInterval() / 10f > GameTime.getDuration()) {
				view.removeCircle(circle);
				if (isLevelComplete) {
					view.goLevelComplete();
				}
				return;
			}
			float timespan = (float) (current - start)
					/ GameTime.getTimeInterval();
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
