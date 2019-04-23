package com.xiwi.aide.fragment;
import android.app.Fragment;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import com.xiwi.aide.R;
import android.widget.Button;
import android.view.View.OnClickListener;
import com.xiwi.aide.MainActivity;

public class FragmentHome extends Fragment
{

    private Button startORoverBut;
    
    @Override
    public View onCreateView ( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        // TODO: Implement this method
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated ( Bundle savedInstanceState )
    {
        // TODO: Implement this method
        super.onActivityCreated(savedInstanceState);

        startORoverBut = (Button) getActivity().findViewById(R.id.startORoverBut);

        /*
        textView = (TextView) getActivity().findViewById(R.id.textView);
        textView.setText("");
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        resultTextView.setText("");

        setTextView = (TextView) findViewById(R.id.setTextView);
        setTextView.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, ThesaurusActivity.class);
                startActivity(intent);
            }
        });*/

        startORoverBut.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){

                if(startORoverBut.getText().toString().equals("start")){
                    //mTvw.startListening(new mTvwListener());
                    startORoverBut.setText("over");
                } else {
                    //mTvw.stopListening();
                    //mTvw.cancel();
                    //toast(getActivity(),"over");
                    startORoverBut.setText("start");
                }
            }
        });
        
        
    }
    
    
    
}
