package com.xiwi.sqlite;

import android.app.*;
import android.os.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class MainActivity extends Activity 
{
    
    private SQLiteDatabase db = null;
    private String sql = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        XiwiSQLite xiwiSQLite = new XiwiSQLite(this, "xiwi.db", 1);
        
        db = xiwiSQLite.getWritableDatabase();
        
        sql = "INSERT INTO xiwi(Keyword, Reply) values('key1','rep1') ";
        db.execSQL(sql);
        
        sql = "SELECT * FROM xiwi";
        Cursor cur = db.rawQuery(sql, null);
        while(cur.moveToNext()){
            String id = String.valueOf( cur.getInt(0) );
            String keyword = cur.getString(1);
            String reply = cur.getString(2);
            System.out.println(id + "\t" + keyword + "\t" + reply);
        }
        
        
    }
}
