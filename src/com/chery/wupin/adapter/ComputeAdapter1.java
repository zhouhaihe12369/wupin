package com.chery.wupin.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.view.MyGridView;

public class ComputeAdapter1 extends BaseExpandableListAdapter {
	
	private Context mcontext;
	private List<List<RecordObj>> mgroup_list;
	private String[] flags = {"录入物品","待购物品"};
	private boolean mwhere , mowner;

	public ComputeAdapter1(Context context,List<List<RecordObj>> group_list, boolean where, boolean owner) {
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
			convertView = View.inflate(mcontext, R.layout.item_chat1, null);
			childHolder.lanmu_gv = (MyGridView) convertView.findViewById(R.id.lanmu_gv);
			convertView.setTag(childHolder);
			GridAdapter gd = new GridAdapter(mcontext,mgroup_list.get(groupPosition) , mwhere , mowner);
			childHolder.lanmu_gv.setAdapter(gd);
		}else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return  1;
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
		private MyGridView lanmu_gv;
	}
}
