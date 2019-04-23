package com.xiwi.aide;

import com.iflytek.cloud.*;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import org.json.JSONArray;
import android.content.Intent;
import com.xiwi.aide.sqlite.XiwiSQLite;
import android.database.sqlite.SQLiteDatabase;
import com.xiwi.aide.fragment.FragmentHome;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.content.Context;
import android.widget.LinearLayout;
import com.xiwi.aide.fragment.FragmentThesaurus;
import android.transition.Visibility;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.app.Notification;


public class MainActivity extends Activity implements OnClickListener, ActivityTitleClickCallBack
{
    private TextView textView, resultTextView, setTextView;
    private Button startORoverBut;

    private SpeechSynthesizer mTts;
    private VoiceWakeuper mTvw;
    private SpeechRecognizer mIat;
    private String APPID = "5c6d4352";

    private SQLiteDatabase db;


    private LinearLayout linearMsg, linearHome, linearThesaurus;

    private FragmentThesaurus fragmentThesaurus;
    private FragmentHome fragmentHome;
    private FragmentManager fm;
    //private FragmentTransaction tx;

    //private NotificaitonManager n;
    
    private TextView leftTextView, titleTextView, rightTextView;

    
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    
    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        
        
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);;
        
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);  
        //设置小图标
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        //设置通知标题
        mBuilder.setContentTitle("测试中…………");
        //设置通知内容
        mBuilder.setContentText("测试notification中的使用包括图标，内容，时间等");
        //设置通知时间，默认为系统发出通知的时间，通常不用设置
        mBuilder.setWhen(System.currentTimeMillis());
        
        mNotification = mBuilder.build();
        mNotification.flags = mNotification.FLAG_NO_CLEAR;
        mNotificationManager.notify(1, mNotification);
        
        
        
        
        leftTextView = (TextView) findViewById(R.id.title_leftText);
        titleTextView = (TextView) findViewById(R.id.title_titleText);
        rightTextView = (TextView) findViewById(R.id.title_rightText);





        fragmentHome = new FragmentHome();
        fragmentThesaurus = new FragmentThesaurus();

        fm = getFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.add(R.id.frame, fragmentHome, "Home");
        tx.add(R.id.frame, fragmentThesaurus, "Thesaurus");
        tx.hide(fragmentThesaurus);
        ActivitySetting(new textViewClass("", View.INVISIBLE), "Home", new textViewClass("", View.INVISIBLE));
        tx.commit();




        linearMsg = (LinearLayout) findViewById(R.id.LinearMsg);
        linearHome = (LinearLayout) findViewById(R.id.LinearHome);
        linearThesaurus = (LinearLayout) findViewById(R.id.LinearThesaurus);

        linearMsg.setOnClickListener(this);
        linearHome.setOnClickListener(this);
        linearThesaurus.setOnClickListener(this);


        /*
         mBtn=findViewById(R.id.main_btn);
         mBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
         mFTwo=new Fragment_Two();
         FragmentManager fm=getFragmentManager();
         FragmentTransaction tx=fm.beginTransaction();
         tx.replace(R.id.fragment_content,mFTwo,"Two");
         tx.commit();
         }
         });
         */

        init();



        // 讯飞 - 初始化
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5c6d4352");

        // 语音合成
        mTts = SpeechSynthesizer.createSynthesizer(this, null);
        mTts.setParameter(SpeechConstant.PARAMS, null);
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");
        mTts.setParameter(SpeechConstant.SPEED, "50");
        mTts.setParameter(SpeechConstant.VOLUME, "80");
        //mTts.startSpeaking("张晓晓大傻逼", null);

        // 语音唤醒
        String resPath = ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "ivw/" + APPID + ".jet");
        mTvw = VoiceWakeuper.createWakeuper(this, null);
        mTvw.setParameter(SpeechConstant.PARAMS, null);
        mTvw.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
        mTvw.setParameter(SpeechConstant.IVW_RES_PATH, resPath);
        mTvw.setParameter(SpeechConstant.IVW_THRESHOLD, "0:80");
        mTvw.setParameter(SpeechConstant.IVW_SST, "wakeup");
        mTvw.setParameter(SpeechConstant.KEEP_ALIVE, "1");
        mTvw.setParameter(SpeechConstant.VAD_BOS, "2500");
        mTvw.setParameter(SpeechConstant.VAD_EOS, "1000");
        //mTvw.startListening(new mTvwListener());


        // 语音识别
        mIat = SpeechRecognizer.createRecognizer(this, null);
        mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
        mIat.setParameter(SpeechConstant.SUBJECT, null);
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
        mIat.setParameter(SpeechConstant.VAD_BOS, "2500");
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
        mIat.setParameter(SpeechConstant.ASR_PTT, "0");
        //mIat.startListening(mIatListener);

    }

    /**
     * 初始化
     */
    private void init ( ){

        XiwiSQLite xiwiSQLite = new XiwiSQLite(MainActivity.this, "xiwi_aide", 1);
        db = xiwiSQLite.getWritableDatabase();

    }

    private void TTSstartSpeaking ( String msg ){ mTts.startSpeaking(msg, null); }



    private void TshopORopen ( String type ){

        if ( type.equals("Tvw") ){
            if ( mTvw.isListening() ){
                //会话中
                mTvw.stopListening();
                mTvw.cancel();
            } else{
                mTvw.startListening(new mTvwListener());
                //mTvw.cancel();
            }

        } else if ( type.equals("Iat") ){
            if ( mIat.isListening() ){
                mIat.stopListening();
                mIat.cancel();
            } else {
                mIat.startListening(new mIatListener());
            }
        }

        /*else if( type.equals("Tts") ){
         if (mTts.isSpeaking()){
         //会话中
         mTts.stopSpeaking();
         //mTts.destroy();
         } else{
         // mTts.startListening(new mTvwListener());
         }
         }*/



    }
    // 实现类 - 语音唤醒 
    private class mTvwListener implements WakeuperListener{
        @Override
        public void onResult ( WakeuperResult result ){
            String str = result.getResultString();
            System.out.println(str);

            try{
                JSONObject object = new JSONObject(str);
                //toast(object.optString("score"));
                if ( Integer.decode(object.optString("score")) > 700 ){
                    //TTSstartSpeaking("啊~ 我在的呢。");
                    TshopORopen("Tvw");
                    TshopORopen("Iat");
                    toast("Tvw over");
                }
            }catch (JSONException e){

            }
            /*
             StringBuffer buffer = new StringBuffer();
             buffer.append("【RAW】" + str);
             */
            textView.setText(str);
        }
        @Override
        public void onError ( SpeechError error ){
            System.out.println(error.getPlainDescription(true));
        }
        @Override
        public void onBeginOfSpeech ( ){
            System.out.println("开始说话了！！！");
        }
        @Override
        public void onEvent ( int eventType, int isLast, int arg2, Bundle obj ){

        }
        @Override
        public void onVolumeChanged ( int i ){
            System.out.println("mIvw onVolumeChanged");
        }
    }

    // 语音识别
    private class mIatListener implements RecognizerListener{

        @Override
        public void onResult ( RecognizerResult result, boolean b ){
            String str = result.getResultString();
            System.out.println(str);

            resultTextView.append(str + "\n");

            //str = "";

            try{
                JSONObject json = new JSONObject(str);
                JSONArray jsonList = json.getJSONArray("ws");
                str = "";
                for ( int i=0; i < jsonList.length(); i++ ){
                    System.out.println(jsonList.get(i));

                    json = (JSONObject)(((JSONObject)jsonList.get(i)).getJSONArray("cw").get(0));
                    str += json.getString("w");
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
            if ( !str.equals("") ){
                resultTextView.append("str:" + str);
                resultTextView.append("\n");


                if ( str.equals("今天天气") ){
                    TTSstartSpeaking("今天天气真是尼玛的好！");
                }

            }


        }

        @Override
        public void onEvent ( int p1, int p2, int p3, Bundle bundle ){

        }

        @Override
        public void onVolumeChanged ( int p1, byte[] bytes ){
            System.out.println("mIat onVolumeChanged");
        }

        @Override
        public void onError ( SpeechError error ){
            System.out.println(error.getErrorDescription());
        }

        @Override
        public void onBeginOfSpeech ( ){
            System.out.println("begin");
        }

        @Override
        public void onEndOfSpeech ( ){
            System.out.println("end");
        }
    }

    @Override
    public void onClick ( View p1 )
    {
        // TODO: Implement this method
        System.out.println("底部按钮点击：" + p1.getId());

        FragmentTransaction tx = fm.beginTransaction();

        switch ( p1.getId() ){
            case R.id.LinearMsg:
            break;
            case R.id.LinearHome:
            tx.hide(fragmentThesaurus);
            tx.show(fragmentHome);
            ActivitySetting(new textViewClass("", View.INVISIBLE), "Home", new textViewClass("", View.INVISIBLE));
            tx.commit();
            break;
            case R.id.LinearThesaurus:
            tx.hide(fragmentHome);
            tx.show(fragmentThesaurus);
            ActivitySetting(new textViewClass("", View.INVISIBLE), "Thesaurus", new textViewClass("添加", View.VISIBLE));
            tx.commit();
            break;
        }

    }

    private void ActivitySetting ( textViewClass textView_left, String title, textViewClass textView_right ){

        leftTextView.setText(textView_left.getTitle());
        leftTextView.setVisibility(textView_left.getVisibility());

        titleTextView.setText(title);

        rightTextView.setText(textView_right.getTitle());
        rightTextView.setVisibility(textView_right.getVisibility());

    }

    private class textViewClass{
        private String title;
        private int visibility;

        public textViewClass ( String title, int visibility ){
            this.title = title;
            this.visibility = visibility;
        }

        public void setVisibility ( int visibility )
        {
            this.visibility = visibility;
        }

        public int getVisibility ( )
        {
            return visibility;
        }

        public void setTitle ( String title )
        {
            this.title = title;
        }

        public String getTitle ( )
        {
            return title;
        }
    }

    @Override
    public void topTextViewOnClick ( int id )
    {
        // TODO: Implement this method
        switch(id){
            case R.id.title_leftText:
                break;
                case R.id.title_rightText:
                    toast("right " + titleTextView.getText().toString());
                    
                    break;
        }
    }

    
    

    private void toast ( String msg ){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy ( )
    {
        // TODO: Implement this method
        mNotificationManager.cancelAll();
        super.onDestroy();
    }

    
    
    
}
