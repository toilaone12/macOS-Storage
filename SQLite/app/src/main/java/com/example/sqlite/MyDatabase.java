package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    public static final String DB_Name = "QLSach.sqlite";
    public static final int VERSION = 2;
    private static final String Tag = "QLS";
    public static final String TABLE_NAME = "SACH";
    public static SQLiteDatabase db;

    public MyDatabase(@Nullable Context context) {
        super(context, DB_Name, null, VERSION);
        db = getReadableDatabase();
    }

    public MyDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreate = "CREATE TABLE "+TABLE_NAME+"(id integer primary key autoincrement, name varchar(50), page integer, price float, description text)";
        sqLiteDatabase.execSQL(sqlCreate);
        Log.d(Tag,"OnCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String addColumn = "ALTER TABLE "+MyDatabase.TABLE_NAME+" ADD image blob";
        sqLiteDatabase.execSQL(addColumn);
    }

    public void execSQL(String sql){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        sqLiteDatabase.execSQL(sql);
    }

    public Cursor selectSQL(String sql){
        return getReadableDatabase().rawQuery(sql,null);
    }

//    public long insertSQL(String table, String nullCollumHack, ContentValues values){
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        return sqLiteDatabase.insert(table, nullCollumHack, values);
//    }
//    public int updateSQL(String table, String whereClause,String[] whereArgs, ContentValues values){
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        return sqLiteDatabase.update(table,values,whereClause,whereArgs);
//    }
//    public int deleteSQL(String table, String whereClause,String[] whereArgs){
//        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//        return sqLiteDatabase.delete(table,whereClause,whereArgs);
//    }
}
