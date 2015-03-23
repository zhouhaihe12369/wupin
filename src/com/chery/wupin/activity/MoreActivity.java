package com.chery.wupin.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bidaround.youtui_template.YouTuiViewType;
import cn.bidaround.youtui_template.YtTemplate;
import cn.bidaround.youtui_template.YtToast;
import cn.bidaround.ytcore.ErrorInfo;
import cn.bidaround.ytcore.YtShareListener;
import cn.bidaround.ytcore.data.ShareData;
import cn.bidaround.ytcore.data.YtPlatform;

import com.chery.wupin.R;
import com.chery.wupin.adapter.ShowAdapter;
import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.dao.RecordDao;
import com.chery.wupin.db.SQLHelper;
import com.chery.wupin.view.LockPatternUtils;
/*
 * �������
 */

public class MoreActivity extends Activity {

	private ListView more_lv;
	private ImageView back_iv;
	private LinearLayout more_ll;
	private RelativeLayout catogery_rl , password_rl , member_rl , place_rl , about , feedback_rl;
	private TextView more_close_tv , name;
	private boolean compute , where , owner;
	private String catogery , flag;
	private RelativeLayout share_rl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		YtTemplate.init(this);
		setUpView();
		setUpEvent();
	}

	private void setUpView() {
		more_lv = (ListView) findViewById(R.id.more_lv);
		name = (TextView) findViewById(R.id.name);
		back_iv = (ImageView) findViewById(R.id.back_iv);
		more_ll = (LinearLayout) findViewById(R.id.more_ll);
		catogery_rl = (RelativeLayout) findViewById(R.id.catogery_rl);
		member_rl = (RelativeLayout) findViewById(R.id.member_rl);
		place_rl = (RelativeLayout) findViewById(R.id.place_rl);
		password_rl = (RelativeLayout) findViewById(R.id.password_rl);
		about = (RelativeLayout) findViewById(R.id.about);
		more_close_tv = (TextView) findViewById(R.id.more_close_tv);
		feedback_rl = (RelativeLayout) findViewById(R.id.feedback_rl);
		share_rl = (RelativeLayout) findViewById(R.id.share_rl);
		
		Intent it = getIntent();
		compute = it.getBooleanExtra("compute", false);
		where = it.getBooleanExtra("where", false);
		owner = it.getBooleanExtra("owner", false);
		catogery = it.getStringExtra("name");
		flag = it.getStringExtra("flag");
		if (compute) {
			name.setText("��Ʒ�б�");
		} else {
			more_lv.setVisibility(View.GONE);
			more_ll.setVisibility(View.VISIBLE);
			name.setText("����");
		}
	}

	private void onRefresh(boolean where, boolean owner, String catogery,String flag) {
		RecordDao rd = new RecordDao(MoreActivity.this);
		List<RecordObj> list = null;
		if (owner) {
			list = rd.findByWhere(SQLHelper.OWNER + "=?" + "AND "+ SQLHelper.FLAG  + "=?", new String[]{catogery , flag}, null);
		} else {
			if (where) {
				list = rd.findByWhere(SQLHelper.ADDRESS + "=?" + "AND "+ SQLHelper.FLAG  + "=?", new String[]{catogery , flag}, null);
			}else {
				list = rd.findByWhere(SQLHelper.CTATOGERY + "=?" + "AND "+ SQLHelper.FLAG  + "=?", new String[]{catogery , flag}, null);
			}
		}
		more_lv.setVisibility(View.VISIBLE);
		more_ll.setVisibility(View.GONE);
		ShowAdapter adapter = new ShowAdapter(MoreActivity.this,list);
		more_lv.setAdapter(adapter);
	}
	
	private void setUpEvent() {
		
		back_iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		catogery_rl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goToActivity(CategoryActivity.class,"����");
			}
		});
		
		member_rl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goToActivity(CategoryActivity.class , "��Ա");
			}
		});

		place_rl.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View v) {
				goToActivity(CategoryActivity.class ,"�ص�");
			}
		});
		
		password_rl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goToActivity(PasswordActivity.class,"");
			}
		});
		
		about.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goToActivity(AboutActivity.class,"");
			}
		});
		
		feedback_rl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goToActivity(FeedbackActivity.class,"");
			}
		});
		
		share_rl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				Toast.makeText(MoreActivity.this, "���޴˹���,�����ڴ�!", Toast.LENGTH_SHORT).show();
				share();
			}
		});
	}
	
	private void share() {
		// ShareDataʹ�����ݷ������ͷ�������
		ShareData whiteViewShareData = new ShareData();
		whiteViewShareData.isAppShare = true;
		whiteViewShareData.setDescription("���ƻ������");
		whiteViewShareData.setTitle("��Ʒ�Ƿ���");
		whiteViewShareData.setText("С����Ƕ���ʹ����Ʒ��,��Ʒ���þӼ����������!�������� ");
		whiteViewShareData.setTarget_url("http://ws.yingyonghui.com/7b957c5397f7858c4fcd1033504a66c0/54814df5/com.chery.wupin.1416979269497.apk");
		whiteViewShareData.setImageUrl("http://app.xnimg.cn/application/logo48/20141203/13/50/LhE1p22e020051.jpg");
		// shareData.setImagePath(Environment.getExternalStorageDirectory()+YoutuiConstants.FILE_SAVE_PATH+"demo.png");
//		whiteViewShareData.setDescription("���ƻ������");
//		whiteViewShareData.setTitle("���Ʒ���");
//		whiteViewShareData.setText("ͨ�����ƻ�������������߼��д���Ϳ���ΪӦ����ӷ����ͻ��ֹ��ܣ����ṩ�꾡�ĺ�̨ͳ�����ݣ����˱���߱��ķ������⣬������Ҳ�ɽ����ֹ��ܵ������������з��������app�ϣ��������԰� ");
//		whiteViewShareData.setTarget_url("http://youtui.mobi/");
//		whiteViewShareData.setImageUrl("http://cdnup.b0.upaiyun.com/media/image/default.png");
		YtTemplate whiteGridTemplate = new YtTemplate(this, YouTuiViewType.WHITE_GRID, false);
		whiteGridTemplate.setScreencapVisible(false);
		whiteGridTemplate.setShareData(whiteViewShareData);

		YtShareListener whiteViewListener = new YtShareListener() {

			@Override
			public void onSuccess(ErrorInfo error) {
				YtToast.showS(MoreActivity.this, "����ɹ�");
			}

			@Override
			public void onPreShare() {

			}

			@Override
			public void onError(ErrorInfo error) {
				YtToast.showS(MoreActivity.this, "����ʧ��");
			}

			@Override
			public void onCancel() {
				YtToast.showS(MoreActivity.this, "����ȡ��");
			}
		};
		/** ��ӷ���������,��������߲���Ҫ����ص��¼��򲻱����� */
		whiteGridTemplate.addListener(YtPlatform.PLATFORM_QQ, whiteViewListener);
		whiteGridTemplate.addListener(YtPlatform.PLATFORM_QZONE, whiteViewListener);
		whiteGridTemplate.addListener(YtPlatform.PLATFORM_RENN, whiteViewListener);
		whiteGridTemplate.addListener(YtPlatform.PLATFORM_SINAWEIBO, whiteViewListener);
		whiteGridTemplate.addListener(YtPlatform.PLATFORM_TENCENTWEIBO, whiteViewListener);
		whiteGridTemplate.addListener(YtPlatform.PLATFORM_WECHAT, whiteViewListener);
		whiteGridTemplate.addListener(YtPlatform.PLATFORM_WECHATMOMENTS, whiteViewListener);
		whiteGridTemplate.addListener(YtPlatform.PLATFORM_WECHAT_FAVORITE, whiteViewListener);
		/**
		 * Ϊÿ��ƽ̨��ӷ�������,������������,�����ΪwhiteViewTemplate.setShareData(
		 * whiteviewShareData)���õķ�������
		 */
		whiteGridTemplate.addData(YtPlatform.PLATFORM_QQ, whiteViewShareData);
		whiteGridTemplate.addData(YtPlatform.PLATFORM_QZONE, whiteViewShareData);
		whiteGridTemplate.addData(YtPlatform.PLATFORM_RENN, whiteViewShareData);
		whiteGridTemplate.addData(YtPlatform.PLATFORM_SINAWEIBO, whiteViewShareData);
		whiteGridTemplate.addData(YtPlatform.PLATFORM_TENCENTWEIBO, whiteViewShareData);
		whiteGridTemplate.addData(YtPlatform.PLATFORM_WECHAT, whiteViewShareData);
		whiteGridTemplate.addData(YtPlatform.PLATFORM_WECHATMOMENTS, whiteViewShareData);
		whiteGridTemplate.addData(YtPlatform.PLATFORM_MESSAGE, whiteViewShareData);
		whiteGridTemplate.addData(YtPlatform.PLATFORM_EMAIL, whiteViewShareData);
		whiteGridTemplate.addData(YtPlatform.PLATFORM_MORE_SHARE, whiteViewShareData);
		whiteGridTemplate.show();
	}

	private void setOpenOrClose() {
		LockPatternUtils lock_util = new LockPatternUtils(MoreActivity.this);
		boolean save = lock_util.getSave();
		if(save){
			more_close_tv.setText("��");
		}else{
			more_close_tv.setText("�ر�");
		}
	}
	
	private void goToActivity(Class<?> activity , String value) {
		Intent it = new Intent(MoreActivity.this,activity);
		it.putExtra("flag", value);
		startActivity(it);
	}
	
	@Override
	protected void onResume() {
		if (compute) {
			onRefresh(where, owner, catogery, flag);
		} else {
			setOpenOrClose();
		}
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		// ����
		YtTemplate.release(this);
		finish();
		super.onDestroy();
	}
}
