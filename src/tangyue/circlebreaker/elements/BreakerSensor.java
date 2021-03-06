package tangyue.circlebreaker.elements;

import tangyue.circlebreaker.helper.SharedPreferencesHelper;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class BreakerSensor {
	public float ratioX = 0.0f;
	public float ratioY = 0.0f;
	private static BreakerSensor sensor = null;
	private float adjustX;
	private float adjustY;

	private BreakerSensor(Context context) {
		registerSernor(context);
		SharedPreferencesHelper helper = new SharedPreferencesHelper(context);
		adjustX = helper.getAdjustX();
		adjustY = helper.getAdjustY();
	}

	public static BreakerSensor getInstance(Context context) {
		if (sensor == null) {
			return new BreakerSensor(context);
		}
		return sensor;
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
					ratioX = limitRatioX(event.values[0]);// 使方向和二维坐标一致
					ratioY = limitRatioY(event.values[1]);
				}
			}
		};
		mySensorManager.registerListener(mySensorEventListener,
				mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	private float limitRatioX(float value) {
		value -= adjustX;
		value /= 2;
		if (value > 4.0f) {
			value = 4.0f;
		} else if (value < -4.0f) {
			value = -4.0f;
		}
		return -value;// 使方向和二维坐标系一致
	}

	private float limitRatioY(float value) {
		value -= adjustY;
		value = value * 2f;
		if (value > 3.0f) {
			value = 3.0f;
		} else if (value < -3.0f) {
			value = -3.0f;
		}
		return 3.0f - value;
	}
}
