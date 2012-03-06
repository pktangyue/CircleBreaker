package tangyue.circlebreaker;

public class BallThread extends Thread {
	private BreakerView view;
	private Ball ball;
	private Baffle baffle;
	private long current;
	private long start;

	public boolean flag = false;

	public BallThread(BreakerView view) {
		this.view = view;
		this.ball = view.ball;
		this.baffle = view.baffle;
		this.flag = true;
	}

	public void run() {
		start = System.nanoTime();
		while (flag) {
			current = System.nanoTime();
			float timespan = (float) (current - start) / 1000 / 1000;
			if (isLose(timespan)) {
				view.reset();
			}
			ball.top = calculateTop(timespan);
			ball.left = calculateLeft(timespan);
			start = current;
		}
	}

	public float calculateTop(float timespan) {
		float span = timespan * ball.yv;
		float tmpTop = ball.top + span;
		if (tmpTop < Ball.RADIUS) {
			ball.yv = -ball.yv;
			return 2 * Ball.RADIUS - tmpTop;
		} else if (tmpTop + Ball.RADIUS > baffle.bottom) {
			ball.yv = -ball.yv;
			return 2 * baffle.bottom - 2 * Ball.RADIUS - tmpTop;
		}
		return tmpTop;
	}

	public float calculateLeft(float timespan) {
		float span = timespan * ball.xv;
		float tmpLeft = ball.left + span;
		if (tmpLeft - Ball.RADIUS < 0) {
			ball.xv = -ball.xv;
			return 2 * Ball.RADIUS - tmpLeft;
		} else if (tmpLeft + Ball.RADIUS > view.width) {
			ball.xv = -ball.xv;
			return 2 * view.width - 2 * Ball.RADIUS - tmpLeft;
		}
		return tmpLeft;
	}

	public boolean isLose(float timespan) {
		float spanY = timespan * ball.yv;
		float spanX = timespan * ball.xv;
		float tmpTop = ball.top + spanY;
		float tmpLeft = ball.left + spanX;
		if (tmpTop + Ball.RADIUS > baffle.bottom
				&& (tmpLeft + Ball.RADIUS < baffle.left || (tmpLeft
						- Ball.RADIUS > baffle.left + Baffle.WIDTH))) {
			return true;
		}
		return false;
	}
}
