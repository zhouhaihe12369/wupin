package com.chery.wupin.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chery.wupin.R;
import com.chery.wupin.adapter.ListsAdapter1;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.dao.RecordDao;
import com.chery.wupin.db.SQLHelper;

public class PriceActitivy1 extends Activity {
	
	private ImageView back_iv;
	private ExpandableListView tongji_el;
	private List<RecordObj> list_recore , list_buy;
	private TextView name;
	private RecordDao rd;
	private EditText begain_et , end_et;
	private List<List<RecordObj>> group_list;
	private Button search_bt;
	private String begins , ends;
	private ListsAdapter1 adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tongji_seekbar1);
		setUpView();
		setUpEvent();
	}

	private void setUpView() {
		name = (TextView) findViewById(R.id.name);
		back_iv = (ImageView) findViewById(R.id.back_iv);
		tongji_el = (ExpandableListView) findViewById(R.id.tongji_el);
		begain_et = (EditText) findViewById(R.id.begain_et);
		end_et = (EditText) findViewById(R.id.end_et);
		search_bt = (Button) findViewById(R.id.search_bt);
		group_list = new ArrayList<List<RecordObj>>();
	}

	private void setUpEvent() {
		
		name.setText("价值统计");
		rd = new RecordDao(PriceActitivy1.this);
		
		back_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		search_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				begins = begain_et.getText().toString().trim();
				ends = end_et.getText().toString().trim();
				if (TextUtils.isEmpty(begins)) {
					Toast.makeText(PriceActitivy1.this, "请输入初始价值", Toast.LENGTH_SHORT).show();
				} else{
					findInfo();
					setUpList(group_list);
				}
			}
		});
	}
	
	private void setUpList(List<List<RecordObj>> group_list) {
		if (list_recore.size() == 0 && list_buy.size() == 0){
			Toast.makeText(PriceActitivy1.this, "无搜索结果", Toast.LENGTH_SHORT).show();
			tongji_el.setVisibility(View.GONE);
		}
		else{
			tongji_el.setVisibility(View.VISIBLE);
			adapter = new ListsAdapter1(PriceActitivy1.this,group_list);
			tongji_el.setAdapter(adapter);
	    	for(int i = 0; i < adapter.getGroupCount(); i++){
	    		tongji_el.expandGroup(i);
			}
		}
   	}
	
	private void findInfo() {
		String sql = null;
		group_list.clear();
		if (TextUtils.isEmpty(ends)) {
			sql = SQLHelper.PRICE + ">="+begins;
		} else {
			sql = SQLHelper.PRICE + ">="+begins + " AND " + SQLHelper.PRICE + "<="+ends;
		}
		String sql1 = SQLHelper.FLAG + "=" + "'录入物品'" + " AND ";
		String sql2 = SQLHelper.FLAG + "=" + "'待购物品'" + " AND ";
		list_recore = rd.findByWhere(sql1 + sql, null, SQLHelper.PRICE + " DESC");
		list_buy = rd.findByWhere(sql2 + sql, null, SQLHelper.PRICE + " DESC");
		group_list.add(list_recore);
		group_list.add(list_buy);
	}
	
	@Override
	protected void onRestart() {
		findInfo();
		setUpList(group_list);
		super.onRestart();
	}
}
