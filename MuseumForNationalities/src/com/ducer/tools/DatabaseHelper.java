package com.ducer.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static String name = "suggest.db";
	private static int version = 1;
	private String create_sql = "create table if not exists SuggestTable (_id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT, datetime text)";
	private String drop_sql = "drop table if exists SuggestTable";

	public DatabaseHelper(Context context) {
		super(context, name , null, version );
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(create_sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(drop_sql );
		onCreate(db);
	}

}
