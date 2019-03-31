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


public class MainActivity extends Activity 
{
    private TextView textView, resultTextView, setTextView;
    private Button startORoverBut;

    private SpeechSynthesizer mTts;
    private VoiceWakeuper mTvw;
    private SpeechRecognizer mIat;
    private String APPID = "5c6d4352";

    @Override
    protected void onCreate ( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        startORoverBut = (Button) findViewById(R.id.startORoverBut);
        
        textView = (TextView) findViewById(R.id.textView);
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
        });

        startORoverBut.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                
                if(startORoverBut.getText().toString().equals("start")){
                    mTvw.startListening(new mTvwListener());
                    startORoverBut.setText("over");
                } else {
                    mTvw.stopListening();
                    mTvw.cancel();
                    toast("over");
                    startORoverBut.setText("start");
                }
            }
        });
        
        
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
                for (int i=0; i<jsonList.length(); i++){
                    System.out.println(jsonList.get(i));
                   
                    json = (JSONObject)(((JSONObject)jsonList.get(i)).getJSONArray("cw").get(0));
                    str += json.getString("w");
                }
            }catch(JSONException e){
                e.printStackTrace();
            }
            if(!str.equals("")){
                resultTextView.append("str:" + str);
                resultTextView.append("\n");
                
                
                if(str.equals("今天天气")){
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


    private void toast ( String msg ){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
