package tangyue.circlebreaker.view;

import tangyue.circlebreaker.R;
import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

public class BaseActivity extends Activity {
	protected void startGame() {
		Intent intent = new Intent("tangyue.circlebreaker.view.GameActivity");
		startActivity(intent);
	}

	protected void startMenu() {
		Intent intent = new Intent("tangyue.circlebreaker.view.MenuActivity");
		startActivity(intent);
	}

	protected void setScore() {
		Intent intent = getIntent();
		int score = intent.getIntExtra("score", 0);
		TextView textview = (TextView) findViewById(R.id.your_score);
		textview.setText(String.valueOf(score));
	}

}
