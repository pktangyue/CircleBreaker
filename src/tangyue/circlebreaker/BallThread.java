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
		this.ball = view.getBall();
		this.baffle = view.getBaffle();
		this.flag = true;
	}

	public void run() {
		start = System.currentTimeMillis();
		while (flag) {
			current = System.currentTimeMillis();
			float timespan = (float) (current - start) / 100.0f;
			checkLose(timespan);
			if (ball.isLose() && ball.getY() > 1500.0f) {
				view.reset();
			}
			calculateY(timespan);
			calculateX(timespan);
			view.checkEliminate(ball.getX(), ball.getY());
			start = current;
		}
	}

	public void calculateY(float timespan) {
		float span = timespan * ball.getVY();
		float tmpTop = ball.getY() + span;
		if (tmpTop < Ball.RADIUS) {
			ball.reverseVY();
			tmpTop = 2.0f * Ball.RADIUS - tmpTop;
		} else if (tmpTop + Ball.RADIUS > baffle.getBottom() && !ball.isLose()) {
			calculateVY();
			calculateXV(timespan);
			tmpTop = 2.0f * baffle.getBottom() - 2.0f * Ball.RADIUS - tmpTop;
		}
		ball.setY(tmpTop);
	}

	public void calculateX(float timespan) {
		float span = timespan * ball.getVX();
		float tmpLeft = ball.getX() + span;
		if (tmpLeft - Ball.RADIUS < 0.0f) {
			ball.reverseVX();
			tmpLeft = 2.0f * Ball.RADIUS - tmpLeft;
		} else if (tmpLeft + Ball.RADIUS > view.getWidth()) {
			ball.reverseVX();
			tmpLeft = 2.0f * view.getWidth() - 2.0f * Ball.RADIUS - tmpLeft;
		}
		ball.setX(tmpLeft);
	}

	public void calculateXV(float timespan) {
		float deltaT = (baffle.getBottom() - ball.getY() - Ball.RADIUS)
				/ ball.getVX();
		if (Float.isNaN(deltaT) || Float.isInfinite(deltaT)) {
			deltaT = 0.0f;
		}
		float tmpLeft = ball.getX() + deltaT * ball.getVX();
		ball.setVX(tmpLeft - baffle.getLeft() - Baffle.WIDTH / 2.0f);
	}

	public void calculateVY() {
		ball.reverseVY();
	}

	public void checkLose(float timespan) {
		if (ball.isLose()) {
			ball.setLose(true);// 已经丢失就不再检查
			return;
		}
		float spanY = timespan * ball.getVY();
		float spanX = timespan * ball.getVX();
		float tmpTop = ball.getY() + spanY;
		float tmpLeft = ball.getX() + spanX;
		if (tmpTop + Ball.RADIUS > baffle.getBottom()
				&& (tmpLeft + Ball.RADIUS < baffle.getLeft() || (tmpLeft
						- Ball.RADIUS > baffle.getLeft() + Baffle.WIDTH))) {
			ball.setLose(true);
			return;
		}
		ball.setLose(false);
	}
}
