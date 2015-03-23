package com.chery.wupin.activity;

import android.app.Activity;
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

public class FeedbackActivity extends Activity {
	
	private ImageView back_iv;
	private TextView name;
	private Button save_feedback_bt;
	private EditText feedback_et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedback);
		setUpView();
		setUpEvent();
	}

	private void setUpView() {
		name = (TextView) findViewById(R.id.name);
		back_iv = (ImageView) findViewById(R.id.back_iv);
		
		save_feedback_bt = (Button) findViewById(R.id.save_feedback_bt);
		feedback_et = (EditText) findViewById(R.id.feedback_et);
		
		name.setText("意见反馈");
	}

	private void setUpEvent() {
		
		back_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		save_feedback_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String feedback = feedback_et.getText().toString();
				if (TextUtils.isEmpty(feedback)) {
					Toast.makeText(FeedbackActivity.this, "请输入反馈信息,谢谢!", Toast.LENGTH_SHORT).show();
				} else {
					feedback_et.setText(null);
					Toast.makeText(FeedbackActivity.this, "发送成功,谢谢您对物品记一如既往的支持!", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
