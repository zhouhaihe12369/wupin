package com.chery.wupin.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.bean.RecordObj;

public class PriceAdapter extends BaseAdapter {
	
	private Context mcontext;
	private List<RecordObj> mlist;

	public PriceAdapter(Context context, List<RecordObj> list) {
		this.mcontext = context;
		this.mlist = list; 
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChildHolder childHolder = null;
		if(convertView == null){
			childHolder = new ChildHolder();
			convertView = View.inflate(mcontext, R.layout.item_price, null);
			childHolder.flag_price_tv = (TextView) convertView.findViewById(R.id.flag_price_tv);
			childHolder.time_price_tv = (TextView) convertView.findViewById(R.id.time_price_tv);
			childHolder.name_price_tv = (TextView) convertView.findViewById(R.id.name_price_tv);
			childHolder.price_tv = (TextView) convertView.findViewById(R.id.price_tv);
			childHolder.price_catogery_tv = (TextView) convertView.findViewById(R.id.price_catogery_tv);
			childHolder.price_owner_tv = (TextView) convertView.findViewById(R.id.price_owner_tv);
			childHolder.price_address_tv = (TextView) convertView.findViewById(R.id.price_address_tv);
			convertView.setTag(childHolder);
		}else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		final RecordObj rb = mlist.get(position);
		childHolder.flag_price_tv.setText(rb.getFlag());
		childHolder.time_price_tv.setText(rb.getTime());
		childHolder.name_price_tv.setText("物品名称: " + rb.getName());
		childHolder.price_tv.setText(rb.getPrice() + " 元");
		childHolder.price_catogery_tv.setText("物品类别: " + rb.getCatogery());
		childHolder.price_owner_tv.setText("物品主人: " + rb.getOwner());
		childHolder.price_address_tv.setText("放置地点: " + rb.getAddress());
		return convertView;
	}
	
	public class ChildHolder{
		private TextView flag_price_tv;
		private TextView time_price_tv;
		private TextView name_price_tv;
		private TextView price_tv;
		private TextView price_catogery_tv;
		private TextView price_owner_tv;
		private TextView price_address_tv;
	}

}
