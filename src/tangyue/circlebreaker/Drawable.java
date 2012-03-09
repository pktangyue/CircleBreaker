package tangyue.circlebreaker;

import android.graphics.Canvas;

public interface Drawable {
	public void init(Canvas canvas);
	public void drawSelf(Canvas canvas);
}
