package com.xiwi.aide;
import android.app.Activity;
import android.os.Bundle;
import java.util.ArrayList;
import com.xiwi.aide.model.thesaurus;
import android.widget.ListView;
import com.xiwi.aide.adapter.ListAdapter;
import com.xiwi.aide.sqlite.XiwiSQLite;
import android.database.sqlite.SQLiteDatabase;

public class ThesaurusActivity extends Activity
{
    private SQLiteDatabase db;
    
    private ListView listView;
    
    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thesaurus);
        
        XiwiSQLite xiwiSQLite = new XiwiSQLite(this, "xiwi_aide", 1);
        db = xiwiSQLite.getWritableDatabase();
        
        
        ArrayList<thesaurus> thesaurusList = new ArrayList<thesaurus>();
        
        for(int i=1; i<=1000; i++){
            thesaurusList.add(new thesaurus("问"+i, "答"+i));
        }
        
        //thesaurusList.add(new thesaurus("问2", "答2"));
        
        
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ListAdapter(ThesaurusActivity.this, thesaurusList));
    }
    
}
