package tangyue.circlebreaker.view;

import java.util.ArrayList;
import java.util.HashMap;

import tangyue.circlebreaker.R;
import tangyue.circlebreaker.adapter.*;
import tangyue.circlebreaker.helper.LevelSQLiteHelper;
import tangyue.circlebreaker.levels.GameLevel;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuActivity extends BaseActivity {
	private int nowMaxLevel;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		LevelSQLiteHelper helper = new LevelSQLiteHelper(this,
				LevelSQLiteHelper.DB_NAME, null, LevelSQLiteHelper.VERSION);
		nowMaxLevel = helper.getNowMaxLevel();
		assignListView(helper.getLevelHash());
	}

	public void assignListView(HashMap<Integer, Integer> data) {
		ListView list = (ListView) findViewById(R.id.list);
		list.setDivider(null);// 去掉分割线
		ArrayList<MenuItem> items = new ArrayList<MenuItem>();
		for (int i = 1; i <= GameLevel.MAX_LEVEL; i++) {
			MenuItem item = null;
			if (data.containsKey(i)) {
				item = new MenuItem(i, data.get(i), false);
			} else if (i == nowMaxLevel + 1) {
				item = new MenuItem(i, 0, false);
			} else {
				item = new MenuItem(i, 0, true);
			}
			items.add(item);
		}
		MenuItemAdapter adapter = new MenuItemAdapter(this, items);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (nowMaxLevel < position) {
					return;
				}
				startGame(position + 1);
			}
		});
	}

	public void onBackPressed() {
		startMain();
	}
}