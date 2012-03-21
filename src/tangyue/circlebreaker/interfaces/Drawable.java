package tangyue.circlebreaker.interfaces;

import tangyue.circlebreaker.threads.BaseThread;
import android.graphics.Canvas;

public interface Drawable {
	public void init(Canvas canvas);

	public void drawSelf(Canvas canvas);

	public BaseThread getThread();

	public void disableThread();
}
