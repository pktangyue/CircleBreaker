package tangyue.circlebreaker.view;

import tangyue.circlebreaker.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FailActivity extends BaseActivity {
	private Button retry;
	private Button menu;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fail);
		// retry button
		retry = (Button) findViewById(R.id.retry);
		retry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startGame();
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

	public void onBackPressed() {
		super.onBackPressed();
	}
}
