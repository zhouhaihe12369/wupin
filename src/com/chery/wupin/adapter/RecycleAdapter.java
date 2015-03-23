package com.chery.wupin.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.activity.DeleteActivity;
import com.chery.wupin.bean.RecordObj;

public class RecycleAdapter extends BaseAdapter {
	
	private Context mcontext;
	private List<RecordObj> mlist;

	public RecycleAdapter(Context context, List<RecordObj> list) {
		mcontext = context;
		mlist = list;
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
			convertView = View.inflate(mcontext, R.layout.item_recycle1, null);
			childHolder.recycle_rl = (RelativeLayout) convertView.findViewById(R.id.recycle_rl);
			childHolder.name_recycle_tv = (TextView) convertView.findViewById(R.id.name_recycle_tv);
			childHolder.type_recycle_tv = (TextView) convertView.findViewById(R.id.type_recycle_tv);
			childHolder.reason_recycle_tv = (TextView) convertView.findViewById(R.id.reason_recycle_tv);
			childHolder.time_recycle_tv = (TextView) convertView.findViewById(R.id.time_recycle_tv);
			childHolder.item_recycle_iv = (ImageView) convertView.findViewById(R.id.item_recycle_iv);
			childHolder.price_recycle_tv = (TextView) convertView.findViewById(R.id.price_recycle_tv);
			
			convertView.setTag(childHolder);
		}else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		final RecordObj rb = mlist.get(position);
		childHolder.time_recycle_tv.setText(rb.getTime());
		childHolder.reason_recycle_tv.setText("ÒÆ³ýÔ­Òò: " +  rb.getReason());
		childHolder.name_recycle_tv.setText(rb.getName());
		childHolder.type_recycle_tv.setText(rb.getCatogery());
		childHolder.price_recycle_tv.setText(rb.getPrice() + "" + "Ôª");
		String text = childHolder.reason_recycle_tv.getText().toString();
		if (text.length() > 50) {
			LayoutParams params = childHolder.recycle_rl.getLayoutParams();
			params.height = 100;
			childHolder.reason_recycle_tv.setLayoutParams(params);
			childHolder.reason_recycle_tv.setPadding(30, 65, 70, 0);
		}
		childHolder.item_recycle_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(mcontext,DeleteActivity.class);
				Bundle bd = new Bundle();
				bd.putSerializable("rbs", rb);
				it.putExtras(bd);
				it.putExtra("recycle", true);
				mcontext.startActivity(it);
			}
		});
		if (position % 3 == 0) {
			childHolder.item_recycle_iv.setBackgroundResource(R.drawable.bule_delete);
		}
		else if(position % 3 == 1){
			childHolder.item_recycle_iv.setBackgroundResource(R.drawable.yellow_delete);
		}
		else if(position % 3 == 2){
			childHolder.item_recycle_iv.setBackgroundResource(R.drawable.green_delete);
		}
		return convertView;
	}

	public class ChildHolder{
		private TextView name_recycle_tv;
		private TextView type_recycle_tv;
		private TextView reason_recycle_tv;
		private TextView time_recycle_tv;
		private TextView price_recycle_tv;
		private ImageView item_recycle_iv;
		private RelativeLayout recycle_rl;
	}
}
