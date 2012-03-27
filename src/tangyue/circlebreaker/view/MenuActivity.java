package tangyue.circlebreaker.view;

import java.util.ArrayList;
import java.util.HashMap;

import tangyue.circlebreaker.R;
import tangyue.circlebreaker.helper.LevelSQLiteHelper;
import tangyue.circlebreaker.levels.GameLevel;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class MenuActivity extends BaseActivity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		LevelSQLiteHelper helper = new LevelSQLiteHelper(this,
				LevelSQLiteHelper.DB_NAME, null, LevelSQLiteHelper.VERSION);
		HashMap<Integer, Integer> data = helper.getLevelHash();
		assignListView(data);
	}

	public void assignListView(HashMap<Integer, Integer> data) {
		ListView list = (ListView) findViewById(R.id.list);
		list.setDivider(null);// 去掉分割线
		ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
		for (int i = 1; i <= GameLevel.MAX_LEVEL; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("item_title", String.format("%02d", i));
			map.put("item_score", data.containsKey(i) ? data.get(i) : 0);
			items.add(map);
		}
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, items,
				R.layout.item, new String[] { "item_title", "item_score" },
				new int[] { R.id.item_title, R.id.item_score });
		list.setAdapter(listItemAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				startGame(position + 1);
			}
		});
	}

	public void onBackPressed() {
		startMain();
	}
}