package com.chery.wupin.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class AdapterForLinearLayout extends BaseAdapter {

    private LayoutInflater mInflater;
    private int resource;
    private List<RecordObj> data;
    private Context context;

    public AdapterForLinearLayout(Context context,List<RecordObj> data, int resouce) {
    	this.context = context;
        this.data = data;
        this.resource = resouce;
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	convertView = mInflater.inflate(resource, null);
        TextView names = (TextView) convertView.findViewById(R.id.names);
        TextView time_child_tv = (TextView) convertView.findViewById(R.id.time_child_tv);
        TextView name_child_tv = (TextView) convertView.findViewById(R.id.name_child_tv);
        TextView price_child_tv = (TextView) convertView.findViewById(R.id.price_child_tv);
        TextView owner_child = (TextView) convertView.findViewById(R.id.owner_child);
        ImageView child_portrait = (ImageView) convertView.findViewById(R.id.child_portrait);
        RelativeLayout child_rl = (RelativeLayout) convertView.findViewById(R.id.child_rl);
        
        final RecordObj record = data.get(position);
        time_child_tv.setText(record.getTime());
		name_child_tv.setText(record.getCatogery());
		price_child_tv.setText(String.valueOf(record.getPrice())  + " Ԫ");
		names.setText(record.getName());
		owner_child.setText(record.getOwner());
		
		if (position % 4 == 0) {
			child_portrait.setBackgroundResource(R.drawable.ic_men1);
		} 
		else if(position % 4 == 1) {
			child_portrait.setBackgroundResource(R.drawable.ic_men2);
		}
		else if(position % 4 == 2) {
			child_portrait.setBackgroundResource(R.drawable.ic_woman1);
		}
		else if(position % 4 == 3) {
			child_portrait.setBackgroundResource(R.drawable.ic_woman2);
		}
		
		child_rl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(context,RecordActivity.class);
				it.putExtra("modify", true);
				Bundle bd = new Bundle();
				bd.putSerializable("record", record);
				it.putExtras(bd);
				context.startActivity(it);
			}
		});
		convertView.setTag(position);
        return convertView;
    }
}