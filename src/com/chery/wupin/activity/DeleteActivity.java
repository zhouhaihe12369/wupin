package com.chery.wupin.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.App;
import com.chery.wupin.R;
import com.chery.wupin.bean.Category;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.dao.CategoryDao;
import com.chery.wupin.dao.RecordDao;
import com.chery.wupin.db.SQLHelper;

public class DeleteActivity extends Activity {

	private Button cancel_bt , delete_bt;
	private LinearLayout delete_ll;
	private boolean recycle;
	private Intent it;
	private String success = "删除成功";
	private String faile = "删除失败";
	private Category cg;
	private RecordObj reb;
	private String flags = "待购物品";
	private String reason;
	private EditText reason_et;
	private String[] flag = {"分类" , "成员" , "地点"};
	private TextView delete_tv;
	private List<RecordObj> list = null;
	private RecordDao rd = new RecordDao(DeleteActivity.this);
	private CategoryDao cd = new CategoryDao(DeleteActivity.this);
	private String name;
	private String cg_str;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.delete);
		setUpView();
		setUpEvent();
	}

	private void setUpView() {
		cancel_bt = (Button) findViewById(R.id.cancel_bt);
		delete_bt = (Button) findViewById(R.id.delete_bt);
		delete_ll = (LinearLayout) findViewById(R.id.delete_ll);
		reason_et = (EditText) findViewById(R.id.reason_et);
		View view = findViewById(R.id.view1);
		delete_tv = (TextView) findViewById(R.id.delete_tv);
		TextView tv_name = (TextView) findViewById(R.id.delete_name_tv);
		RelativeLayout delete_rl = (RelativeLayout) findViewById(R.id.delete_rl);
		
		it = getIntent();
		recycle = it.getBooleanExtra("recycle", false);
		cg = (Category) it.getExtras().getSerializable("ct");
		reb = (RecordObj) it.getExtras().getSerializable("reobj");
		if (recycle) {
			delete_tv.setText("是否彻底删除该记录？");
			setLayout(view, delete_rl);
		} 
		else if (cg != null) {
			cg_str = cg.getCategory();
			name = cg.getName();
			if (flag[0].equals(name)) {
				list = rd.findByWhere(SQLHelper.CTATOGERY + "=?", new String[]{cg_str}, null);
			}
			else if(flag[1].equals(name)){
				list = rd.findByWhere(SQLHelper.OWNER + "=?", new String[]{cg_str}, null);
			}
			else if(flag[2].equals(name)){
				list = rd.findByWhere(SQLHelper.ADDRESS + "=?", new String[]{cg_str}, null);
			}
			
			if (list.size() != 0) {
				delete_tv.setText("删除该类别将会删除该类别下的所有记录,您确定删除吗？");
				reason_et.setVisibility(View.GONE);
				view.setVisibility(View.GONE);
			}else{
				delete_tv.setText("您确认删除该类别吗？");
				setLayout(view, delete_rl);
			}
		}
		else {
			delete_tv.setVisibility(View.GONE);
			view.setVisibility(View.VISIBLE);
			tv_name.setText("删除提示");
		}
	}

	private void setLayout(View view, RelativeLayout delete_rl) {
		reason_et.setVisibility(View.GONE);
		view.setVisibility(View.GONE);
		/*LayoutParams pam = delete_rl.getLayoutParams();
		pam.height = 200;
		delete_rl.setLayoutParams(pam);*/
	}

	private void setUpEvent() {
		
		cancel_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		delete_bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (recycle) {
					RecordObj rbs = (RecordObj) getIntent().getExtras().getSerializable("rbs");
					int id = rbs.get_id();
					boolean dele = rd.deleteInfo(SQLHelper._ID + "=?", new String[]{String.valueOf(id)} , true);
					setToast(dele);
				}
				else if(cg != null){
					int id = cg.get_id();
					boolean flags = cd.deleteInfo(SQLHelper._ID + "=?", new String[]{String.valueOf(id)});
					boolean delete = false;
					
					if (flag[0].equals(name)) {
						delete = rd.deleteInfo(SQLHelper.CTATOGERY + "=?", new String[]{cg_str},false);
					}
					else if(flag[1].equals(name)){
						delete = rd.deleteInfo(SQLHelper.OWNER + "=?", new String[]{cg_str},false);
					}
					else if(flag[2].equals(name)){
						delete = rd.deleteInfo(SQLHelper.ADDRESS + "=?", new String[]{cg_str},false);
					}
					setToast(flags);
				}
				else{
					reason = reason_et.getText().toString();
					if(TextUtils.isEmpty(reason)){
						Toast.makeText(DeleteActivity.this, "请输入删除原因", Toast.LENGTH_SHORT).show();
					}else{
						RecordObj reb = (RecordObj) it.getExtras().getSerializable("reobj");
						int id = reb.get_id();
						boolean dele = rd.deleteInfo(SQLHelper._ID + "=?", new String[]{String.valueOf(id)} , false);
						if(dele){
							reb.setReason(reason);
							boolean flag = rd.addObj(reb, true);
							setToast(flag);
							App.recordActivity.finish();
							App.isSave = true;
						}else{
							setToast(dele);
						}
					}
				}
			}
		});
		
		delete_ll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}
	
	private void setToast(boolean dele) {
		if(dele){
			Toast.makeText(DeleteActivity.this, success, Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(DeleteActivity.this, faile, Toast.LENGTH_SHORT).show();
		}
		finish();
	}
}
