package tangyue.circlebreaker;

public class BallThread extends Thread {
	private Ball ball;
	public boolean flag = false;
	private long current;
	private int maxWidth;
	private int maxHeight;

	public BallThread(Ball ball, int maxWidth, int maxHeight) {
		this.ball = ball;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.flag = true;
	}

	public void run() {
		while (flag) {
			current = System.nanoTime();
			float timespan = (float) (current - ball.time) / 1000 / 1000;
			ball.top = calculateTop(timespan);
			ball.time = current;
		}
	}

	public float calculateTop(float timespan) {
		float span = timespan * ball.v;
		float tmpTop = ball.top + span;
		if (tmpTop < 0) {
			ball.v = -ball.v;
			return -tmpTop;
		} else if (tmpTop > maxHeight) {
			ball.v = -ball.v;
			return 2 * maxHeight - tmpTop;
		}
		return tmpTop;
	}

	public void calculateLeft() {

	}
}
