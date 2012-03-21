package tangyue.circlebreaker.threads;

public class BaseThread extends Thread {
	public boolean flag = false;
	protected long current;
	protected long start;
	protected long pauseDuration = 0;
	protected boolean isPause = false;

	public void pauseThread() {
		start = System.currentTimeMillis();
		this.isPause = true;
	}

	public void resumeThread() {
		this.current = System.currentTimeMillis();
		if (this.isAlive()) {
			this.pauseDuration += (current - start);// 未start线程的则不加暂停时间
		}
		this.start = current;
		this.isPause = false;
	}

	public void startThread() {
		if (!this.isAlive()) {
			this.start();
		}
	}
}
