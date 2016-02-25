package com.example.administrator.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/*
数据库每次操作之后自动关闭
 */
public class DBHelper extends SQLiteOpenHelper {
    //数据库版本
    private final static int VERSION = 1;
    //数据库名
    private final static String DB_NAME = "phones.db";
    //表名
    private final static String TABLE_NAME = "phone";
    //表字段
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_NAME = "name";
    private final static String COLUMN_SEX = "sex";
    private final static String COLUMN_NUMBER = "number";
    private final static String COLUMN_DESC = "desc";
    //创建表的sql语句
    String CREATE_TBL_SQL = "create table "+
            TABLE_NAME+" ( "+
            COLUMN_ID+" integer primary key autoincrement, "+
            COLUMN_NAME+" text not null, "+
            COLUMN_SEX+" text not null, "+
            COLUMN_NUMBER+" text not null, "+
            COLUMN_DESC+" text not null);";
    private SQLiteDatabase db;

    //SQLiteOpenHelper子类必须要的一个构造函数
    public DBHelper(Context context, String name, CursorFactory factory,int version) {
        //必须通过super 调用父类的构造函数
        super(context, name, factory, version);
    }

    //数据库的构造函数，传递三个参数的
    public DBHelper(Context context, String name, int version){
        this(context, name, null, version);
    }

    //数据库的构造函数，传递一个参数的， 数据库名字和版本号都写死了
    public DBHelper(Context context){
        this(context, DB_NAME, null, VERSION);
    }

    // 回调函数，第一次创建时才会调用此函数，创建一个数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        System.out.println("Create Database");
        db.execSQL(CREATE_TBL_SQL);
    }

    //回调函数，当你构造DBHelper的传递的Version与之前的Version调用此函数
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("update Database");

    }

    //插入方法
    public long insert(ContentValues values){
        //获取SQLiteDatabase实例
        db = getWritableDatabase();
        //插入数据库中
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    //查询全部数据方法
    public Cursor queryAll(){
        db = getReadableDatabase();
        //获取Cursor
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null, null);
        return c;

    }

    public Cursor queryById(int id){
        db = getReadableDatabase();
        //获取Cursor
        Cursor c = db.query(TABLE_NAME,null,COLUMN_ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        return c;
    }

    //根据唯一标识_id  来删除数据
    public int delete(int id){
        db = getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID+"=?", new String[]{String.valueOf(id)});
        return result;
    }


    //更新数据库的内容
    public int update(ContentValues values, String whereClause, String[]whereArgs){
        db = getWritableDatabase();
        int result=db.update(TABLE_NAME, values, whereClause, whereArgs);
        return result;
    }

    //关闭数据库
    public void close(){
        if(db != null){
            db.close();
        }
    }

}
