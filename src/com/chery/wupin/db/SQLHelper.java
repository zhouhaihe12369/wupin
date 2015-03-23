package com.chery.wupin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {
	
	public static final String DB_NAME = "Obj.db";
	public static final int DB_VERSON = 8;
	public static final String TABLE_RECORD = "record";//录入物品的数据库表名
	public static final String TABLE_DELETE = "deletes";//回收站数据库表名
	public static final String TABLE_CATEGORY = "catogery";//回收站数据库表名
	
	public String CREATE_TABLE_RECORD = "create table record (_id INTEGER PRIMARY KEY AUTOINCREMENT,name varchar(10),catogery varchar(10),owner varchar(10),"+
			"come varchar(10), time varchar(100), price integer, address varchar(100),comments varchar(100), catogery_id integer, owner_id integer, address_id integer, flag varchar(10) , year varchar(10))";
	
	public String CREATE_TABLE_DELETE = "create table deletes (_id INTEGER PRIMARY KEY AUTOINCREMENT,name varchar(10),catogery varchar(10),owner varchar(10),"+
			"come varchar(10), time varchar(100), price integer, address varchar(100),comments varchar(100), catogery_id integer, owner_id integer, flag varchar(10) , reason varchar(100))";
	
	public String CREATE_TABLE_CATEGORY = "create table catogery (_id INTEGER PRIMARY KEY AUTOINCREMENT,catogery varchar(10) , name varchar(20))";
	
	public static final String sql1 = "select catogery ,count(catogery) , flag from record where flag = '录入物品' group by catogery order by count(catogery) DESC";
	public static final String sql2 = "select catogery ,count(catogery) , flag from record where flag = '待购物品' group by catogery order by count(catogery) DESC";
	
	public static final String sql3 = "select address ,count(address) , flag from record where flag = '录入物品' group by address order by count(address) DESC";
	public static final String sql4 = "select address ,count(address) , flag from record where flag = '待购物品' group by address order by count(address) DESC";
	
	public static final String sql5 = "select owner ,count(owner) , flag from record where flag = '录入物品' group by owner order by count(owner) DESC";
	public static final String sql6 = "select owner ,count(owner) , flag from record where flag = '待购物品' group by owner order by count(owner) DESC";
	public static final String sql7 = "select * from record where flag='录入物品' order by _id desc limit 3";
	public static final String sql8 = "select * from record where flag='待购物品' order by _id desc limit 3";
	public static final String _ID = "_id";
	public static final String NAME = "name";
	public static final String CTATOGERY = "catogery";
	public static final String OWNER = "owner";
	public static final String COME = "come";
	public static final String TIME = "time";
	public static final String PRICE = "price";
	public static final String ADDRESS = "address";
	public static final String COMMENTS = "comments";
	public static final String CATOGERY_ID = "catogery_id";
	public static final String OWNER_ID = "owner_id";
	public static final String ADDRESS_ID = "address_id";
	public static final String FLAG = "flag";
	public static final String YEAR = "year";
	public static final String REASON = "reason";
	
	

	public SQLHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSON);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// TODO 创建数据库后，对数据库的操作
		db.execSQL(CREATE_TABLE_RECORD);
		db.execSQL(CREATE_TABLE_DELETE);
		db.execSQL(CREATE_TABLE_CATEGORY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 db.execSQL("ALTER TABLE record RENAME TO __temp__record");
		 db.execSQL("ALTER TABLE deletes RENAME TO __temp__deletes");
		 db.execSQL("ALTER TABLE catogery RENAME TO __temp__catogery");
		 
		 db.execSQL(CREATE_TABLE_RECORD);
		 db.execSQL(CREATE_TABLE_DELETE);
		 db.execSQL(CREATE_TABLE_CATEGORY);
		 
		 db.execSQL("INSERT INTO record SELECT * FROM __temp__record");
		 db.execSQL("INSERT INTO deletes SELECT * FROM __temp__deletes");
		 db.execSQL("INSERT INTO catogery SELECT * FROM __temp__catogery");
		 
		 db.execSQL("drop table __temp__record");
		 db.execSQL("drop table __temp__deletes");
		 db.execSQL("drop table __temp__catogery");
		 
	     //onCreate(db);
	}

}
