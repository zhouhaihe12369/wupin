package com.chery.wupin.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.activity.MoreActivity;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.view.RiseNumberTextView;

public class GridAdapter extends BaseAdapter {
	
	private Context mcontext;
	private List<RecordObj> mlist;
	private boolean mwhere;
	private boolean mowner;

	public GridAdapter(Context context, List<RecordObj> list, boolean where, boolean owner) {
		this.mcontext = context;
		this.mlist = list;
		this.mwhere = where;
		this.mowner = owner;
	}

	@Override
	public int getCount() {
		return mlist != null ? mlist.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mlist.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChildHolder childHolder = null;
		if(convertView == null){
			childHolder = new ChildHolder();
			convertView = View.inflate(mcontext, R.layout.item_grid, null);
			childHolder.percent_tv = (RiseNumberTextView) convertView.findViewById(R.id.percent_tv);
			childHolder.chat_name_tv = (TextView) convertView.findViewById(R.id.chat_name_tv);
			childHolder.chat_num_tv = (TextView) convertView.findViewById(R.id.chat_num_tv);
			childHolder.grid_ll = (LinearLayout) convertView.findViewById(R.id.grid_ll);
			childHolder.grid_lls = (LinearLayout) convertView.findViewById(R.id.grid_lls);
			convertView.setTag(childHolder);
		}else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		RecordObj record = (RecordObj) mlist.get(position);
		int count = record.getCount();
		final String name = record.getName();
		final String flag = record.getFlag();
		int total_count = 0;
		for (int i = 0; i < mlist.size(); i++) {
			int counts = ((RecordObj) mlist.get(i)).getCount();
			total_count += counts;
		}
		DecimalFormat df1 = new DecimalFormat("0.0000");  
		String str_percent = df1.format((float)count / total_count);
		float percent =Float.parseFloat(str_percent) * 100;
		DecimalFormat df2 = new DecimalFormat("0.00");
		String percents = df2.format(percent);
		childHolder.percent_tv.withNumber(percent).setDuration(4000).start();
		childHolder.chat_name_tv.setText(record.getName());
		childHolder.chat_num_tv.setText(count+"По");
		if(record.getName() == null){
			childHolder.grid_ll.setVisibility(View.GONE);
		}
		childHolder.grid_ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(mcontext,MoreActivity.class);
				it.putExtra("compute", true);
				it.putExtra("name", name);
				it.putExtra("flag", flag);
				it.putExtra("where", mwhere);
				it.putExtra("owner", mowner);
				mcontext.startActivity(it);
			}
		});
		if (position % 4 == 0) {
			childHolder.grid_lls.setBackgroundResource(R.drawable.green);
		} 
		else if(position % 4 == 1){
			childHolder.grid_lls.setBackgroundResource(R.drawable.bule);
		}
		else if(position % 4 == 2){
			childHolder.grid_lls.setBackgroundResource(R.drawable.yellow);
		}
		else if(position % 4 == 3){
			childHolder.grid_lls.setBackgroundResource(R.drawable.dark_green);
		}
		return convertView;
	}
	
	public class ChildHolder{
		private TextView chat_name_tv , chat_num_tv;
		private RiseNumberTextView percent_tv;
		private LinearLayout grid_ll , grid_lls;
	}

}
