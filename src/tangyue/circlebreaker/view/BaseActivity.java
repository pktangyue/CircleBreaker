package tangyue.circlebreaker.view;

import java.util.ArrayList;

import tangyue.circlebreaker.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BaseActivity extends Activity {
	public static final int EXIT_APPLICATION = 0x0001;
	public static ArrayList<Activity> activityList = new ArrayList<Activity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activityList.add(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		activityList.remove(this);
	}

	protected void startMain() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

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

	protected void exit() {
		if (activityList.size() > 0) {
			for (Activity activity : activityList) {
				activity.finish();
			}
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}
}
