package com.xiwi.wechat;

import android.app.*;
import android.os.*;
import java.util.concurrent.RunnableFuture;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import android.widget.TextView;
import java.util.Map;
import java.util.HashMap;

public class MainActivity extends Activity{

        private RequestQueue mQueue;
        private XiwiVolley.VolleyStringRequest strRequtst;


        @Override
        protected void onCreate(Bundle savedInstanceState){
                super.onCreate(savedInstanceState);
                setContentView(R.layout.main);

                mQueue = Volley.newRequestQueue(getApplicationContext(), null);

                strRequtst = new XiwiVolley.VolleyStringRequest(Request.Method.GET, "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=1476606163580", new Response.Listener<String>(){
                            @Override
                            public void onResponse(String str){
                                    ((TextView)findViewById(R.id.textView)).setText(str);
                                    System.out.println(str);
                                }
                        }, new Response.ErrorListener(){
                            @Override
                            public void onErrorResponse(VolleyError volleyError){

                                }
                        });
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.5 (KHTML, like Gecko) Chrome/19.0.1084.52 Safari/536.5");
                strRequtst.setHeaders(headers);
                mQueue.add(strRequtst);

            }
    }
