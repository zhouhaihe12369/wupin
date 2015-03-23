package com.chery.wupin.activity;


import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.App;
import com.chery.wupin.R;
import com.chery.wupin.adapter.CategoryAdapter;
import com.chery.wupin.bean.Category;

public class CategoryActivity extends Activity implements OnClickListener{
	
	private TextView name;
	private ImageView back_iv , top_add , add_iv;
	private ListView category_lv;
	private boolean add = true;
	private CategoryAdapter adapter;
	private String[] flags = {"分类" , "成员" , "地点"};
	private String flag;
	private List<Category> list;
	private App app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.category_manager);
		setUpView();
		setUpEvent();
		app = new App(CategoryActivity.this);
	}

	private void setUpView() {
		name = (TextView) findViewById(R.id.name);
		back_iv = (ImageView) findViewById(R.id.back_iv);
		top_add = (ImageView) findViewById(R.id.top_add);
		add_iv = (ImageView) findViewById(R.id.add);
		
		category_lv = (ListView) findViewById(R.id.category_lv);
		add_iv.setVisibility(View.VISIBLE);
	}
	
	private void setUpEvent() {
		Intent it = getIntent();
		flag = it.getStringExtra("flag");
		back_iv.setOnClickListener(this);
		top_add.setOnClickListener(this);
	}
	
	private void setList() {
		if (flags[0].equals(flag)) {
			String[] objs = this.getResources().getStringArray(R.array.objtype);
			list = app.setSpinnerInfo(objs , flags[0]);
			name.setText("分类管理");
			adapter = new CategoryAdapter(CategoryActivity.this,list,0);
			category_lv.setAdapter(adapter);
		} 
		else if(flags[1].equals(flag)){
			String[] objs = this.getResources().getStringArray(R.array.ownertype);
			list = app.setSpinnerInfo(objs , flags[1]);
			name.setText("成员管理");
			adapter = new CategoryAdapter(CategoryActivity.this,list,1);
			category_lv.setAdapter(adapter);
		}
		else if(flags[2].equals(flag)){
			String[] objs = this.getResources().getStringArray(R.array.places);
			list = app.setSpinnerInfo(objs , flags[2]);
			name.setText("地点管理");
			adapter = new CategoryAdapter(CategoryActivity.this,list,2);
			category_lv.setAdapter(adapter);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.back_iv) {
			finish();
		} 
		else if(v.getId() == R.id.top_add){
			Intent it = new Intent(CategoryActivity.this,EditActivity.class);
			it.putExtra("add", add);
			it.putExtra("flag", flag);
			startActivity(it);
		}
	}
	
	@Override
	protected void onResume() {
		setList();
		super.onResume();
	}
}
