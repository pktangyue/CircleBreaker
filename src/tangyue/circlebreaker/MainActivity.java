package tangyue.circlebreaker;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);	//无标题
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);	//全屏
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	//固定垂直
		Display display = getWindowManager().getDefaultDisplay();
		BreakerView view = new BreakerView(this, display.getWidth(),
				display.getHeight());
		setContentView(view);
	}
}