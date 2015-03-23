package com.chery.wupin.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.adapter.ComputeAdapter1;
import com.chery.wupin.base.BaseSlidingFragmentActivity;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.dao.RecordDao;
import com.chery.wupin.db.SQLHelper;
import com.chery.wupin.slidingmenu.SlidingMenu;
import com.chery.wupin.slidingmenu.SlidingMenu.OnClosedListener;

public class ComputActivity extends BaseSlidingFragmentActivity {
	
	private TextView compute_name_tv , tongji_tv;
	private ExpandableListView category_el;
	private RecordDao rd;
	private List<List<RecordObj>> group_list;
	private List<RecordObj> list1 , list2;
	private ImageView compute_back_iv , compute_press_iv;
	private boolean where , member , catogery;
	private SlidingMenu sm;
	private ImageView classify_right , number_right , price_right , place_right;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tongji);
		initSlidingMenu();
		setUpView();
		setUpEvent();
	}

	private void setUpView() {
		compute_name_tv = (TextView) findViewById(R.id.compute_name_tv);
		compute_back_iv = (ImageView) findViewById(R.id.back_iv);
		tongji_tv = (TextView) findViewById(R.id.tongji_tv);
		category_el = (ExpandableListView) findViewById(R.id.category_el);
		compute_press_iv = (ImageView) findViewById(R.id.compute_press_iv);
		compute_name_tv.setText("类别统计");
		rd = new RecordDao(ComputActivity.this);
	}

	private void setUpEvent() {
		
		compute_back_iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		compute_press_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showMenu();
				compute_press_iv.setBackgroundResource(R.drawable.ic_right_normal);
			}
		});
		
		price_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(ComputActivity.this,PriceActitivy1.class);
				startActivity(it);
				toggle();
			}
		});
		
		place_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				catogery = false;
				member = false;
				where = true;
				compute_name_tv.setText("地点统计");
				getList(SQLHelper.sql3 , SQLHelper.sql4);
				setAdapters(true , false);
				toggle();
			}
		});
		
		number_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				catogery = false;
				member = true;
				where = false;
				compute_name_tv.setText("成员统计");
				getList(SQLHelper.sql5 , SQLHelper.sql6);
				setAdapters(false , true);
				toggle();
			}
		});
		
		classify_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				catogery = true;
				member = false;
				where = false;
				compute_name_tv.setText("分类统计");
				getList(SQLHelper.sql1 , SQLHelper.sql2);
				setAdapters(false, false);
				sm.showMenu(false);
				toggle();
			}
		});
	}
	
	private void getList(String a , String b) {
		group_list = new ArrayList<List<RecordObj>>();
		list1 = rd.findAllObjs(a);
		group_list.add(list1);
		list2 = rd.findAllObjs(b);
		group_list.add(list2);
	}

	private void setAdapters(boolean where , boolean owner) {
		if (list1.size() == 0 && list2.size() == 0) {
			category_el.setVisibility(View.GONE);
			tongji_tv.setVisibility(View.VISIBLE);
		} else {
			ComputeAdapter1 adapter = new ComputeAdapter1(ComputActivity.this,group_list, where , owner);
			category_el.setAdapter(adapter);
			for(int i = 0; i < adapter.getGroupCount(); i++){
				category_el.expandGroup(i);
			}
		}
	}
	
    @Override
    protected void onResume() {
    	if (where) {
    		getList(SQLHelper.sql3 , SQLHelper.sql4);
    		setAdapters(true , false);
		} 
    	else if(member){
    		getList(SQLHelper.sql5 , SQLHelper.sql6);
    		setAdapters(false , true);
		}
    	else if(catogery){
    		getList(SQLHelper.sql1 , SQLHelper.sql2);
    		setAdapters(false, false);
		}
    	else {
    		getList(SQLHelper.sql1 , SQLHelper.sql2);
    		setAdapters(false, false);
		}
    	super.onResume();
    }
    
 // [start]初始化函数
 	private void initSlidingMenu() {
 		setBehindContentView(R.layout.behind_slidingmenu);
 		sm = getSlidingMenu();
 		sm.setMode(SlidingMenu.RIGHT);
 		sm.setShadowWidthRes(R.dimen.shadow_width);
 		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
 		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
 		sm.setShadowDrawable(R.drawable.slidingmenu_shadow);
 		sm.setShadowWidth(20);
 		classify_right = (ImageView) findViewById(R.id.classify_right);
 		number_right = (ImageView) findViewById(R.id.number_right);
 		price_right = (ImageView) findViewById(R.id.price_right);
 		place_right = (ImageView) findViewById(R.id.place_right);
 		sm.setOnClosedListener(new OnClosedListener() {
			
			@Override
			public void onClosed() {
				compute_press_iv.setBackgroundResource(R.drawable.ic_gash_normal);
			}
		});
 	}
 	
}
