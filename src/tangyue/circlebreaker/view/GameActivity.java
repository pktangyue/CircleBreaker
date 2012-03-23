package tangyue.circlebreaker.view;

import tangyue.circlebreaker.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class GameActivity extends BaseActivity {
	private BreakerView breakerview;
	private boolean isPause = false;
	private PauseDialog pauseDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int level = getLevel();
		breakerview = new BreakerView(this, level);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(breakerview);
		pauseDialog = new PauseDialog(this, R.style.fullscreen);
	}

	public void startLevelComplete(int score, int level) {
		Intent intent = new Intent(
				"tangyue.circlebreaker.view.LevelCompleteActivity");
		intent.putExtra("score", score);
		intent.putExtra("level", level);
		startActivity(intent);
		this.finish();
	}

	public void startFail(int level) {
		Intent intent = new Intent("tangyue.circlebreaker.view.FailActivity");
		intent.putExtra("level", level);
		startActivity(intent);
		this.finish();
	}

	public void onBackPressed() {
		if (isPause) {
			breakerview.resume();
		} else {
			breakerview.pause();
			pauseDialog.show();
		}
		isPause = !isPause;
		return;
	}

	public void onPause() {
		pauseDialog.dismiss();
		super.onPause();
	}

	public void finish() {
		if (breakerview != null) {
			breakerview.destroyDrawingCache();
			breakerview = null;
		}
		super.finish();
	}
}
