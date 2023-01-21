package com.example.sqlite;

//import static com.example.sqliteapi.database.MyDatabase.TABLE_NAME;
//import static com.example.sqliteapi.database.MyDatabase.db;

//import static com.example.sqlitedemo.MyDatabase.TABLE_NAME;

import static com.example.sqlite.MyDatabase.TABLE_NAME;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.sqliteapi.database.MyDatabase;
//import com.example.sqliteapi.model.Books;

import java.util.ArrayList;
import java.util.List;

public class BookVM {
    public List<Books> getAll(){
        List<Books> booksList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = MyDatabase.db;
        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM SACH",null);
        while (c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            int page = c.getInt(2);
            int price = c.getInt(3);
            String desc = c.getString(4);
            byte[] image = c.getBlob(5);
            booksList.add(new Books(id,name,page,price,desc,image));
//            System.out.println("List: "+booksList);
        }
        return booksList;
    }
    public long insertSQL(Books b){
        SQLiteDatabase sqLiteDatabase = MyDatabase.db;
//        b = new Books(b.getId(),b.getName(),b.getPage(),b.getPrice(),b.getDesc());
        ContentValues values = new ContentValues();
        values.put("id",b.getId());
        values.put("name",b.getName());
        values.put("page",b.getPage());
        values.put("price",b.getPrice());
        values.put("description",b.getDesc());
        values.put("image",b.getImage());
        return sqLiteDatabase.insert(TABLE_NAME,null,values);
    }

    public int updateSQL(Books books){
        SQLiteDatabase sqLiteDatabase = MyDatabase.db;
        ContentValues values = new ContentValues();
        values.put("id",books.getId());
        values.put("name",books.getName());
        values.put("page",books.getPage());
        values.put("price",books.getPrice());
        values.put("description",books.getDesc());
        values.put("image",books.getImage());
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(books.getId())};
        return sqLiteDatabase.update(TABLE_NAME,values,whereClause,whereArgs);
    }

    public int deleteSQL(Books books){
        SQLiteDatabase sqLiteDatabase = MyDatabase.db;
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(books.getId())};
        return sqLiteDatabase.delete(TABLE_NAME,whereClause,whereArgs);
    }

    public int deleteSQL(int id){
        SQLiteDatabase sqLiteDatabase = MyDatabase.db;
        String whereClause = "id = ?";
        String[] whereArgs = {id+""};
        return sqLiteDatabase.delete(TABLE_NAME,whereClause,whereArgs);
    }
}

