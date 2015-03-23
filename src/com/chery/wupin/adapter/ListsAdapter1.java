package com.chery.wupin.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.activity.RecordActivity;
import com.chery.wupin.bean.RecordObj;

public class ListsAdapter1 extends BaseExpandableListAdapter{
	
	private Context mcontext;
	private List<List<RecordObj>> mlist;
	private String[] mflags = {"已有物品","待购物品"};

	public ListsAdapter1(Context context,List<List<RecordObj>> group_list) {
		mcontext = context;
		mlist = group_list;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mlist.get(groupPosition).get(childPosition);
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
			convertView = View.inflate(mcontext, R.layout.item_child, null);
			childHolder.time_child_tv = (TextView) convertView.findViewById(R.id.time_child_tv);
			childHolder.name_child_tv = (TextView) convertView.findViewById(R.id.name_child_tv);
			childHolder.price_child_tv = (TextView) convertView.findViewById(R.id.price_child_tv);
			childHolder.names = (TextView) convertView.findViewById(R.id.names);
			childHolder.owner_child = (TextView) convertView.findViewById(R.id.owner_child);
			childHolder.address_child = (TextView) convertView.findViewById(R.id.address_child);
			childHolder.child_rl = (RelativeLayout) convertView.findViewById(R.id.child_rl);
			childHolder.child_portrait = (ImageView) convertView.findViewById(R.id.child_portrait);
			convertView.setTag(childHolder);
		}else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		final RecordObj record = (RecordObj) getChild(groupPosition, childPosition);
		childHolder.time_child_tv.setText(record.getTime());
		childHolder.name_child_tv.setText(record.getCatogery());
		childHolder.price_child_tv.setText(String.valueOf(record.getPrice()) + " 元");
		childHolder.names.setText(record.getName());
		childHolder.owner_child.setText(record.getOwner());
		String address = record.getAddress();
		if (address != null) {
			childHolder.address_child.setText("放置地点: " + record.getAddress());
		}else{
			childHolder.address_child.setVisibility(View.GONE);
		}
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
		int child_num = (int) getChildId(groupPosition, childPosition);
		if (child_num % 4 == 0) {
			childHolder.child_portrait.setBackgroundResource(R.drawable.ic_men1);
		} 
		else if(child_num % 4 == 1) {
			childHolder.child_portrait.setBackgroundResource(R.drawable.ic_men2);
		}
		else if(child_num % 4 == 2) {
			childHolder.child_portrait.setBackgroundResource(R.drawable.ic_woman1);
		}
		else if(child_num % 4 == 3) {
			childHolder.child_portrait.setBackgroundResource(R.drawable.ic_woman2);
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return  mlist.get(groupPosition) == null ? 0 :mlist.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mflags[groupPosition];
	}

	@Override
	public int getGroupCount() {
		return mflags.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,View convertView, ViewGroup parent) {
		
		View view = View.inflate(mcontext, R.layout.item_group, null);
		ImageView next_el_iv = (ImageView) view.findViewById(R.id.next_el_iv);
		TextView name_ll = (TextView) view.findViewById(R.id.name_ll);
		name_ll.setText(mflags[groupPosition]);
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
		private TextView time_child_tv;
		private TextView name_child_tv;
		private TextView price_child_tv;
		private TextView names;
		private TextView owner_child;
		private TextView address_child;
		private RelativeLayout child_rl;
		private ImageView child_portrait;
	}
}
