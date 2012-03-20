package tangyue.circlebreaker.view;

import tangyue.circlebreaker.BreakerView;
import tangyue.circlebreaker.GameScore;
import android.content.Intent;
import android.os.Bundle;

public class GameActivity extends BaseActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BreakerView breakerview = new BreakerView(this);
		setContentView(breakerview);
	}

	public void startLevelComplete() {
		Intent intent = new Intent(
				"tangyue.circlebreaker.view.LevelCompleteActivity");
		intent.putExtra("score", GameScore.getTotalPoints());
		startActivity(intent);
		this.finish();
	}
}
