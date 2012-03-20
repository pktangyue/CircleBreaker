package tangyue.circlebreaker;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BreakerView view = new BreakerView(this);
		setContentView(view);
	}

	public void onBackPressed() {
		this.finish();
	}
}