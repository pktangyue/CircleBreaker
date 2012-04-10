package tangyue.circlebreaker.view;

import tangyue.circlebreaker.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

public class GameActivity extends BaseActivity {
	private BreakerView breakerview;
	private boolean isPause = false;
	private PauseDialog pauseDialog;
	private Handler handler;
	private Thread loadingThread;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		loadGame();
	}

	public void loadGame() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					loadingThread = null;
					int level = getLevel();
					breakerview = new BreakerView(GameActivity.this, level);
					pauseDialog = new PauseDialog(GameActivity.this,
							R.style.fullscreen);
					setContentView(breakerview);
					break;
				default:
					break;
				}
			}
		};
		loadingThread = new Thread() {
			public void run() {
				try {
					Thread.sleep(10);
					handler.sendEmptyMessage(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		loadingThread.start();
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
		if (pauseDialog != null) {
			pauseDialog.dismiss();
		}
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
