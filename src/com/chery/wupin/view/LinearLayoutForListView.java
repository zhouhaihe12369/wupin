package com.chery.wupin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chery.wupin.adapter.AdapterForLinearLayout;

public class LinearLayoutForListView extends LinearLayout {

    private AdapterForLinearLayout adapter;
    private OnClickListener onClickListener = null;

    /**
     * �󶨲���
     */
    public void bindLinearLayout() {
        int count = adapter.getCount();
        removeAllViews();
        for (int i = 0; i < count; i++) {
            View v = adapter.getView(i, null, null);
            v.setOnClickListener(this.onClickListener);
            addView(v, i);
        }
        Log.v("countTAG", "" + count);
    }

    public LinearLayoutForListView(Context context) {
        super(context);

    }

    public LinearLayoutForListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * ��ȡAdapter
     * 
     * @return adapter
     */
    public AdapterForLinearLayout getAdpater() {
        return adapter;
    }

    /**
     * ��������
     * 
     * @param adpater
     */
    public void setAdapter(AdapterForLinearLayout adpater) {
        this.adapter = adpater;
        bindLinearLayout();
    }

    /**
     * ��ȡ����¼�
     * 
     * @return
     */
    public OnClickListener getOnclickListner() {
        return onClickListener;
    }

    /**
     * ���õ���¼�
     * 
     * @param onClickListener
     */
    public void setOnclickLinstener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

}