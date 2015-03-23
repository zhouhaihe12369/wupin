package com.chery.wupin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chery.wupin.R;
import com.chery.wupin.bean.Category;
import com.chery.wupin.dao.CategoryDao;
import com.chery.wupin.db.SQLHelper;
/*
 * 编辑界面
 */
public class EditActivity extends Activity {
	
	private TextView name;
	private LinearLayout save_ll;
	private ImageView back_iv , add;
	private CategoryDao cd;
	private boolean adds;
	private EditText edit_et;
	private Category ct;
	private String flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit);
		
		setUpView();
		setUpEvent();
	}

	private void setUpView() {
		name = (TextView) findViewById(R.id.name);
		save_ll = (LinearLayout) findViewById(R.id.save_ll);
		back_iv = (ImageView) findViewById(R.id.back_iv);
		add = (ImageView) findViewById(R.id.add);
		edit_et = (EditText) findViewById(R.id.edit_et);
		add.setVisibility(View.GONE);
		save_ll.setVisibility(View.VISIBLE);
		
		Intent it = getIntent();
		adds = it.getBooleanExtra("add", false);
		ct = (Category) it.getExtras().getSerializable("ct");
		if (adds) {
			flag = it.getStringExtra("flag");
			name.setText("添加");
		}else{
			name.setText("修改");
			edit_et.setText(ct.getCategory());
		}
		
		cd = new CategoryDao(EditActivity.this);
	}

	private void setUpEvent() {
		back_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		save_ll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String category = edit_et.getText().toString();
				if (!TextUtils.isEmpty(category)) {
					if (adds) {
						Category ceg = new Category();
						ceg.setCategory(category);
						ceg.setName(flag);
						boolean add = cd.addObj(ceg);
						setToast(add);
					}else{
						ct.setCategory(category);
						boolean updapte = cd.updateInfo(ct, SQLHelper._ID + "=?", new String[]{String.valueOf(ct.get_id())});
						setToast(updapte);
					}
				} else {
					Toast.makeText(EditActivity.this, "编辑内容不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void setToast(boolean add) {
		if (add) {
			Toast.makeText(EditActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(EditActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
		}
		finish();
	}
}
