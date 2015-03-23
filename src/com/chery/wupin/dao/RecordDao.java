package com.chery.wupin.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chery.wupin.bean.RecordObj;
import com.chery.wupin.db.SQLHelper;

public class RecordDao {
	
	private SQLHelper helper = null;
	private SQLiteDatabase database = null;
	private Cursor cursor = null;
	public  static String flags = "录入物品";
	

	public RecordDao(Context context){
		helper = new SQLHelper(context);
	}
	
	//添加物品信息到数据库
	public boolean addObj(RecordObj reobj,boolean delete) {
		boolean flag = false;
		long id = -1;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(SQLHelper.NAME, reobj.getName());
			values.put(SQLHelper.CTATOGERY, reobj.getCatogery());
			values.put(SQLHelper.OWNER, reobj.getOwner());
			values.put(SQLHelper.COME, reobj.getCome());
			values.put(SQLHelper.TIME, reobj.getTime());
			values.put(SQLHelper.PRICE, reobj.getPrice());
			values.put(SQLHelper.ADDRESS, reobj.getAddress());
			values.put(SQLHelper.COMMENTS, reobj.getComments());
			values.put(SQLHelper.CATOGERY_ID, reobj.getCatogery_type_id());
			values.put(SQLHelper.OWNER_ID, reobj.getOwner_type_id());
			if(delete){
				values.put(SQLHelper.FLAG, reobj.getFlag());
				values.put(SQLHelper.REASON, reobj.getReason());
				id = database.insert(SQLHelper.TABLE_DELETE, null, values);
			}else{
				values.put(SQLHelper.ADDRESS_ID, reobj.getAddress_type_id());
				values.put(SQLHelper.YEAR, reobj.getYear());
				values.put(SQLHelper.FLAG, flags);
				id = database.insert(SQLHelper.TABLE_RECORD, null, values);
			}
			flag = (id != -1 ? true : false);
		} catch (Exception e) {
		} finally {
			closeDatabase();
		}
		return flag;
	}
	
	//从数据库中查询所有物品信息
	public List<RecordObj> findAllObj(boolean delete , String orderby , String groupBy) {
		List<RecordObj> record_list = new ArrayList<RecordObj>();
		try {
			database = helper.getWritableDatabase();
			if (delete) {
				cursor = database.query(true, SQLHelper.TABLE_DELETE, null, null,null, null, null, null, null);
			} else {
				cursor = database.query(true, SQLHelper.TABLE_RECORD, null, null,null, groupBy, null, orderby, null);
			}
			while (cursor.moveToNext()) {
				RecordObj rob = new RecordObj();
				rob.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper._ID)));
				rob.setName(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.NAME)));
				rob.setCatogery(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.CTATOGERY)));
				rob.setOwner(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.OWNER)));
				rob.setCome(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.COME)));
				rob.setTime(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.TIME)));
				rob.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper.PRICE)));
				rob.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.ADDRESS)));
				rob.setComments(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.COMMENTS)));
				rob.setOwner_type_id(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper.OWNER_ID)));
				rob.setCatogery_type_id(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper.CATOGERY_ID)));
				if (delete) {
					rob.setReason(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.REASON)));
				}else {
					rob.setAddress_type_id(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper.ADDRESS_ID)));
					rob.setFlag(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.FLAG)));
				}
				record_list.add(rob);
			}
		} catch (Exception e) {
		} finally {
			closeDatabase();
		}
		return record_list;
	}
	
	//从数据库中查询指定物品信息
	public List<RecordObj> findByWhere(String selection,String[] selectionArgs ,String orderBy) {
		List<RecordObj> record_list = new ArrayList<RecordObj>();
		try {
			database = helper.getReadableDatabase();
			cursor = database.query(true, SQLHelper.TABLE_RECORD, null, selection,selectionArgs, null, null, orderBy, null);
			while (cursor.moveToNext()) {
				RecordObj rob = new RecordObj();
				rob.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper._ID)));
				rob.setName(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.NAME)));
				rob.setCatogery(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.CTATOGERY)));
				rob.setOwner(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.OWNER)));
				rob.setCome(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.COME)));
				rob.setTime(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.TIME)));
				rob.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper.PRICE)));
				rob.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.ADDRESS)));
				rob.setComments(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.COMMENTS)));
				rob.setCatogery_type_id(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper.CATOGERY_ID)));
				rob.setOwner_type_id(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper.OWNER_ID)));
				rob.setAddress_type_id(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper.ADDRESS_ID)));
				rob.setFlag(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.FLAG)));
				record_list.add(rob);
			}
		} catch (Exception e) {
		} finally {
			closeDatabase();
		}
		return record_list;
	}
	
	//修改数据库中指定物品信息
	public boolean updateInfo(RecordObj reobj,String whereClause,String[] whereArgs) {
		boolean flag = false;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(SQLHelper.NAME, reobj.getName());
			values.put(SQLHelper.CTATOGERY, reobj.getCatogery());
			values.put(SQLHelper.OWNER, reobj.getOwner());
			values.put(SQLHelper.COME, reobj.getCome());
			values.put(SQLHelper.TIME, reobj.getTime());
			values.put(SQLHelper.PRICE, reobj.getPrice());
			values.put(SQLHelper.ADDRESS, reobj.getAddress());
			values.put(SQLHelper.COMMENTS, reobj.getComments());
			values.put(SQLHelper.CATOGERY_ID, reobj.getCatogery_type_id());
			values.put(SQLHelper.OWNER_ID, reobj.getOwner_type_id());
			values.put(SQLHelper.ADDRESS_ID, reobj.getAddress_type_id());
			values.put(SQLHelper.FLAG, reobj.getFlag());
			values.put(SQLHelper.YEAR, reobj.getYear());
			count = database.update(SQLHelper.TABLE_RECORD, values, whereClause, whereArgs);
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
		} finally {
			closeDatabase();
		}
		return flag;
	}
	
	//删除数据库中指定的数据
	public boolean deleteInfo(String whereClause, String[] whereArgs , boolean delete) {
		boolean flag = false;
		int count = 0;
		try {
			database = helper.getWritableDatabase();
			if (delete) {
				count = database.delete(SQLHelper.TABLE_DELETE, whereClause, whereArgs);
			}else{
				count = database.delete(SQLHelper.TABLE_RECORD, whereClause, whereArgs);
			}
			flag = (count > 0 ? true : false);
		} catch (Exception e) {
		} finally {
			closeDatabase();
		}
		return flag;
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
	
	//从数据库中查询所有物品信息
	public List<RecordObj> findAllObjs(String sql) {
		List<RecordObj> record_list = new ArrayList<RecordObj>();
		try {
			database = helper.getWritableDatabase();
			cursor = database.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				RecordObj rob = new RecordObj();
				rob.setName(cursor.getString(0));
				rob.setCount(cursor.getInt(1));
				rob.setFlag(cursor.getString(2));
				record_list.add(rob);
			}
		} catch (Exception e) {
		}finally{
			closeDatabase();
		}
		return record_list;
	}
	
	//按关键字从数据库中查询相关数据
	public List<RecordObj> findAllByKey(String sql) {
		List<RecordObj> record_list = new ArrayList<RecordObj>();
		try {
			database = helper.getWritableDatabase();
			cursor = database.rawQuery(sql, null);
			while (cursor.moveToNext()) {
				RecordObj rob = new RecordObj();
				rob.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper._ID)));
				rob.setName(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.NAME)));
				rob.setCatogery(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.CTATOGERY)));
				rob.setOwner(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.OWNER)));
				rob.setCome(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.COME)));
				rob.setTime(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.TIME)));
				rob.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper.PRICE)));
				rob.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.ADDRESS)));
				rob.setComments(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.COMMENTS)));
				rob.setCatogery_type_id(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper.CATOGERY_ID)));
				rob.setOwner_type_id(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper.OWNER_ID)));
				rob.setAddress_type_id(cursor.getInt(cursor.getColumnIndexOrThrow(SQLHelper.ADDRESS_ID)));
				rob.setFlag(cursor.getString(cursor.getColumnIndexOrThrow(SQLHelper.FLAG)));
				record_list.add(rob);
			}
		} catch (Exception e) {
		}finally{
			closeDatabase();
		}
		return record_list;
	}
}
