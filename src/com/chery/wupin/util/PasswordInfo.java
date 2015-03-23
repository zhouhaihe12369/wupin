package com.chery.wupin.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class PasswordInfo {
	
	private SharedPreferences sp;
	private Context mcontext;

	public PasswordInfo(Context context){
		this.mcontext = context;
	}
	
	public Editor getEt(){
		sp = mcontext.getSharedPreferences("user_password", Activity.MODE_PRIVATE);
		return sp.edit();
	}
	
	public boolean getOpen(){
		sp = mcontext.getSharedPreferences("user_password", Activity.MODE_PRIVATE);
		boolean yesOrNo = sp.getBoolean("password_yes", false);
		return yesOrNo;
	}
	
	public String getPassword(){
		sp = mcontext.getSharedPreferences("user_password", Activity.MODE_PRIVATE);
		String password = sp.getString("password", null);
		return password;
	}
	
	public void setOpen(boolean value){
		sp = mcontext.getSharedPreferences("user_password", Activity.MODE_PRIVATE);
		Editor et = getEt();
		et.putBoolean("password_yes", value);
		et.commit();
	}

}
