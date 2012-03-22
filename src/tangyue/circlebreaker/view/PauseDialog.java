package tangyue.circlebreaker.view;

import tangyue.circlebreaker.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PauseDialog extends Dialog {
	private Context context;
	private Button resume;
	private Button retry;
	private Button menu;

	public PauseDialog(final Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pause_dialog);
		// resume button
		resume = (Button) findViewById(R.id.resume);
		resume.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		// retry button
		retry = (Button) findViewById(R.id.retry);
		retry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((GameActivity) context).finish();
				((GameActivity) context).startGame();
			}
		});
		// menu button
		menu = (Button) findViewById(R.id.menu);
		menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((GameActivity) context).finish();
				((GameActivity) context).startMenu();
			}
		});
	}

	public void onBackPressed() {
		super.onBackPressed();
		((GameActivity) context).onBackPressed();
	}
}
