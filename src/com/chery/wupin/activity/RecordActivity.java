package com.chery.wupin.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.App;
import com.chery.wupin.R;
import com.chery.wupin.bean.Category;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.dao.CategoryDao;
import com.chery.wupin.dao.RecordDao;
import com.chery.wupin.db.SQLHelper;

public class RecordActivity extends Activity implements OnClickListener{

	private TextView name , tvs;
	private ImageView back_iv,time_iv;
	private LinearLayout address_ll ,save_ll , delete , come_ll;
	private Button save_bt , buy_record_bt;
	private EditText name_et,time_et,come_et,price_et,comments_et;
	private Spinner type_et,owner_type , address_sp;
	
	protected static final int DATE_DIALOG_ID = 0;// 创建日期对话框常量
	private RecordDao rd = new RecordDao(RecordActivity.this);
	private RecordObj reobj;
	private Intent it;
	private boolean buy , modify , buy2;
	private ArrayAdapter<String> adapter;
	private CategoryDao cd = new CategoryDao(RecordActivity.this);
	private String[] flags = {"分类" , "成员" , "地点"};
	private App app;
	private int Catogery_type_id , Owner_type_id , Address_type_id , _ID , mYear , mMonth , mDay;// 年  月  日
	private String ca , ow , flag1;
	private String  ad = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		getWindow().setBackgroundDrawableResource(R.color.qianhui);
		initView();
		setUpView();
	}
	
	private void initView() {
		name = (TextView) findViewById(R.id.name);
		back_iv = (ImageView) findViewById(R.id.back_iv);
		address_ll = (LinearLayout) findViewById(R.id.address_ll);
		save_bt = (Button) findViewById(R.id.save_bt);
		save_ll = (LinearLayout) findViewById(R.id.save_ll);
		
		name_et = (EditText) findViewById(R.id.name_et);
		come_et = (EditText) findViewById(R.id.come_et);
		time_iv = (ImageView) findViewById(R.id.time_iv);
		time_et = (EditText) findViewById(R.id.time_et);
		price_et = (EditText) findViewById(R.id.price_et);
		comments_et = (EditText) findViewById(R.id.comments_et);
		type_et = (Spinner) findViewById(R.id.type_et);
		owner_type = (Spinner) findViewById(R.id.owner_type);
		address_sp = (Spinner) findViewById(R.id.address_sp);
		delete = (LinearLayout) findViewById(R.id.delete);
		buy_record_bt = (Button) findViewById(R.id.buy_record_bt);
		come_ll = (LinearLayout) findViewById(R.id.come_ll);
		tvs = (TextView) findViewById(R.id.tvs);
		
		back_iv.setOnClickListener(this);
		time_iv.setOnClickListener(this);
		save_bt.setOnClickListener(this);
		save_ll.setOnClickListener(this);
		buy_record_bt.setOnClickListener(this);
		App.recordActivity = this;
	}
	
	private void setUpView() {
		setTitle();
	}
	
	private void setTitle() {
		it = getIntent();
		String flag = it.getStringExtra("recordOrBuy");
		modify = it.getBooleanExtra("modify", false);
		RecordDao.flags = "录入物品";
		buy2 = "buy".equals(flag);
		app = new App(RecordActivity.this);
		if(modify){
			delete.setVisibility(View.VISIBLE);
			reobj = (RecordObj) it.getExtras().getSerializable("record");
			ca = reobj.getCatogery();
			ow = reobj.getOwner();
			_ID = reobj.get_id();
			getTimes();
			name.setText("修改物品");
			save_ll.setVisibility(View.VISIBLE);
			tvs.setVisibility(View.GONE);
			
			flag1 = reobj.getFlag();
			if ("待购物品".equals(flag1)) {
				setGone();
				buy_record_bt.setVisibility(View.VISIBLE);
			}else{
				ad = reobj.getAddress();
				setSpinner(flags[2] , address_sp ,"请选择物品放置地点:");
			}
		}else {
			if(buy2){
				name.setText("待购物品");
				setGone();
				RecordDao.flags = "待购物品";
				setDate();
			}else{
				setDate();
				setSpinner(flags[2] , address_sp ,"请选择物品放置地点:");
			}
		}
		setSpinner(flags[0] , type_et ,"请选择类别:");
		setSpinner(flags[1] , owner_type ,"请选择主人:");
		if(modify)setdefault(reobj);
	}
	
	private void setSpinner(String value , Spinner type_et , String prompt ) {
		List<Category> list = cd.findByWhere(SQLHelper.NAME + "=?", new String[]{value});
		String[] arr = app.setSpinner(list);
		setArry(arr , type_et , prompt);
		if(modify){
			for (int i = 0; i < arr.length; i++) {
				if (ca.equals(arr[i])) {
					Catogery_type_id = i;
				}
				else if (ow.equals(arr[i])) {
					Owner_type_id = i;
				}
				else if (ad != null) {
					if (ad.equals(arr[i]))Address_type_id = i;
				}
			}
		}
	}

	private void setArry(String[] arr , Spinner type_et , String prompt) {
		adapter = new ArrayAdapter<String>( this ,android.R.layout. simple_spinner_item , arr);
		adapter.setDropDownViewResource(android.R.layout. simple_spinner_dropdown_item );
		type_et .setAdapter(adapter);
		type_et .setPrompt(prompt);
	}

	private void setGone() {
		address_ll.setVisibility(View.GONE);
		come_ll.setVisibility(View.GONE);
	}
	
	private void saveInfo() {
		RecordObj rob = new RecordObj();
		String name = name_et.getText().toString().trim();
		String come = come_et.getText().toString().trim();
		String time = time_et.getText().toString().trim();
		String price = price_et.getText().toString().trim();
		String comments = comments_et.getText().toString().trim();
		String type = type_et.getSelectedItem().toString().trim();
		String owner = owner_type.getSelectedItem().toString().trim();
		int catogery_id = type_et.getSelectedItemPosition();
		int owner_id = owner_type.getSelectedItemPosition();
		if (TextUtils.isEmpty(name)) {
			setToast("请填写物品名称");
			return;
		}
		else if(TextUtils.isEmpty(price)){
			setToast("请填写物品价值");
			return;
		}
		getTimes();
		rob.setName(name);
		rob.setCome(come);
		try { 
			rob.setPrice(Integer.parseInt(price)); 
		} catch(NumberFormatException nfe) {  
		} 
		rob.setCatogery(type);
		rob.setOwner(owner);
		rob.setComments(comments);
		rob.setCatogery_type_id(catogery_id);
		rob.setOwner_type_id(owner_id);
		rob.setYear(String.valueOf(mYear));
		rob.setFlag(flag1);
		if(modify){
			rob.setTime(time);
			if (buy) {
				rob.setFlag("录入物品");
			} 
			if ("录入物品".equals(flag1)) {
				String address = address_sp.getSelectedItem().toString();
				int address_id = address_sp.getSelectedItemPosition();
				rob.setAddress(address);
				rob.setAddress_type_id(address_id);
			}
			boolean isModify = rd.updateInfo(rob, SQLHelper._ID + "=?", new String[]{String.valueOf(_ID)});
			if (buy) {
				if (isModify) {
					setToast("此物品已存在录入清单请设置地点");
					App.isSave = true;
				} else {
					setToast("买入失败");
				}
			} else {
				if (isModify) {
					setToast("修改成功");
					App.isSave = true;
				} else {
					setToast("修改失败");
				}
			}
		}else{
			if (!buy2) {
				String address = address_sp.getSelectedItem().toString();
				int address_id = address_sp.getSelectedItemPosition();
				rob.setAddress(address);
				rob.setAddress_type_id(address_id);
			}
			rob.setTime(getDate());
			insertInfo(rob);
		}
		finish();
	}

	private void insertInfo(RecordObj rob) {
		boolean isAdd = rd.addObj(rob , false);
		if (isAdd) {
			setToast("保存成功");
		} else {
			setToast("保存失败");
		}
	}

	private void setToast(String text) {
		Toast.makeText(RecordActivity.this, text, Toast.LENGTH_SHORT).show();
	}

	private String getDate() {
		String currentMonths = null;
		String currentDay = null;
		int currentMonth = mMonth + 1;
		if(currentMonth < 10){
			currentMonths = "0" +String.valueOf(currentMonth);
		}else{
			currentMonths = String.valueOf(currentMonth);
		}
		if(mDay < 10){
			currentDay = "0" +String.valueOf(mDay);
		}else{
			currentDay = String.valueOf(mDay);
		}
		String date = new StringBuilder().append(mYear).append("年")
				.append(currentMonths).append("月").append(currentDay).append("日").toString();
		return date;
	}
	
	private void setDate() {
		final Calendar c = Calendar.getInstance();// 获取当前系统日期
		mYear = c.get(Calendar.YEAR);// 获取年份
		mMonth = c.get(Calendar.MONTH);// 获取月份
		mDay = c.get(Calendar.DAY_OF_MONTH);// 获取天数
		String date = getDate();
		time_et.setHint(date);
	}

	private void getTimes() {
		try {
			String time = reobj.getTime();
			Date date = new SimpleDateFormat("yyyy年MM月dd日").parse(time);
			String now_tiem = new SimpleDateFormat("yyyy-MM-dd").format(date);
			String[] times = now_tiem.split("-");
			mYear = Integer.parseInt(times[0]);
			mMonth = Integer.parseInt(times[1]) - 1;
			mDay = Integer.parseInt(times[2]);
		} catch (Exception e) {
		}
	}

	private void setdefault(RecordObj reobj) {
		name_et.setText(reobj.getName());
		come_et.setText(reobj.getCome());
		time_et.setText(reobj.getTime());
		price_et.setText(String.valueOf(reobj.getPrice()));
		comments_et.setText(reobj.getComments());
		type_et.setSelection(Catogery_type_id , true);
		owner_type.setSelection(Owner_type_id , true);
		address_sp.setSelection(Address_type_id , true);
	}

	@Override
	protected Dialog onCreateDialog(int id)// 重写onCreateDialog方法
	{
		switch (id) {
		case DATE_DIALOG_ID:// 弹出日期选择对话框
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,mDay);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;// 为年份赋值
			mMonth = monthOfYear;// 为月份赋值
			mDay = dayOfMonth;// 为天赋值
			updateDisplay();// 显示设置的日期
		}
	};
	
	private void updateDisplay() {
		// 显示设置的时间
		String date = getDate();
		time_et.setText(date);
		if (reobj != null) {
			reobj.setTime(date);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.back_iv) {
			App.isSave = false;
			finish();
		}
		else if(v.getId() == R.id.time_iv){
			showDialog(DATE_DIALOG_ID);
		}
		else if(v.getId() == R.id.save_bt){
			buy = false;
			saveInfo();
		}
		else if(v.getId() == R.id.save_ll){
			if(modify){
				deleteInfo();
			}else{
				saveInfo();
			}
		}
		else if(v.getId() == R.id.buy_record_bt){
			buy = true;
			saveInfo();
		}
	}

	private void deleteInfo() {
		it = new Intent(RecordActivity.this,DeleteActivity.class);
		Bundle bd = new Bundle();
		bd.putSerializable("reobj", reobj);
		it.putExtras(bd);
		startActivity(it);
	}
	
	@Override
	public void onBackPressed() {
		App.isSave = false;
		super.onBackPressed();
	}
}
