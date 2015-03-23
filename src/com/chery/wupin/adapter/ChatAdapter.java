package com.chery.wupin.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.bean.RecordObj;

public class ChatAdapter extends BaseAdapter {
	
	private Context mcontext;
	private List<RecordObj> mlist;

	public ChatAdapter(Context context, List<RecordObj> list) {
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
			convertView = View.inflate(mcontext, R.layout.item_chat, null);
			childHolder.chat_name_tv = (TextView) convertView.findViewById(R.id.chat_name_tv);
			childHolder.chat_count_tv = (TextView) convertView.findViewById(R.id.chat_count_tv);
			childHolder.chat_percent_tv = (TextView) convertView.findViewById(R.id.chat_percent_tv);
			childHolder.ProgressBar = (ProgressBar) convertView.findViewById(R.id.ProgressBar);
			convertView.setTag(childHolder);
		}else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		RecordObj rd = mlist.get(position);
		childHolder.chat_name_tv.setText(rd.getName());
		childHolder.chat_count_tv.setText(rd.getCount()+"По");
		
		int total_count = 0;
		for (int i = 0; i < mlist.size(); i++) {
			int count = mlist.get(i).getCount();
			total_count += count;
		}
		DecimalFormat df1 = new DecimalFormat("0.0000");  
		String str_percent = df1.format((float)rd.getCount() / total_count);
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
		
		return convertView;
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

	public class ChildHolder{
		private TextView chat_name_tv;
		private TextView chat_count_tv;
		private TextView chat_percent_tv;
		private ProgressBar ProgressBar;
	}
	
	private void doWork(int index) {  
		try {
			Thread.sleep(50);
		} catch (Exception e) {
		}
	}  

}
