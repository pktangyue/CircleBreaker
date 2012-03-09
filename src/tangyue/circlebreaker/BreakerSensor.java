package tangyue.circlebreaker;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class BreakerSensor {
	float ratioX = 0;
	float ratioY = 0;
	private static BreakerSensor sensor = null;

	private BreakerSensor(Context context) {
		registerSernor(context);
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
					ratioX = limitRatio(event.values[0]);// 使方向和二维坐标一致
				}
			}
		};
		mySensorManager.registerListener(mySensorEventListener,
				mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_GAME);
	}

	private float limitRatio(float value) {
		if (value > 4) {
			value = 4;
		} else if (value < -4) {
			value = -4;
		}
		return -value;// 使方向和二维坐标系一致
	}
}
