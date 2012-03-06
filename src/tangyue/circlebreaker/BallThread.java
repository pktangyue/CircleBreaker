package tangyue.circlebreaker;

import java.util.ArrayList;

public class BallThread extends Thread {
	private BreakerView view;
	private Ball ball;
	private Baffle baffle;
	private ArrayList<Circle> circles;
	private long current;
	private long start;

	public boolean flag = false;

	public BallThread(BreakerView view) {
		this.view = view;
		this.ball = view.ball;
		this.baffle = view.baffle;
		this.circles = view.circles;
		this.flag = true;
	}

	public void run() {
		start = System.nanoTime();
		while (flag) {
			current = System.nanoTime();
			float timespan = (float) (current - start) / 1000 / 1000 / 100;
			if (isLose(timespan)) {
				view.reset();
			}
			ball.y = calculateY(timespan);
			ball.x = calculateX(timespan);
			checkEliminate(ball.x, ball.y);
			start = current;
		}
	}

	public void checkEliminate(float x, float y) {
		for (int i = 0; i < circles.size(); i++) {
			if (Math.pow(circles.get(i).x - x, 2)
					+ Math.pow(circles.get(i).y - y, 2) <= Math.pow(Ball.RADIUS
					+ circles.get(i).radius, 2)) {
				circles.remove(i);
			}
		}
	}

	public float calculateY(float timespan) {
		float span = timespan * ball.yv;
		float tmpTop = ball.y + span;
		if (tmpTop < Ball.RADIUS) {
			ball.yv = -ball.yv;
			return 2 * Ball.RADIUS - tmpTop;
		} else if (tmpTop + Ball.RADIUS > baffle.bottom) {
			ball.yv = -ball.yv;
			changeXV(timespan);
			changeYV();
			return 2 * baffle.bottom - 2 * Ball.RADIUS - tmpTop;
		}
		return tmpTop;
	}

	public float calculateX(float timespan) {
		float span = timespan * ball.xv;
		float tmpLeft = ball.x + span;
		if (tmpLeft - Ball.RADIUS < 0) {
			ball.xv = -ball.xv;
			return 2 * Ball.RADIUS - tmpLeft;
		} else if (tmpLeft + Ball.RADIUS > view.width) {
			ball.xv = -ball.xv;
			return 2 * view.width - 2 * Ball.RADIUS - tmpLeft;
		}
		return tmpLeft;
	}

	public void changeXV(float timespan) {
		float deltaT = (baffle.bottom - ball.y - Ball.RADIUS) / ball.xv;
		float tmpLeft = ball.x + deltaT * ball.xv;
		ball.xv = tmpLeft - baffle.left - Baffle.WIDTH / 2;
	}

	public void changeYV() {

	}

	public boolean isLose(float timespan) {
		float spanY = timespan * ball.yv;
		float spanX = timespan * ball.xv;
		float tmpTop = ball.y + spanY;
		float tmpLeft = ball.x + spanX;
		if (tmpTop + Ball.RADIUS > baffle.bottom
				&& (tmpLeft + Ball.RADIUS < baffle.left || (tmpLeft
						- Ball.RADIUS > baffle.left + Baffle.WIDTH))) {
			return true;
		}
		return false;
	}
}
