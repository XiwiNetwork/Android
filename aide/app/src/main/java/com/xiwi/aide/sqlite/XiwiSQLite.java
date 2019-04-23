package com.xiwi.aide.sqlite;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class XiwiSQLite extends SQLiteOpenHelper
{
    private String tag = "XiwiSQLite.class";
    private String sql = null;

    /**
     * 创建数据库的构造方法
     * @param context 应用程序上下文
     * name 数据库的名字
     * factory 查询数据库的游标工厂一般情况下用sdk默认的
     * version 数据库的版本一般大于0
     */
    public XiwiSQLite(Context context, String dbName, int version) {
        super(context, dbName, null, version);
    }

    /**
     * 在数据库第一次创建时会执行
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(tag,"onCreate.....");
        
        // 词库表
        sql = "CREATE TABLE thesaurus ("
        + "Id integer primary key autoincrement,"
        + "Keyword varchar(200),"
        + "Reply varchar(200)"
        + " )";              
        db.execSQL(sql);
    }

    /**
     * 更新数据的时候调用的方法
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(tag,"onUpgrade*******");
        //增加一列
        //db.execSQL("alter table person add phone varchar(13) null");

    }
}
