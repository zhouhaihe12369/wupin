package com.chery.wupin.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.adapter.RecycleAdapter;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.dao.RecordDao;

public class RecycleActivity extends Activity {

	private ListView recycle_lv;
	private RecordDao rd = new RecordDao(RecycleActivity.this);
	private TextView tishi_tv;
	private ImageView empty_recycle ,back_recycle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recycle);
		initView();
		setUpView();
	}

	private void initView() {
		back_recycle = (ImageView) findViewById(R.id.back_recycle);
		recycle_lv = (ListView) findViewById(R.id.recycle_lv);
		tishi_tv = (TextView) findViewById(R.id.tishi_tv);
		empty_recycle = (ImageView) findViewById(R.id.empty_recycle);
	}
	
	private void setUpView() {
		
		back_recycle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		empty_recycle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				rd.deleteInfo(null, null, true);
				setAdapter();
			}
		});
	}
	
	@Override
	protected void onResume() {
		setAdapter();
		super.onResume();
	}

	private void setAdapter() {
		List<RecordObj> list = rd.findAllObj(true , null,null);
		if (list.size() != 0) {
			RecycleAdapter ra = new RecycleAdapter(RecycleActivity.this, list);
			recycle_lv.setAdapter(ra);
		} else {
			recycle_lv.setAdapter(null);
			tishi_tv.setVisibility(View.VISIBLE);
		}
	}
}
