package tangyue.circlebreaker.view;

import tangyue.circlebreaker.BreakerView;
import tangyue.circlebreaker.GameScore;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class GameActivity extends BaseActivity {
	private BreakerView breakerview;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		breakerview = new BreakerView(this);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(breakerview);
	}

	public void startLevelComplete() {
		Intent intent = new Intent(
				"tangyue.circlebreaker.view.LevelCompleteActivity");
		intent.putExtra("score", GameScore.getTotalPoints());
		startActivity(intent);
		this.finish();
	}
	
	public void onBackPassed(){
		breakerview.pause();
	}
}
