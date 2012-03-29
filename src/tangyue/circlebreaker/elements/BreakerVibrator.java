package tangyue.circlebreaker.elements;

import android.content.Context;
import android.os.Vibrator;

public class BreakerVibrator {
	private static BreakerVibrator instance = null;
	private Vibrator vibrator = null;

	private BreakerVibrator(Context context) {
		vibrator = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);
	}

	public static BreakerVibrator getInstance(Context context) {
		if (instance == null) {
			return new BreakerVibrator(context);
		}
		return instance;
	}

	public void vibrate() {
		vibrator.vibrate(100);
	}
}
