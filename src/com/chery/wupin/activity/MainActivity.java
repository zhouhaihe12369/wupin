package com.chery.wupin.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.App;
import com.chery.wupin.R;
import com.chery.wupin.adapter.AdapterForLinearLayout;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.dao.RecordDao;
import com.chery.wupin.db.SQLHelper;
import com.chery.wupin.view.LinearLayoutForListView;

public class MainActivity extends Activity implements OnClickListener{

	private RecordDao rd;
	private LinearLayoutForListView list_el;
	private AdapterForLinearLayout sa;
	private boolean remind = true;
	private Button record_bt , buy_bt;
	private List<RecordObj> list1 , list2;
	private TextView time , tvLogin,tvLogins , remind_buy_tv , remind_wait_tv;
	private LinearLayout recover_ll , list_ll , chat_ll , more_ll , buy_remind , buy_wait;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		initView();
		setViewEvent();
		setUpInfo();
	}

	private void initView() {
		record_bt = (Button) findViewById(R.id.record_bt);
		recover_ll = (LinearLayout) findViewById(R.id.recover_ll);
		time = (TextView) findViewById(R.id.time);
		tvLogin = (TextView) findViewById(R.id.tvLogin);
		tvLogins = (TextView) findViewById(R.id.tvLogins);
		buy_bt = (Button) findViewById(R.id.buy_bt);
		list_ll = (LinearLayout) findViewById(R.id.list_ll);
		chat_ll = (LinearLayout) findViewById(R.id.chat_ll);
		more_ll = (LinearLayout) findViewById(R.id.more_ll);
		buy_remind = (LinearLayout) findViewById(R.id.buy_remind);
		buy_wait = (LinearLayout) findViewById(R.id.buy_wait);
		list_el = (LinearLayoutForListView) findViewById(R.id.list_el);
		remind_buy_tv = (TextView) findViewById(R.id.remind_buy_tv);
		remind_wait_tv = (TextView) findViewById(R.id.remind_wait_tv);
		rd = new RecordDao(MainActivity.this);
		findInfo();
	}
	
	private void setViewEvent() {
		App app = new App();
		time.setText(app.date);
		setNumber();
		record_bt.setOnClickListener(this);
		recover_ll.setOnClickListener(this);
		buy_bt.setOnClickListener(this);
		list_ll.setOnClickListener(this);
		chat_ll.setOnClickListener(this);
		more_ll.setOnClickListener(this);
		buy_remind.setOnClickListener(this);
		buy_wait.setOnClickListener(this);
		
	}

	private void setNumber() {
		remind_buy_tv.setText(list1.size() + "");
		remind_wait_tv.setText(list2.size() + "");
	}
	
	private void setUpInfo() {
		RecordDao rd = new RecordDao(MainActivity.this);
		List<RecordObj> list_all = rd.findAllObj(false , null, null); 
		tvLogin.setText(list_all.size()+"" + " ผþ");
		int total_price = getTotalPrice(list_all);
		tvLogins.setText(total_price + "" + " ิช");
		
		App app = new App();
		app.list_all = list_all;
	}
	
	private int getTotalPrice( List<RecordObj> list) {
		int total_price = 0;
		for(int i = 0; i<list.size(); i++){
			int price = list.get(i).getPrice();
			total_price = total_price + price;
		}
		return total_price;
	}
	
	private void goToActivity(Class<?> activity,String value) {
		Intent it = new Intent(MainActivity.this,activity);
		it.putExtra("recordOrBuy", value);
		startActivity(it);
	}
	
	@Override
	protected void onRestart() {
		setUpInfo();
		findInfo();
		setNumber();
		super.onRestart();
	}
	private void findInfo(){
		list1 = rd.findAllByKey(SQLHelper.sql7);
		list2 = rd.findAllByKey(SQLHelper.sql8);
		if (remind) {
			setAdapters(list1);
		} else {
			setAdapters(list2);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.record_bt:
			goToActivity(RecordActivity.class,"record");
			break;
		case R.id.recover_ll:
			goToActivity(RecycleActivity.class,"recover");
			break;
		case R.id.buy_bt:
			goToActivity(RecordActivity.class,"buy");
			break;
		case R.id.list_ll:
			goToActivity(ListsActivity.class,"");
			break;
		case R.id.chat_ll:
			goToActivity(ComputActivity.class, "");
			break;
		case R.id.more_ll:
			goToActivity(MoreActivity.class, "");
			break;
		case R.id.buy_remind:
			setAdapters(list1);
			buy_wait.setBackgroundColor(Color.parseColor("#029D0E"));
			buy_remind.setBackgroundColor(Color.parseColor("#0A8300"));
			remind = true;
			break;
		case R.id.buy_wait:
			setAdapters(list2);
			buy_remind.setBackgroundColor(Color.parseColor("#029D0E"));
			buy_wait.setBackgroundColor(Color.parseColor("#0A8300"));
			remind = false;
			break;
		default:
			break;
		}
	}

	private void setAdapters(List<RecordObj> list) {
		sa = new AdapterForLinearLayout(this, list, R.layout.item_child);
		list_el.setAdapter(sa);
	}
}
