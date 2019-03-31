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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.android.volley.toolbox.RequestFuture;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.ImageRequest;

public class MainActivity extends Activity{

    private RequestQueue mQueue;
   // private XiwiVolley xiwiVolley = new XiwiVolley();
    private Handler myHandler = null;

    private TextView textView, logTextView = null;
    private ImageView imageView = null;

    private String url = null;
    private String result = null;
    
    private Matcher m = null;
    private String strRelt = null;


    @Override
    protected void onCreate ( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mQueue = Volley.newRequestQueue(getApplicationContext(), null);

        textView = (TextView) findViewById(R.id.textView);
        logTextView = (TextView) findViewById(R.id.logTextView);
        imageView = (ImageView) findViewById(R.id.imageView);

        /*
         strRequtst = new XiwiVolley.VolleyStringRequest(Request.Method.GET, "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=1476606163580", new Response.Listener<String>(){
         @Override
         public void onResponse(String str){

         System.out.println(str);

         Matcher m = Pattern.compile("window.QRLogin.uuid = \"(.*?)\";").matcher(str);
         if(m.find()){



         }

         }
         }, new Response.ErrorListener(){
         @Override
         public void onErrorResponse(VolleyError volleyError){

         }
         });
         */
        /*
         new Thread(new Runnable(){
         @Override
         public void run ( ){

         RequestFuture<String> future = RequestFuture.newFuture();
         strRequtst = new XiwiVolley.VolleyStringRequest(Request.Method.GET, "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=1476606163580", future, future);

         strRequtst.setHeaders(headers);
         mQueue.add(strRequtst);

         }
         }).start();
         */
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("User-Agent", "User-Agent, Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");

        //XiwiVolley xiwiVolley = new XiwiVolley();


        /*
         xiwiVolley.httpGet(mQueue, "String", this.url, headers, new httpInterface(){
         @Override
         public void onResponse(String result){
         ((TextView)findViewById(R.id.textView)).setText(result);
         syso(result);
         Matcher m = Pattern.compile("uuid = \"(.*?)\";").matcher(result);
         if(m.find()){
         MainActivity.this.url = "https://login.weixin.qq.com/qrcode/" + m.group(1);
         syso("url ok");
         MainActivity.this.xiwiVolley.httpGet(mQueue, "Image", MainActivity.this.url, null, new httpInterface(){
         @Override
         public void onResponse(String result){
         syso(result);
         }
         @Override
         public void onResponse(Bitmap Bitmap){
         ((ImageView)findViewById(R.id.imageView)).setImageBitmap(Bitmap);
         syso("image ok");
         toast("image ok");
         }
         });
         }
         }
         @Override
         public void onResponse(Bitmap bitmap){

         }
         });

         syso("img");
         syso(this.url);
         toast(this.url);
         */



        new Thread(new Runnable(){
            @Override
            public void run ( ){

               // XiwiVolley xiwiVolley = new XiwiVolley();
                RequestFuture strFuture = RequestFuture.newFuture();
                RequestFuture imgFuture = RequestFuture.newFuture();

                String url = null;
                
                String uuid = null;

                try{
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    headers.put("User-Agent", "User-Agent, Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");

                    url = "https://login.wx.qq.com/jslogin?appid=wx782c26e4c19acffb&redirect_uri=https%3A%2F%2Fwx.qq.com%2Fcgi-bin%2Fmmwebwx-bin%2Fwebwxnewloginpage&fun=new&lang=zh_CN&_=1476606163580";
                    syso(url);
                    //StringRequest strRequest = new StringRequest(Request.Method.GET, url, future, future);
                    XiwiVolley.VolleyStringRequest getUuidRequest = new XiwiVolley.VolleyStringRequest(Request.Method.GET, url, strFuture, strFuture);
                    getUuidRequest.setHeaders(headers);
                    mQueue.add(getUuidRequest);
                 
                    result = "" + strFuture.get();            
                    strFuture = RequestFuture.newFuture();
                    
                    syso("string ok");
                    Message msg = new Message();
                    msg.what = 0x01;
                    msg.obj = result;
                    myHandler.sendMessage(msg);
                    m = Pattern.compile("uuid = \"(.*?)\";").matcher(result);
                    if ( m.find() ){
                        uuid = m.group(1);
                        url = "https://login.weixin.qq.com/qrcode/" + m.group(1);
                    }
                    syso(url);
                    XiwiVolley.VolleyImageRequest QRcodeRequest = new XiwiVolley.VolleyImageRequest(url, imgFuture, 0, 0, Config.ARGB_8888, imgFuture);
                    QRcodeRequest.setHeaders(headers);
                    mQueue.add(QRcodeRequest);
                 
                    Bitmap bitmap = (Bitmap)imgFuture.get();
                    imgFuture = RequestFuture.newFuture();
          
                    syso("image ok");
                    Message msg2 = new Message();
                    msg2.what = 0x02;
                    msg2.obj = bitmap;
                    myHandler.sendMessage(msg2);

                    int tip = 1;
                    String ticket = null;
                    String scan = null;
                    //String uuid = null;
                    while ( true ){
                        //syso("sleep,,,");
                        
                        url = "https://login.wx.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=" + uuid + "&tip=" + tip + "&r=1870135597&_=1544318065401";                     
                        XiwiVolley.VolleyStringRequest verificationRequest = new XiwiVolley.VolleyStringRequest(Request.Method.GET, url, strFuture, strFuture);
                        verificationRequest.setHeaders(headers);
                        //Thread.sleep(1000);
                        mQueue.add(verificationRequest);
                        //strFuture = RequestFuture.newFuture();
                        result = "" + strFuture.get();                
                        strFuture = RequestFuture.newFuture();
                        syso("result:" + result);
                        if ( result.indexOf("code=201") > 0 ){
                            tip = 0;
                        } else {
                            tip = 1;
                        }
                        if ( result.indexOf("code=200") > 0){
                            
                            Message msg3 = new Message();
                            msg3.what = 0x00;
                            msg3.obj = result;
                            myHandler.sendMessage(msg3);
                            
                            m = Pattern.compile("window.redirect_uri=\"(.*?)\"").matcher(result);
                            if(m.find()){
                                syso("m.gr(1):"+m.group(1));
                                m = Pattern.compile("ticket=(.*?)&uuid=(.*?)&lang=zh_CN&scan=(.*?)xiwi").matcher(m.group(1)+"xiwi");
                                if(m.find()){
                                    ticket = m.group(1);
                                    uuid = m.group(2);
                                    scan = m.group(3);
                                    /*
                                    syso(m.group());
                                    syso("count:"+ m.groupCount());
                                    for(int i=1; i<=m.groupCount(); i++){
                                        syso("gr:"+i+"/t"+m.group(i));
                                    }
                                    */
                                    syso("ticket:"+ticket);
                                    syso("uuid:"+uuid);
                                    syso("scan:"+scan);
                                    
                                }
                                
                                break;
                            }
                            //https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=A5ncNUM2NJBYNOpJ49Jd38m2@qrticket_0&uuid=Ia7HTPkEdQ==&lang=zh_CN&scan=1485320697"
                            
                        }
                    }
                    strFuture = RequestFuture.newFuture();
                    //toast("登录成功！ On yes!!!");
                    syso("登录成功了！！");
                    
                    url = "https://wx2.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket="+ticket+"&uuid="+ uuid +"&lang=zh_CN&scan="+scan+"&fun=new";
                    Message loginMsg = new Message();
                    loginMsg.what = 0x00;
                    loginMsg.obj = url;
                    myHandler.sendMessage(loginMsg);
                    //url = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket="+ticket+"&uuid="+ uuid +"&lang=zh_CN&scan="+scan+"&fun=new&version=v2&lang=zh_CN";
                    syso(url);
                    XiwiVolley.VolleyStringRequest loginMarkRequest = new XiwiVolley.VolleyStringRequest(Request.Method.GET, url, strFuture, strFuture);
                    loginMarkRequest.setHeaders(headers);
                    mQueue.add(loginMarkRequest);
                    result = ""+strFuture.get();
                    strFuture = RequestFuture.newFuture();
                    //syso(result);
                    
                    Message msg3 = new Message();
                    msg3.what = 0x00;
                    msg3.obj = result;
                    myHandler.sendMessage(msg3);
                    
                    
                    
                    strRelt = "<error><ret>0</ret><message></message>" +
                        "<skey>(.*?)</skey>" + 
                        "<wxsid>(.*?)</wxsid>" +
                        "<wxuin>(.*?)</wxuin>" +
                        "<pass_ticket>(.*?)</pass_ticket>" +
                        "<isgrayscale>1</isgrayscale></error>";
                    String skey = null;
                    String wxsid = null;
                    String wxuin = null;
                    String pass_ticket = null;
                    m = Pattern.compile(strRelt).matcher(result);
                    if(m.find()){
                        syso("有！了");
                        skey = m.group(1);
                        wxsid = m.group(2);
                        wxuin = m.group(3);
                        pass_ticket = m.group(4);
                        
                        syso("skey:"+skey);
                        syso("wxsid:"+wxsid);
                        syso("wxuin:" + wxuin);
                        syso("pass_ticket:"+pass_ticket);
                    }
                    
                    String strParame = "{BaseRequest:{" + 
                        "Uin:\""+wxuin+"\"," + 
                        "Sid:\""+wxsid+"\"," +
                        "Skey:\""+skey+"\"," +
                        "DeviceID:\"e119795675188164\"}}";
                    
                    
                    XiwiVolley.VolleyStringRequest loginMarkRequest2 = new XiwiVolley.VolleyStringRequest(Request.Method.GET, url, strFuture, strFuture);
                    loginMarkRequest2.setHeaders(headers);
                    mQueue.add(loginMarkRequest2);
                    result = ""+strFuture.get();
                    strFuture = RequestFuture.newFuture();
                    
                    Message msg4 = new Message();
                    msg4.what = 0x00;
                    msg4.obj = result;
                    myHandler.sendMessage(msg4);
                    
                    
                    /*
                    url = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket="+ticket+"&uuid="+ uuid +"&lang=zh_CN&scan="+scan+"&fun=old&version=v2&lang=zh_CN";
                    syso(url);
                    XiwiVolley.VolleyStringRequest loginMarkRequest2 = new XiwiVolley.VolleyStringRequest(Request.Method.GET, url, strFuture, strFuture);
                    loginMarkRequest2.setHeaders(headers);
                    mQueue.add(loginMarkRequest2);
                    result = ""+strFuture.get();
                    strFuture = RequestFuture.newFuture();
                    syso(result);
*/
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();


        myHandler = new Handler() {
            @Override
            public void handleMessage ( Message msg ) {   
                switch ( msg.what ) {   
                    case 0x00:
                        toast(""+msg.obj);
                    syso(msg.obj + "");
                    logTextView.append(msg.obj+"\n\n");
               
                    break;
                    case 0x01:   
                    textView.setText(msg.obj + "");
                    break;   
                    case 0x02:
                    imageView.setImageBitmap((Bitmap)msg.obj);
                    break;
                }   

            }   
        };  



    }




    private void toast ( String msg ){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
    private void syso ( String msg ){
        System.out.println(msg);
    }


}
    
    
    
    
