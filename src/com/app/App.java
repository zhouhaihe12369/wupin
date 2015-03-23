package com.app;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.chery.wupin.R;
import com.chery.wupin.bean.Category;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.dao.CategoryDao;
import com.chery.wupin.db.SQLHelper;

public class App extends Application {
	
	public static Activity recordActivity;
	public String str_year , date , str_day , str_month;
	public int year , month , day;
	public List<RecordObj> list_all;
	public static boolean isSave = false;
	private Context mcontext;
	private List<Category> list;
	private String[] flags = {"分类" , "成员" , "地点"};
	
	public App(){
		Calendar calendar = Calendar.getInstance();
		month = calendar.get(Calendar.MONTH) + 1;
		year = calendar.get(Calendar.YEAR);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		str_year = String.valueOf(year);
		str_month = String.valueOf(month);
		str_day = String.valueOf(day);
		getDate();
	}
	
	public App(Context context){
		this.mcontext = context;
		String[] objs = mcontext.getResources().getStringArray(R.array.objtype);
		String[] ownertype = mcontext.getResources().getStringArray(R.array.ownertype);
		String[] places = mcontext.getResources().getStringArray(R.array.places);
		setSpinnerInfo(objs , flags[0]);
		setSpinnerInfo(ownertype , flags[1]);
		setSpinnerInfo(places , flags[2]);
	}
	
	public List<Category> setSpinnerInfo(String[] objs , String name) {
		CategoryDao cd = new CategoryDao(mcontext);
		list = cd.findByWhere(SQLHelper.NAME + "=?", new String[]{name});
		if (list.size() == 0) {
			for (int i = 0; i < objs.length; i++) {
				Category cg = new Category();
				cg.setCategory(objs[i]);
				cg.setName(name);
				cd.addObj(cg);
			}
			list = cd.findByWhere(SQLHelper.NAME + "=?", new String[]{name});
		} 
		return list;
	}
	
	private void getDate() {
		String currentMonths = null;
		if(month < 10){
			currentMonths = "0" +String.valueOf(month);
		}else{
			currentMonths = String.valueOf(month);
		}
		date = new StringBuilder().append(str_year).append("年").append(currentMonths).append("月").append(str_day).append("日").toString();
	}
	
	public String[] setSpinner(List<Category> list) {
		String[] arr = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i).getCategory();
		}
		return arr;
	}
}
