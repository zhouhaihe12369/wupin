package com.chery.wupin.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.adapter.ChatAdapter;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.dao.RecordDao;
import com.chery.wupin.db.SQLHelper;

public class ChatActivity extends Activity implements OnClickListener{

	private ListView chat_lv;
	private ChatAdapter adapter; 
	private ImageView back_iv;
	private TextView chat_tv , name;
	private Button catogery_btn , address_btn , owner_btn , price_btn;
	private Intent it;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		setUpView();
		setUpEvent();
	}
	
	private void setUpView() {
		name = (TextView) findViewById(R.id.name);
		back_iv = (ImageView) findViewById(R.id.back_iv);
		
		catogery_btn = (Button) findViewById(R.id.catogery_btn);
		address_btn = (Button) findViewById(R.id.address_btn);
		owner_btn = (Button) findViewById(R.id.owner_btn);
		price_btn = (Button) findViewById(R.id.price_btn);
		
		catogery_btn.setOnClickListener(this);
		address_btn.setOnClickListener(this);
		owner_btn.setOnClickListener(this);
		price_btn.setOnClickListener(this);
		back_iv.setOnClickListener(this);
		
		name.setText("Í¼±íÍ³¼Æ");
	}
	
	private void setUpEvent() {
		
		
//		setAdapter();
	}

	private void setAdapter() {
		RecordDao rd = new RecordDao(ChatActivity.this);
		List<RecordObj>  list = rd.findAllObjs(SQLHelper.sql1);
		if (list.size() == 0) {
			chat_tv.setVisibility(View.VISIBLE);
		} else {
			adapter = new ChatAdapter(ChatActivity.this,list);
			chat_lv.setAdapter(adapter);
		}
	}

	@Override
	public void onClick(View v) {
		
		if (v.getId() == R.id.back_iv) {
			finish();
		} 
		else if(v.getId() == R.id.catogery_btn){
			it = new Intent(ChatActivity.this,ComputActivity.class);
			startActivity(it);
		}
		else if(v.getId() == R.id.price_btn){
			it = new Intent(ChatActivity.this,PriceActitivy.class);
			startActivity(it);
		}
	}
	
}
