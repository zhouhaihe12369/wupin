package com.chery.wupin.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.util.PasswordInfo;
import com.chery.wupin.view.LockPatternUtils;
import com.chery.wupin.view.LockPatternView;
import com.chery.wupin.view.LockPatternView.Cell;
import com.chery.wupin.view.LockPatternView.DisplayMode;
import com.chery.wupin.view.LockPatternView.OnPatternListener;

public class LoginActivity extends Activity {
	

	/*private String password;
	private String passwords;
	private EditText password_et;*/
	private LockPatternView lockPatternView;
	private LockPatternUtils lockPatternUtils;
	private TextView locks_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/*setContentView(R.layout.login);
		Button login_sure_bt = (Button) findViewById(R.id.login_sure_bt);
		password_et = (EditText) findViewById(R.id.password_et);
		
		PasswordInfo pi = new PasswordInfo(LoginActivity.this);
		password =  pi.getPassword();
		
		
		login_sure_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				passwords = password_et.getText().toString();
				if (!TextUtils.isEmpty(passwords)) {
					if (passwords.equals(password)) {
						Intent it = new Intent(LoginActivity.this,MainActivity.class);
						startActivity(it);
					} else {
						Toast.makeText(LoginActivity.this, "√‹¬Î¥ÌŒÛ£¨«Î÷ÿ–¬ ‰»Î", Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(LoginActivity.this, "√‹¬Î≤ªƒ‹Œ™ø’", Toast.LENGTH_SHORT).show();
				}
				
			}
		});*/
		setContentView(R.layout.login_chat);
		setUpEvent();
	}
	
	private void setUpEvent() {
		lockPatternUtils = new LockPatternUtils(LoginActivity.this);
		lockPatternView = (LockPatternView) findViewById(R.id.lpvs_lock);
		locks_tv = (TextView) findViewById(R.id.locks_tv);
		
		lockPatternView.setOnPatternListener(new OnPatternListener() {
			
			@Override
			public void onPatternStart() {
			}
			
			@Override
			public void onPatternDetected(List<Cell> pattern) {
				
				int result = lockPatternUtils.checkPattern(pattern);
				if (result == 1) {
					Intent it = new Intent(LoginActivity.this,MainActivity.class);
					startActivity(it);
				} else {
					lockPatternView.setDisplayMode(DisplayMode.Wrong);
					locks_tv.setText("Õº–Œ√‹¬Î¥ÌŒÛ«Î÷ÿ ‘");
					locks_tv.setTextColor(Color.RED);
				}
			}
			
			@Override
			public void onPatternCleared() {
			}
			
			@Override
			public void onPatternCellAdded(List<Cell> pattern) {
			}
		});
	}

	@Override
	protected void onRestart() {
		PasswordInfo pi = new PasswordInfo(LoginActivity.this);
		boolean open = pi.getOpen();
		if(!open){
			finish();
		}
		super.onRestart();
	}

}
