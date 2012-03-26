package tangyue.circlebreaker.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
	private SharedPreferences sp;
	private final static String NAME = "Adjust";
	private final static String ADJUST_X_KEY = "AdjustX";
	private final static String ADJUST_Y_KEY = "AdjustY";

	public SharedPreferencesHelper(Context context) {
		sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
	}

	public float getAdjustX() {
		return sp.getFloat(ADJUST_X_KEY, 0);
	}

	public float getAdjustY() {
		return sp.getFloat(ADJUST_Y_KEY, 0);
	}

	public void update(float adjustX, float adjustY) {
		SharedPreferences.Editor editor = sp.edit();
		editor.putFloat("AdjustX", adjustX);
		editor.putFloat("AdjustY", adjustY);
		editor.commit();
	}
}
