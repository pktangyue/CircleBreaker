package tangyue.circlebreaker.view;

import tangyue.circlebreaker.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FailActivity extends BaseActivity {
	private Button retry;
	private Button menu;
	private int level;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fail);
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
		// set level
		level = getLevel();
		TextView textViewLevel = (TextView) findViewById(R.id.level);
		textViewLevel.setText(textViewLevel.getText()
				+ String.format(" %02d", level));
	}
}