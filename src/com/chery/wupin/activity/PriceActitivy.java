package com.chery.wupin.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.adapter.PriceAdapter;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.dao.RecordDao;
import com.chery.wupin.db.SQLHelper;

public class PriceActitivy extends Activity {
	
	private ImageView back_iv;
	private ListView tongji_lv;
	private List<RecordObj> list;
	private TextView tv1 , tv2 , tv3 , tv4 , name;
	private SeekBar seekbar;
	private int a , b , c , max_price;
	private RecordDao rd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tongji_seekbar);
		setUpView();
		setUpEvent();
	}

	private void setUpView() {
		name = (TextView) findViewById(R.id.name);
		back_iv = (ImageView) findViewById(R.id.back_iv);
		tongji_lv = (ListView) findViewById(R.id.tongji_lv);
		seekbar = (SeekBar) findViewById(R.id.seekbar);
		tv1 = (TextView) findViewById(R.id.tv1);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv3 = (TextView) findViewById(R.id.tv3);
		tv4 = (TextView) findViewById(R.id.tv4);
	}

	private void setUpEvent() {
		
		name.setText("价值统计");
		rd = new RecordDao(PriceActitivy.this);
		setValue();
		seekbar.setMax(max_price);
		
		back_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
				String zero = 0 +"";
				String as = a+ "";
				String bs = b+ "";
				String cs = c+ "";
				String max_prices = max_price + "";
				if (progress > 0 && progress <= a) {
					getLists(SQLHelper.PRICE + ">"+ zero  + " AND " + SQLHelper.PRICE + "<="+as);
				}
				else if(progress > a && progress <= b){
					getLists(SQLHelper.PRICE + ">"+as + " AND " + SQLHelper.PRICE + "<="+bs);
				}
				else if(progress > b && progress <= c){
					getLists(SQLHelper.PRICE + ">"+bs + " AND " + SQLHelper.PRICE + "<="+cs);
				}
				else if(progress > c && progress <= max_price){
					getLists(SQLHelper.PRICE + ">"+cs + " AND " + SQLHelper.PRICE + "<="+max_prices);
				}
				setAdapter();
			}
		});
	}
	
	private void setValue() {
		getLists(null);
		max_price = list.get(0).getPrice();
		a = max_price / 4;
		b = max_price / 2;
		c = a + b;
		tv1.setText(getMoney(a));
		tv2.setText(getMoney(b));
		tv3.setText(getMoney(c));
		tv4.setText(getMoney(max_price));
	}

	private void getLists(String selection) {
		list = rd.findByWhere(selection, null, SQLHelper.PRICE + " DESC");
	}
	
	private void setAdapter() {
		PriceAdapter adapter = new PriceAdapter(PriceActitivy.this,list);
		tongji_lv.setAdapter(adapter);
	}
	
	private String getMoney(int value){
		return value + ""+ "元";
	}
}
