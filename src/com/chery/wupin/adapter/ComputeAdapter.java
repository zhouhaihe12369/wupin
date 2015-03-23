package com.chery.wupin.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.activity.MoreActivity;
import com.chery.wupin.bean.RecordObj;

public class ComputeAdapter extends BaseExpandableListAdapter {
	
	private Context mcontext;
	private List<List<RecordObj>> mgroup_list;
	private String[] flags = {"录入物品","待购物品"};
	private boolean mwhere , mowner;

	public ComputeAdapter(Context context,List<List<RecordObj>> group_list, boolean where, boolean owner) {
		this.mcontext = context;
		this.mgroup_list = group_list;
		this.mwhere = where;
		this.mowner = owner;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mgroup_list.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
		
		ChildHolder childHolder = null;
		if(convertView == null){
			childHolder = new ChildHolder();
			convertView = View.inflate(mcontext, R.layout.item_chat, null);
			childHolder.chat_ll = (LinearLayout) convertView.findViewById(R.id.chat_ll);
			childHolder.chat_name_tv = (TextView) convertView.findViewById(R.id.chat_name_tv);
			childHolder.chat_count_tv = (TextView) convertView.findViewById(R.id.chat_count_tv);
			childHolder.chat_percent_tv = (TextView) convertView.findViewById(R.id.chat_percent_tv);
			childHolder.ProgressBar = (ProgressBar) convertView.findViewById(R.id.ProgressBar);
			convertView.setTag(childHolder);
		}else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		RecordObj record = (RecordObj) getChild(groupPosition, childPosition);
		final String name = record.getName();
		final String flag = record.getFlag();
		int count = record.getCount();
		childHolder.chat_name_tv.setText(name);
		childHolder.chat_count_tv.setText(count+"项");
		
		int total_count = 0;
		List<RecordObj> list = mgroup_list.get(groupPosition);
		for (int i = 0; i < list.size(); i++) {
			int counts = list.get(i).getCount();
			total_count += counts;
		}
		DecimalFormat df1 = new DecimalFormat("0.0000");  
		String str_percent = df1.format((float)count / total_count);
		float percent =Float.parseFloat(str_percent) * 100;
		DecimalFormat df2 = new DecimalFormat("0.00");
		String percents = df2.format(percent) + "%";
		childHolder.chat_percent_tv.setText(percents);
		int value = childHolder.ProgressBar.getProgress();
		if (value == 0) {
			doProgress(percent , childHolder.ProgressBar);
		} else {
			childHolder.ProgressBar.setProgress((int)percent);
		}
		
		childHolder.chat_ll.setOnClickListener(new OnClickListener() {
			
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
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return  mgroup_list.get(groupPosition) == null ? 0 :mgroup_list.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return flags[groupPosition];
	}

	@Override
	public int getGroupCount() {
		return flags.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		View view = View.inflate(mcontext, R.layout.item_group, null);
		TextView name_ll = (TextView) view.findViewById(R.id.name_ll);
		ImageView next_el_iv = (ImageView) view.findViewById(R.id.next_el_iv);
		name_ll.setText(flags[groupPosition] + "  :");
		if(isExpanded){
			next_el_iv.setBackgroundResource(R.drawable.ic_develop_normal);
		}else{
			next_el_iv.setBackgroundResource(R.drawable.ic_retract_normal);
		}
		return view;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	public class ChildHolder{
		private TextView chat_name_tv;
		private TextView chat_count_tv;
		private TextView chat_percent_tv;
		private ProgressBar ProgressBar;
		private LinearLayout chat_ll;
	}
	
	private void doProgress(final float percent , final ProgressBar progressBar) {
		new Thread() {  
            @Override  
            public void run() {  
                int index = 0;
                while(index < (int)percent) {  
                    doWork(index);  
                    progressBar.setProgress(index);
                    index += 2;
                }  
            }  
        }.start();
	}

	private void doWork(int index) {  
		try {
			Thread.sleep(50);
		} catch (Exception e) {
		}
	}  
}
