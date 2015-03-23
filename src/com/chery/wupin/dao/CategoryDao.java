package com.chery.wupin.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chery.wupin.bean.Category;
import com.chery.wupin.db.SQLHelper;

public class CategoryDao {
	
	private SQLHelper helper = null;
	private SQLiteDatabase database = null;
	private Cursor cursor = null;

	public CategoryDao(Context context){
		helper = new SQLHelper(context);
	}
	
	//添加物品信息到数据库
	public boolean addObj(Category ceg) {
		boolean flag = false;
		long id = -1;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(SQLHelper.CTATOGERY, ceg.getCategory());
			values.put(SQLHelper.NAME, ceg.getName());
			id = database.insert(SQLHelper.TABLE_CATEGORY, null, values);
			flag = (id != -1 ? true : false);
		} catch (Exception e) {
			
		} finally {
			closeDatabase();
		}
		return flag;
	}
	
	//修改数据库中指定物品信息
	public boolean updateInfo(Category ct,String whereClause,String[] whereArgs) {
		boolean flag = false;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(SQLHelper.CTATOGERY, ct.getCategory());
			count = database.update(SQLHelper.TABLE_CATEGORY, values, whereClause, whereArgs);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
			
		} finally {
			closeDatabase();
		}
		return flag;
	}
	
	//删除数据库中指定的数据
	public boolean deleteInfo(String whereClause, String[] whereArgs) {
		boolean flag = false;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			count = database.delete(SQLHelper.TABLE_CATEGORY, whereClause, whereArgs);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
			
		} finally {
			closeDatabase();
		}
		return flag;
	}
	
	//从数据库中查询所有物品信息
	public List<Category> findByWhere(String selection , String[] selectionArgs) {
		// TODO Auto-generated method stub
		List<Category> record_list = new ArrayList<Category>();
		try {
			database = helper.getWritableDatabase();
			cursor = database.query(SQLHelper.TABLE_CATEGORY, null, selection,selectionArgs, null, null, null, null);
			while (cursor.moveToNext()) {
				Category rob = new Category();
				rob.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper._ID)));
				rob.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.CTATOGERY)));
				rob.setName(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.NAME)));
				record_list.add(rob);
			}
		} catch (Exception e) {
			
		} finally {
			closeDatabase();
		}
		return record_list;
	}
	
	//关闭数据库
	private void closeDatabase() {
		
		if (database != null) {
			database.close();
		}
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
	}
}
