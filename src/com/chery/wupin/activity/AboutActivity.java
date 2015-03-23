package com.chery.wupin.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.chery.wupin.R;

public class AboutActivity extends Activity {
	
	private ImageView back_iv;
	private TextView name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		setUpView();
		setUpEvent();
	}

	private void setUpView() {
		
		name = (TextView) findViewById(R.id.name);
		back_iv = (ImageView) findViewById(R.id.back_iv);
		
		name.setText("¹ØÓÚ");
	}

	private void setUpEvent() {
		
		back_iv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
