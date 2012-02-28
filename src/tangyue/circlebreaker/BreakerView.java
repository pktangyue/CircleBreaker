package tangyue.circlebreaker;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BreakerView extends SurfaceView implements SurfaceHolder.Callback {

	public DrawThread thread;
	public SurfaceHolder holder;
	public float x;
	public float y;
	public ArrayList<Point> points = new ArrayList<Point>();
	Path path = new Path();
	public BreakerView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		thread = new DrawThread(this);
		holder = getHolder();
		holder.addCallback(this);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		int action = event.getAction();
		x = event.getX();
		y = event.getY();
		return true;
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if(!thread.isAlive()){
			thread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

}
