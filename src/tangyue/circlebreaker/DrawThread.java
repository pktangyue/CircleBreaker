package tangyue.circlebreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class DrawThread extends Thread {
	public BreakerView view;
	public DrawThread(BreakerView view){
		this.view = view;
	}
	public void run(){
		Canvas canvas = view.holder.lockCanvas(null);
        Paint mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(new RectF(40,60,80,80), mPaint);
        view.holder.unlockCanvasAndPost(canvas);
	}
}
