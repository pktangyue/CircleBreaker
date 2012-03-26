package tangyue.circlebreaker.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Paint.Style;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SettingView extends SurfaceView implements SurfaceHolder.Callback,
		Runnable {
	public float sensorX = 0;
	public float sensorY = 0;
	public float adjustX = 0;
	public float adjustY = 0;

	private final static int TOP = 150;
	private final static int HALF_RECT_LENDTH = 150;
	private final static int RANGE_RADIUS = 20;
	private final static int BALL_RADIUS = 8;
	private boolean flag = false;
	private SurfaceHolder holder = null;
	private Canvas canvas = null;
	private Paint paint = null;

	public SettingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = this.getHolder();
		holder.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		registerSernor(context);
	}

	private void registerSernor(Context context) {
		SensorManager mySensorManager = (SensorManager) context
				.getSystemService(Context.SENSOR_SERVICE);
		SensorEventListener mySensorEventListener = new SensorEventListener() {
			@Override
			public void onAccuracyChanged(final Sensor sensor,
					final int accuracy) {
			}

			@Override
			public void onSensorChanged(final SensorEvent event) {
				if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
					sensorX = event.values[0];
					sensorY = event.values[1];
				}
			}
		};
		mySensorManager.registerListener(mySensorEventListener,
				mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	protected void doDraw(Canvas canvas) {
		int width = getWidth();
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		paint.setColor(Color.CYAN);
		paint.setStyle(Style.STROKE);
		canvas.drawRect(width / 2 - HALF_RECT_LENDTH, TOP, width / 2
				+ HALF_RECT_LENDTH, TOP + 2 * HALF_RECT_LENDTH, paint);
		canvas.drawLine(width / 2 - HALF_RECT_LENDTH, TOP + HALF_RECT_LENDTH,
				width / 2 - RANGE_RADIUS, TOP + HALF_RECT_LENDTH, paint);
		canvas.drawLine(width / 2 + HALF_RECT_LENDTH, TOP + HALF_RECT_LENDTH,
				width / 2 + RANGE_RADIUS, TOP + HALF_RECT_LENDTH, paint);
		canvas.drawLine(width / 2, TOP, width / 2, TOP + HALF_RECT_LENDTH
				- RANGE_RADIUS, paint);
		canvas.drawLine(width / 2, TOP + HALF_RECT_LENDTH + RANGE_RADIUS,
				width / 2, TOP + 2 * HALF_RECT_LENDTH, paint);
		paint.setStrokeWidth(3);
		paint.setColor(Color.RED);
		canvas.drawCircle(width / 2, TOP + HALF_RECT_LENDTH, RANGE_RADIUS,
				paint);
		paint.setStrokeWidth(1);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.FILL);
		canvas.drawCircle(width / 2 - (sensorX - adjustX) * 15, TOP
				+ HALF_RECT_LENDTH + (sensorY - adjustY) * 15, BALL_RADIUS,
				paint);
	}

	@Override
	public void run() {
		while (flag) {
			synchronized (holder) {
				canvas = holder.lockCanvas();
				doDraw(canvas);
				if (canvas != null) {
					holder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.flag = true;
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.flag = false;
	}
}