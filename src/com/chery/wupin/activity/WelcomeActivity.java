package com.chery.wupin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.chery.wupin.R;
import com.chery.wupin.view.LockPatternUtils;

public class WelcomeActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        
        AlphaAnimation alpha = new AlphaAnimation(0.2f, 1.0f);
		alpha.setDuration(3000);
		findViewById(R.id.welcome_ll).startAnimation(alpha);
		
		alpha.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				/*PasswordInfo pi = new PasswordInfo(WelcomeActivity.this);
				boolean open = pi.getOpen();*/
				LockPatternUtils lock_util = new LockPatternUtils(WelcomeActivity.this);
				boolean save = lock_util.getSave();
				if(save){
					Intent it = new Intent(WelcomeActivity.this,LoginActivity.class);
					startActivity(it);
					finish();
				}else{
					Intent it = new Intent(WelcomeActivity.this,MainActivity.class);
					startActivity(it);
					finish();
				}
				
			}
		});
        
    }
}