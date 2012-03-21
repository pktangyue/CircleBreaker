package tangyue.circlebreaker.threads;

import tangyue.circlebreaker.view.BreakerView;

public class DrawThread extends BaseThread {
	private BreakerView view;
	private int count = 0;

	public DrawThread(BreakerView view) {
		this.view = view;
		this.flag = true;
	}

	public void run() {
		while (flag) {
			synchronized (view.getHolder()) {
				view.doDraw();
			}
			this.count++;
			if (count == 20) { // 如果计满20帧
				count = 0; // 清空计数器
				long tempStamp = System.nanoTime();// 获取当前时间
				long span = tempStamp - start; // 获取时间间隔
				start = tempStamp; // 为start重新赋值
				double fps = Math.round(100000000000.0 / span * 20) / 100.0;// 计算帧速率
				view.fps = ("FPS:" + fps);// 将计算出的帧速率设置到BallView的相应字符串对象中
			}
		}
	}
}
