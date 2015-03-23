package com.chery.wupin.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.activity.RecordActivity;
import com.chery.wupin.bean.RecordObj;

public class SearchAdapter extends BaseAdapter {
	
	private Context mcontext;
	private List<RecordObj> mlist_search;

	public SearchAdapter(Context context, List<RecordObj> list_search) {
		this.mcontext = context;
		this.mlist_search = list_search;
	}

	@Override
	public int getCount() {
		return mlist_search != null ? mlist_search.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return mlist_search.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChildHolder childHolder = null;
		if(convertView == null){
			childHolder = new ChildHolder();
			convertView = View.inflate(mcontext, R.layout.item_child, null);
			childHolder.names = (TextView) convertView.findViewById(R.id.names);
			childHolder.time_child_tv = (TextView) convertView.findViewById(R.id.time_child_tv);
			childHolder.name_child_tv = (TextView) convertView.findViewById(R.id.name_child_tv);
			childHolder.price_child_tv = (TextView) convertView.findViewById(R.id.price_child_tv);
			childHolder.child_rl = (RelativeLayout) convertView.findViewById(R.id.child_rl);
			childHolder.owner_child = (TextView) convertView.findViewById(R.id.owner_child);
			childHolder.child_portrait = (ImageView) convertView.findViewById(R.id.child_portrait);
			convertView.setTag(childHolder);
		}else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		final RecordObj record = mlist_search.get(position);
		childHolder.time_child_tv.setText(record.getTime());
		childHolder.name_child_tv.setText(record.getCatogery());
		childHolder.price_child_tv.setText(String.valueOf(record.getPrice())  + " Ԫ");
		childHolder.names.setText(record.getName());
		childHolder.owner_child.setText(record.getOwner());
		
		childHolder.child_rl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(mcontext,RecordActivity.class);
				it.putExtra("modify", true);
				Bundle bd = new Bundle();
				bd.putSerializable("record", record);
				it.putExtras(bd);
				mcontext.startActivity(it);
			}
		});
		if (position % 4 == 0) {
			childHolder.child_portrait.setBackgroundResource(R.drawable.ic_men1);
		} 
		else if(position % 4 == 1) {
			childHolder.child_portrait.setBackgroundResource(R.drawable.ic_men2);
		}
		else if(position % 4 == 2) {
			childHolder.child_portrait.setBackgroundResource(R.drawable.ic_woman1);
		}
		else if(position % 4 == 3) {
			childHolder.child_portrait.setBackgroundResource(R.drawable.ic_woman2);
		}
		return convertView;
	}
	
	public class ChildHolder{
		private TextView time_child_tv;
		private TextView name_child_tv;
		private TextView price_child_tv;
		private TextView names;
		private TextView owner_child;
		private RelativeLayout child_rl;
		private ImageView child_portrait;
	}

}
