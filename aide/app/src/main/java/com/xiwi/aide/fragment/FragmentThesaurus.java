package com.xiwi.aide.fragment;
import com.xiwi.aide.R;
import android.app.Fragment;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.text.Layout;
import com.xiwi.aide.sqlite.XiwiSQLite;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import com.xiwi.aide.model.thesaurus;
import android.widget.ListView;
import com.xiwi.aide.adapter.ListAdapter;
import android.widget.TextView;
import android.app.Activity;
import com.xiwi.aide.ActivityTitleClickCallBack;

public class FragmentThesaurus extends Fragment
{
    private SQLiteDatabase db;
    private ListView listView;
    
    private ActivityTitleClickCallBack activityTitleClickCallBack;

    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        
        
       //activityTitleClickCallBack.topTextViewOnClick(R.id.text
        
        // TODO: Implement this method
        View view = inflater.inflate(R.layout.fragment_thesaurus, container, false);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach ( Activity activity )
    {
        // TODO: Implement this method
        super.onAttach(activity);
        activityTitleClickCallBack = (ActivityTitleClickCallBack) activity;
        
    }
    private interface ThesaurusCallback{
        public void textViewID(int id);
    }

    
    
    
    
    @Override
    public void onActivityCreated ( Bundle savedInstanceState )
    {
        // TODO: Implement this method
        super.onActivityCreated(savedInstanceState);
        XiwiSQLite xiwiSQLite = new XiwiSQLite(getActivity(), "xiwi_aide", 1);
        db = xiwiSQLite.getWritableDatabase();


        ArrayList<thesaurus> thesaurusList = new ArrayList<thesaurus>();

        for ( int i=1; i <= 1000; i++ ){
            thesaurusList.add(new thesaurus("问" + i, "答" + i));
        }

        //thesaurusList.add(new thesaurus("问2", "答2"));


        listView = (ListView) getActivity().findViewById(R.id.listView);
        listView.setAdapter(new ListAdapter(getActivity(), thesaurusList));

        
        
    }


}
