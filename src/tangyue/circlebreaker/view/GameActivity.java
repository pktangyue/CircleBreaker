package tangyue.circlebreaker.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class GameActivity extends BaseActivity {
	private BreakerView breakerview;
	private boolean isPause = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		breakerview = new BreakerView(this);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(breakerview);
	}

	public void startLevelComplete(int score) {
		Intent intent = new Intent(
				"tangyue.circlebreaker.view.LevelCompleteActivity");
		intent.putExtra("score", score);
		startActivity(intent);
		breakerview = null;
		this.finish();
	}

	public void onBackPressed() {
		if (isPause) {
			breakerview.resume();
		} else {
			breakerview.pause();
		}
		isPause = !isPause;
		return;
	}
}
