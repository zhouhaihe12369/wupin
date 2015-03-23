package com.chery.wupin.activity;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.chery.wupin.R;
import com.chery.wupin.adapter.SearchAdapter;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.dao.RecordDao;

public class SearchActivity extends Activity {
	
	private ImageView back_search , search_iv;
	private ListView search_lv;
	private EditText search_et;
	private RecordDao rd = new RecordDao(SearchActivity.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);
		initView();
		setUpView();
	}

	private void initView() {
		back_search = (ImageView) findViewById(R.id.back_search);
		search_lv = (ListView) findViewById(R.id.search_lv);
		search_et = (EditText) findViewById(R.id.search_et);
		search_iv = (ImageView) findViewById(R.id.search_iv);
	}

	private void setUpView() {
		
		back_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		search_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setAdapter();
			}
		});
	}
	
	private void setAdapter() {
		String key = search_et.getText().toString().trim();
		if(!TextUtils.isEmpty(key)){
			/*for (int i = 0; i < list.size(); i++) {
				RecordObj rdo = list.get(i);
				String value = rdo.getName() + rdo.getCatogery() + rdo.getOwner() + rdo.getCome() + rdo.getAddress() +rdo.getComments();
				if(value.contains(key)){
					list_search.add(rdo);
				}
			}*/
			String id = "'%"+key+"%'";
			String name_like = " name Like " +  id;
			String catogery_like = " or catogery Like " +  id;
			String owner_like = " or owner Like " +  id;
			String come_like = " or come Like " +  id;
			String address_like = " or address Like " +  id;
			String comments_like = " or comments Like " +  id;
			String sql = "Select * From record Where"+ name_like + catogery_like + owner_like + come_like + address_like + comments_like;
			List<RecordObj> list_search = rd.findAllByKey(sql);
			if(list_search.size() != 0){
				setAdapter(list_search);
			}else{
				setAdapter(null);
				Toast.makeText(SearchActivity.this, "搜索记录不存在", Toast.LENGTH_SHORT).show();
			}
		}else{
			Toast.makeText(SearchActivity.this, "关键字不能为空", Toast.LENGTH_SHORT).show();
		}
	}

	private void setAdapter(List<RecordObj> list_search) {
		SearchAdapter sa = new SearchAdapter(SearchActivity.this, list_search);
		search_lv.setAdapter(sa);
	}
	
	@Override
	protected void onRestart() {
		setAdapter();
		super.onResume();
	}
}
