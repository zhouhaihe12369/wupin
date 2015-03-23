package com.chery.wupin.activity;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chery.wupin.R;
import com.chery.wupin.util.PasswordInfo;

public class SecurityActivity extends Activity {
	private EditText first_et ,second_et;
	private Button sure_bt;
	private TextView name;
	private ImageView back_iv;
	private PasswordInfo pi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.security);
		setUpView();
		setUpEvent();
	}

	private void setUpView() {
		first_et = (EditText) findViewById(R.id.first_et);
		second_et = (EditText) findViewById(R.id.second_et);
		sure_bt = (Button) findViewById(R.id.sure_bt);
		name = (TextView) findViewById(R.id.name);
		back_iv = (ImageView) findViewById(R.id.back_iv);
		pi = new PasswordInfo(SecurityActivity.this);
		boolean open = pi.getOpen();
		if (open) {
			pi.setOpen(false);
		}
	}
	
	private void setUpEvent() {
		name.setText("设置密码");
		sure_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String first = first_et.getText().toString();
				String second = second_et.getText().toString();
				if(!TextUtils.isEmpty(first) && !TextUtils.isEmpty(second) ){
					pi.setOpen(true);
					Editor et = pi.getEt();
					if(first.equals(second)){
						et.putString("password", second);
						finish();
					}else{
						Toast.makeText(SecurityActivity.this, "两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
					}
					et.commit();
				}else{
					Toast.makeText(SecurityActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
		back_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
