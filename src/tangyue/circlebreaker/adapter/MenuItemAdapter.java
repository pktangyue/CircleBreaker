package tangyue.circlebreaker.adapter;

import java.util.ArrayList;
import tangyue.circlebreaker.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuItemAdapter extends BaseAdapter {
	private ArrayList<MenuItem> list;
	private Context context;

	public MenuItemAdapter(Context context, ArrayList<MenuItem> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater mInflater = LayoutInflater.from(context);
		View view = null;
		MenuItem item = list.get(position);
		if (item.isLocked()) {
			view = mInflater.inflate(R.layout.dis_item, null);
			TextView itemTitle = (TextView) view.findViewById(R.id.item_title);
			itemTitle.setText(item.getTitle());
		} else {
			view = mInflater.inflate(R.layout.item, null);
			TextView itemTitle = (TextView) view.findViewById(R.id.item_title);
			itemTitle.setText(item.getTitle());
			TextView itemScore = (TextView) view.findViewById(R.id.item_score);
			itemScore.setText(item.getScore());
		}
		return view;
	}
}
