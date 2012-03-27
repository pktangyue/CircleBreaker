package tangyue.circlebreaker.view;

import tangyue.circlebreaker.R;
import tangyue.circlebreaker.helper.LevelSQLiteHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LevelCompleteActivity extends BaseActivity {
	private Button next;
	private Button retry;
	private Button menu;
	private int score;
	private int level;
	private int bestScore;
	private LevelSQLiteHelper helper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.levelcomplete);
		helper = new LevelSQLiteHelper(this, LevelSQLiteHelper.DB_NAME, null,
				LevelSQLiteHelper.VERSION);
		// next button
		next = (Button) findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startGame(level + 1);
			}
		});
		// retry button
		retry = (Button) findViewById(R.id.retry);
		retry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startGame(level);
			}
		});
		// menu button
		menu = (Button) findViewById(R.id.menu);
		menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startMenu();
			}
		});
	}

	@Override
	public void onStart() {
		super.onStart();
		score = getScore();
		level = getLevel();
		bestScore = helper.getBestScore(level);
		// update data in SQLite
		if (bestScore < score) {
			helper.replaceBestScore(level, score);
			bestScore = score;
		}
		// set score
		TextView textViewScore = (TextView) findViewById(R.id.your_score);
		textViewScore.setText(String.valueOf(score));
		// set best score
		TextView textViewBestScore = (TextView) findViewById(R.id.best_score);
		textViewBestScore.setText(String.valueOf(bestScore));
		// set level
		TextView textViewLevel = (TextView) findViewById(R.id.level);
		textViewLevel.setText(textViewLevel.getText()
				+ String.format(" %02d", level));
	}
}