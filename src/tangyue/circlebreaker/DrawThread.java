package tangyue.circlebreaker;

public class DrawThread extends Thread {
	private BreakerView view;
	public boolean flag = false;
	private int count = 0;
	private long start = System.nanoTime();

	public DrawThread(BreakerView view) {
		this.view = view;
		this.flag = true;
	}

	public void run() {
		while (flag) {
			synchronized (view.holder) {
				view.doDraw();
			}
			this.count++;
			if (count == 20) { // 如果计满20帧
				count = 0; // 清空计数器
				long tempStamp = System.nanoTime();// 获取当前时间
				long span = tempStamp - start; // 获取时间间隔
				start = tempStamp; // 为start重新赋值
				double fps = Math.round(100000000000.0 / span * 20) / 100.0;// 计算帧速率
				view.fps = "FPS:" + fps;// 将计算出的帧速率设置到BallView的相应字符串对象中
			}
//			try {
//				Thread.sleep(20); // 线程休眠一段时间
//			} catch (Exception e) {
//				e.printStackTrace(); // 捕获并打印异常
//			}
		}
	}
}
