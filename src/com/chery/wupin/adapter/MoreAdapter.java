package com.chery.wupin.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.view.LockPatternUtils;

public class MoreAdapter extends BaseAdapter {
	
	private Context mcontext;
	private String[] name = {"密码保护" , "分类管理" , "意见反馈" ,"分享好友" ,"关于"};
	private int[] drawbles = {R.drawable.icon_security , R.drawable.icon_category ,R.drawable.icon_feedback ,R.drawable.share_with_friend,R.drawable.icon_about};
	
	

	public MoreAdapter(Context context) {
		this.mcontext = context;
	}

	@Override
	public int getCount() {
		return name.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(mcontext, R.layout.item_more, null);
		TextView more_name_tv = (TextView) view.findViewById(R.id.more_name_tv);
		TextView more_close_tv = (TextView) view.findViewById(R.id.more_close_tv);
		ImageView more_iv = (ImageView) view.findViewById(R.id.more_iv);
		TextView more_manager_tv = (TextView) view.findViewById(R.id.more_manager_tv);
		
		more_name_tv.setText(name[position]);
		more_iv.setBackgroundResource(drawbles[position]);
		if(position == 0){
//			PasswordInfo pi = new PasswordInfo(mcontext);
//			boolean open = pi.getOpen();
			LockPatternUtils lock_util = new LockPatternUtils(mcontext);
			boolean save = lock_util.getSave();
			if(save){
				more_close_tv.setText("打开");
			}else{
				more_close_tv.setText("关闭");
			}
			more_close_tv.setVisibility(View.VISIBLE);
		}
		
		if(position == 1){
			more_manager_tv.setText("编辑您的物品类别");
		}
		return view;
	}

}
