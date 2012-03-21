package tangyue.circlebreaker.threads;

public class BaseThread extends Thread {
	public boolean flag = false;
	protected long current;
	protected long start;

	public void pauseThread() {
		this.flag = false;
	}

	public void resumeThread() {
		this.flag = true;
		this.start = System.currentTimeMillis();
	}
}
