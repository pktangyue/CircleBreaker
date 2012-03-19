package tangyue.circlebreaker;

import java.util.ArrayList;

public class BallThread extends Thread {
	private BreakerView view;
	private Ball ball;
	private Baffle baffle;
	private long current;
	private long start;
	private long pathStart;
	private BreakerSensor sensor;
	private ArrayList<Float> pathPoints = new ArrayList<Float>();
	private int pathInterval = 0;

	public boolean flag = false;

	public BallThread(BreakerView view) {
		this.view = view;
		this.ball = view.getBall();
		this.baffle = view.getBaffle();
		this.sensor = view.getSensor();
		this.flag = true;
	}

	public void run() {
		initPathPoints();
		start = System.currentTimeMillis();
		while (flag) {
			current = System.currentTimeMillis();
			float timespan = (float) (current - start) / 100.0f;
			if (ball.isLose() && ball.getY() > 1500.0f) {
				view.reset();
			}
			calculateX(timespan);
			calculateY(timespan);
			view.checkEliminate(ball.getX(), ball.getY());
			checkLose(timespan);
			setBallPath();
			start = current;
		}
	}

	public void initPathPoints() {
		float x = ball.getX();
		float y = ball.getY();
		float vx = ball.getVX();
		float vy = ball.getVY();
		float deltaTime = 0.5f;
		pathStart = System.currentTimeMillis();
		pathPoints.clear();
		while (y + Ball.RADIUS < view.getHeight()) {
			x += vx * deltaTime;
			y += deltaTime * vy + Ball.G * deltaTime * deltaTime / 2.0f;
			if (x - Ball.RADIUS <= 0) {
				vx = -vx;
				x = 2.0f * Ball.RADIUS - x;
			} else if (x + Ball.RADIUS > view.getWidth()) {
				vx = -vx;
				x = 2.0f * view.getWidth() - 2.0f * Ball.RADIUS - x;
			}
			vy = vy + deltaTime * Ball.G;
			pathPoints.add(x);
			pathPoints.add(y);
		}
	}

	public void setBallPath() {
		if (pathPoints.size() == 0)
			return;
		long pathCurrent = System.currentTimeMillis();
		int interval = (int) ((pathCurrent - pathStart) / 100f / 0.5f);
		for (int i = 0; i < interval - pathInterval; i++) {
			pathPoints.remove(0);
			pathPoints.remove(0);
		}
		int size = pathPoints.size();
		float[] pts = new float[size];
		for (int i = size - 1; i >= 0; i--) {
			pts[i] = pathPoints.get(i);
		}
		ball.setPathPoints(pts);
		pathInterval = interval;
	}

	public void calculateY(float timespan) {
		float span = timespan * ball.getVY() + Ball.G * timespan * timespan
				/ 2.0f;
		float tmpTop = ball.getY() + span;
		if (tmpTop + Ball.RADIUS > baffle.getBottom() && !ball.isLose()) {
			calculateVY();
			calculateVX(timespan);
			tmpTop = 2.0f * baffle.getBottom() - 2.0f * Ball.RADIUS - tmpTop;
			ball.setY(tmpTop);// 在initPathPoints需要先设定y
			GameScore.reset();
			initPathPoints();
		} else {
			calculateVY(timespan);
			ball.setY(tmpTop);
		}

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

	public void calculateVX(float timespan) {
		float deltaT = (baffle.getBottom() - ball.getY() - Ball.RADIUS)
				/ ball.getVY();
		if (Float.isNaN(deltaT) || Float.isInfinite(deltaT)) {
			deltaT = 0.0f;
		}
		float tmpLeft = ball.getX() + deltaT * ball.getVX();
		ball.setVX((tmpLeft - baffle.getLeft() - Baffle.WIDTH / 2.0f) * 0.5f);
	}

	public void calculateVY() {
		ball.setVY(Ball.BASE_VY - sensor.ratioY * 5);
	}

	public void calculateVY(float timespan) {
		ball.setVY(ball.getVY() + Ball.G * timespan);
	}

	public void checkLose(float timespan) {
		if (ball.isLose()) {
			ball.setLose(true);// 已经丢失就不再检查
			return;
		}
		float spanY = timespan * ball.getVY() + Ball.G * timespan * timespan
				* 0.5f;
		float spanX = timespan * ball.getVX();
		float tmpTop = ball.getY() + spanY;
		float tmpLeft = ball.getX() + spanX;
		if (tmpTop + Ball.RADIUS > baffle.getBottom()
				&& (tmpLeft < baffle.getLeft() || (tmpLeft > baffle.getLeft()
						+ Baffle.WIDTH))) {
			ball.setLose(true);
			return;
		}
		ball.setLose(false);
	}
}
