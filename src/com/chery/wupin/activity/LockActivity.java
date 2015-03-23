package com.chery.wupin.activity;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chery.wupin.R;
import com.chery.wupin.view.LockPatternUtils;
import com.chery.wupin.view.LockPatternView;
import com.chery.wupin.view.LockPatternView.Cell;
import com.chery.wupin.view.LockPatternView.DisplayMode;
import com.chery.wupin.view.LockPatternView.OnPatternListener;



public class LockActivity extends Activity implements OnClickListener {

	private LockPatternView lockPatternView;
	private LockPatternUtils lockPatternUtils;
	private TextView lock_tv , name;
	private Button reset_btn ;
	private ImageView back_iv;
	private String password;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock);
		setView();
		setEvent();
	}
	
	private void setView() {
		lockPatternView = (LockPatternView) findViewById(R.id.lpv_lock);
		lock_tv = (TextView) findViewById(R.id.lock_tv);
		reset_btn = (Button) findViewById(R.id.reset_btn);
		
		name = (TextView) findViewById(R.id.name);
		back_iv = (ImageView) findViewById(R.id.back_iv);
		
		name.setText("图案密码");
		
		reset_btn.setOnClickListener(this);
		back_iv.setOnClickListener(this);
	}

	private void setEvent() {
		
		lockPatternUtils = new LockPatternUtils(LockActivity.this);
		lockPatternUtils.clearLock();
		lockPatternView.setOnPatternListener(new OnPatternListener() {

			public void onPatternStart() {
			}

			public void onPatternDetected(List<Cell> pattern) {
				if(pattern.size() < 4){
					lock_tv.setText("请至少输入四个点");
					lock_tv.setTextColor(Color.RED);
				}
				else{
					password = lockPatternUtils.getLockPaternString();
					if (TextUtils.isEmpty(password)) {
						lockPatternUtils.saveLockPattern(pattern);
						lock_tv.setText("再次绘制图案进行确认");
						lock_tv.setTextColor(Color.GREEN);
						lockPatternView.clearPattern();
					} else{
						int result = lockPatternUtils.checkPattern(pattern);
						if (result == 1) {
							lockPatternUtils.isSave(true);
							finish();
						} else {
							lockPatternView.setDisplayMode(DisplayMode.Wrong);
							lock_tv.setText("与上次绘制不一致");
							lock_tv.setTextColor(Color.RED);
						}
					}
				}
			}

			public void onPatternCleared() {
			}

			public void onPatternCellAdded(List<Cell> pattern) {
			}
		});
	}

	public void onClick(View v) {
		
		if (v.getId() == R.id.reset_btn) {
			lockPatternUtils.clearLock();
			lockPatternView.clearPattern();
			lock_tv.setText("请绘制图案密码");
			lock_tv.setTextColor(Color.GREEN);
		}
		else if(v.getId() == R.id.back_iv){
			finish();
		}
	}

}
