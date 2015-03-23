package com.chery.wupin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.view.LockPatternUtils;

public class PasswordActivity extends Activity {

	private ImageView password_iv , back_iv;
	private RelativeLayout password_rl;
	private TextView name;
	private LockPatternUtils lock_util;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password);
		setUpView();
		setUpEvent();
	}

	private void setUpView() {
		password_iv = (ImageView) findViewById(R.id.password_iv);
		password_rl = (RelativeLayout) findViewById(R.id.password_rl);
		name = (TextView) findViewById(R.id.name);
		back_iv = (ImageView) findViewById(R.id.back_iv);
		
		lock_util = new LockPatternUtils(PasswordActivity.this);
	}

	private void setUpEvent() {
		name.setText("√‹¬Î±£ª§");

		password_rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				boolean open = pi.getOpen();
				boolean save = lock_util.getSave();
				if (save) {
					password_iv.setBackgroundResource(R.drawable.widget_icon_switch_off);
//					pi.setOpen(false);
					lock_util.isSave(false);
					lock_util.clearLock();
				} else {
					Intent it = new Intent(PasswordActivity.this,LockActivity.class);
					startActivity(it);
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

	/*@Override
	protected void onResume() {
		boolean open = pi.getOpen();
		if (open) {
			password_iv.setBackgroundResource(R.drawable.widget_icon_switch_on);
		}
		super.onResume();
	}*/
	
	@Override
	protected void onResume() {
		boolean save = lock_util.getSave();
		if (save) {
			password_iv.setBackgroundResource(R.drawable.widget_icon_switch_on);
		}
		super.onRestart();
	}
}
