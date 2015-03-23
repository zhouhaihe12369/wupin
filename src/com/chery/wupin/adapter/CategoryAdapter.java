package com.chery.wupin.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.activity.DeleteActivity;
import com.chery.wupin.activity.EditActivity;
import com.chery.wupin.bean.Category;

public class CategoryAdapter extends BaseAdapter {
	
	private Context mcontext ;
	private List<Category> mlist ;
	private Intent it;
	private int mflag;

	public CategoryAdapter(Context context,List<Category> list, int flag) {
		this.mcontext = context;
		this.mlist = list;
		this.mflag = flag;
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
			convertView = View.inflate(mcontext, R.layout.item_category, null);
			childHolder.category_name = (TextView) convertView.findViewById(R.id.category_name);
			childHolder.category_delete = (Button) convertView.findViewById(R.id.category_delete);
			childHolder.category_add = (Button) convertView.findViewById(R.id.category_add);
			childHolder.place_iv = (ImageView) convertView.findViewById(R.id.place_iv);
			convertView.setTag(childHolder);
		}else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		final Category ct = mlist.get(position);
		childHolder.category_name.setText(ct.getCategory());
		
		childHolder.category_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoActivity(ct , EditActivity.class);
			}
		});
		
		childHolder.category_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoActivity(ct , DeleteActivity.class);
			}
		});
		judgePlace(position, childHolder);
		return convertView;
	}

	private void judgePlace(int position, ChildHolder childHolder) {
		
		switch (mflag) {
		case 0:
			if (position % 4 == 0) {
				childHolder.place_iv.setBackgroundResource(R.drawable.bule_classify);
			} 
			else if(position % 4 == 1){
				childHolder.place_iv.setBackgroundResource(R.drawable.darkgreen_classify);
			}
			else if(position % 4 == 2){
				childHolder.place_iv.setBackgroundResource(R.drawable.yellow_classify);
			}
			else if(position % 4 == 3){
				childHolder.place_iv.setBackgroundResource(R.drawable.green_classify);
			}
			break;
		case 1:
			if (position % 4 == 0) {
				childHolder.place_iv.setBackgroundResource(R.drawable.ic_men1);
			} 
			else if(position % 4 == 1){
				childHolder.place_iv.setBackgroundResource(R.drawable.ic_men2);
			}
			else if(position % 4 == 2){
				childHolder.place_iv.setBackgroundResource(R.drawable.ic_woman1);
			}
			else if(position % 4 == 3){
				childHolder.place_iv.setBackgroundResource(R.drawable.ic_woman2);
			}
			break;
		case 2:
			if (position % 4 == 0) {
				childHolder.place_iv.setBackgroundResource(R.drawable.blue_place);
			} 
			else if(position % 4 == 1){
				childHolder.place_iv.setBackgroundResource(R.drawable.darkgreen_place);
			}
			else if(position % 4 == 2){
				childHolder.place_iv.setBackgroundResource(R.drawable.yellow_place);
			}
			else if(position % 4 == 3){
				childHolder.place_iv.setBackgroundResource(R.drawable.green_place);
			}
			break;
		}
	}
	
	public class ChildHolder{
		private TextView category_name;
		private Button category_add;
		private Button category_delete;
		private ImageView place_iv;
	}
	
	private void gotoActivity(final Category ct , Class<?> activity) {
		it = new Intent(mcontext,activity);
		Bundle bd = new Bundle();
		bd.putSerializable("ct", ct);
		it.putExtras(bd);
		mcontext.startActivity(it);
	}

}
