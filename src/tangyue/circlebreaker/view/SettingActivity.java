package tangyue.circlebreaker.view;

import tangyue.circlebreaker.R;
import tangyue.circlebreaker.helper.SharedPreferencesHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingActivity extends BaseActivity {
	private Button calibrate;
	private SettingView view;
	private SharedPreferencesHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		// calibrate button
		calibrate = (Button) findViewById(R.id.calibrate);
		calibrate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				helper.update(view.sensorX, view.sensorY);
				view.adjustX = view.sensorX;
				view.adjustY = view.sensorY;
			}
		});
		// setting view
		view = (SettingView) findViewById(R.id.settingview);

		helper = new SharedPreferencesHelper(this);
		view.adjustX = helper.getAdjustX();
		view.adjustY = helper.getAdjustY();
	}

	public void onBackPressed() {
		startMain();
	}
}
