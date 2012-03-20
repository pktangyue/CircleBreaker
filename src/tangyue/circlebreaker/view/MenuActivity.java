package tangyue.circlebreaker.view;

import java.util.ArrayList;
import java.util.HashMap;

import tangyue.circlebreaker.R;
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
		ListView list = (ListView) findViewById(R.id.list);
		list.setDivider(null);
		ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("item_title", String.format("%02d", i));
			map.put("item_score", (int) (Math.random() * 10000));
			items.add(map);
		}
		SimpleAdapter listItemAdapter = new SimpleAdapter(this, items,
				R.layout.item, new String[] { "item_title", "item_score" },
				new int[] { R.id.item_title, R.id.item_score });
		list.setAdapter(listItemAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				startGame();
			}
		});
	}
}