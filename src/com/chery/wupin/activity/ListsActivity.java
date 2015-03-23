package com.chery.wupin.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.App;
import com.chery.wupin.R;
import com.chery.wupin.adapter.ListsAdapter;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.dao.RecordDao;
import com.chery.wupin.db.SQLHelper;

public class ListsActivity extends Activity {

	private TextView year_tv,num_tv , num_wait_tv , zongjia_tv , zongjia_wait_tv , list_tishi_tv;
	private String year;
	private ImageView back_list , search ;
	private RecordDao rd = new RecordDao(ListsActivity.this);
	private String[] flags = {"录入物品","待购物品"};
	private List<RecordObj> list_record,list_wait;
	private ExpandableListView list_el;
	private List<List<RecordObj>> group_list;
	private ListsAdapter adapter;
	private Button bt_pre , bt_next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list1);
		initView();
		setUpView();
	}

	private void initView() {
		year_tv = (TextView) findViewById(R.id.year_tv);
		back_list = (ImageView) findViewById(R.id.back_list);
		num_tv = (TextView) findViewById(R.id.num_tv);
		num_wait_tv = (TextView) findViewById(R.id.num_wait_tv);
		zongjia_tv = (TextView) findViewById(R.id.zongjia_tv);
		zongjia_wait_tv = (TextView) findViewById(R.id.zongjia_wait_tv);
		list_tishi_tv = (TextView) findViewById(R.id.list_tishi_tv);
		list_el = (ExpandableListView) findViewById(R.id.list_el);
		search = (ImageView) findViewById(R.id.search);
		bt_pre = (Button) findViewById(R.id.bt_pre);
		bt_next = (Button) findViewById(R.id.bt_next);
		
	    App app = new App();
		year = app.str_year;
	}
	
	private void setUpView() {
		year_tv.setText(year);
		back_list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		bt_pre.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				year = intToString((Integer.parseInt(year) - 1 ));
				year_tv.setText(year);
				onRefresh();
			}
		});
		
		bt_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				year = intToString((Integer.parseInt(year) + 1 ));
				year_tv.setText(year);
				onRefresh();
			}
		});
		
		search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(ListsActivity.this,SearchActivity.class);
				startActivity(it);
			}
		});
		
		setUpInfo();
		setUpList(flags,group_list);
	}
	
	private void setUpInfo() {
		group_list = new ArrayList<List<RecordObj>>();
    	list_record = rd.findByWhere(SQLHelper.FLAG + "=?" + "AND "+ SQLHelper.YEAR + "=?", new String[] {flags[0] , year},SQLHelper.TIME + " ASC");
    	group_list.add(list_record);
		list_wait = rd.findByWhere(SQLHelper.FLAG + "=?" + "AND "+ SQLHelper.YEAR + "=?", new String[] {flags[1] , year},SQLHelper.TIME + " ASC");
		group_list.add(list_wait);
		int record_size = list_record.size();
		int wait_size = list_wait.size();
		num_tv.setText(intToString(record_size));
		num_wait_tv.setText(intToString(wait_size));
		
		int total_price_record = getTotalPrice(list_record);
		int total_price_wait = getTotalPrice(list_wait);
		zongjia_tv.setText(intToString(total_price_record));
		zongjia_wait_tv.setText(intToString(total_price_wait));
		if(list_record.size()==0 && list_wait.size() == 0){
			list_el.setVisibility(View.GONE);
			list_tishi_tv.setVisibility(View.VISIBLE);
		}else {
			list_el.setVisibility(View.VISIBLE);
			list_tishi_tv.setVisibility(View.GONE);
		}
	}

	private int getTotalPrice( List<RecordObj> list) {
		int total_price = 0;
		for(int i = 0; i<list.size(); i++){
			int price = list.get(i).getPrice();
			total_price = total_price + price;
		}
		return total_price;
	}
	
	private String intToString(int a){
		return a + "";
	}

    private void setUpList(String[] flags ,List<List<RecordObj>> group_list) {
    	adapter = new ListsAdapter(ListsActivity.this,flags,group_list);
    	list_el.setAdapter(adapter);
    	for(int i = 0; i < adapter.getGroupCount(); i++){
    		list_el.expandGroup(i);
		}
   	}
    
    @Override
    protected void onRestart() {
    	if(App.isSave) {
    		onRefresh();
		} 
    	super.onRestart();
    }
    
    private void onRefresh(){
    	setUpInfo();
    	setUpList(flags,group_list);
    }
}
