package com.xiwi.wechat;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class XiwiVolley{

    
    /* Json请求 */
    public static class VolleyJsonRequest extends JsonObjectRequest{
        
        private Map<String, String> Headers = null;
        private String cookie = "";
        private String str;
        
        public VolleyJsonRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
            super(method, url, jsonRequest, listener, errorListener);
        }
        
        // 设置请求头
        @Override
        public Map<String, String> getHeaders ( ) throws AuthFailureError{
            //Cookie = "";
            if ( Headers != null ){
                Map<String, String> headers = new HashMap<>();
                for ( String key : Headers.keySet() ){
                    headers.put(key, Headers.get(key));
                }
                return headers;
            }
            return super.getHeaders();
        }
        @Override
        protected Response<String> parseNetworkResponse ( NetworkResponse response ){
            String str = null;
            try{
                str = new String(response.data, "utf-8");
                Map<String, String> responseHeaders = response.headers;

                for ( String Headerstr : responseHeaders.keySet() ){
                    //System.out.println("
                }

                //判断 请求头 是否有包含 Set-Cookie
                if ( responseHeaders.containsKey("Set-Cookie") ){
                    this.cookie = responseHeaders.get("Set-Cookie");
                }
                //System.out.println("VolleyStringRequest的Set-Cookie:" + responseHeaders.get("Set-Cookie"));
                //Log.i("StringRequest的返回值", str);
            }
            catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
        }
    }
    
    
    /* 图片请求 */
    public static class VolleyImageRequest extends ImageRequest{

        private Map<String, String> Headers = null;
        private String Cookie = "";
        private String str;

        public VolleyImageRequest ( String url, Response.Listener<Bitmap> listener, int heignt, int width, Config config, Response.ErrorListener errorListener ){
            
            super(url, listener, heignt, width, config, errorListener);
            super.setRetryPolicy(new DefaultRetryPolicy(8000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        }
        
        public void setHeaders ( Map<String ,String> headers ){
            Headers = new HashMap<>();
            Headers = headers;
        }

        public void setCookie ( String cookie ){
            Cookie = cookie;
        }

        public String getCookie ( ){
            return Cookie;
        }

        // 设置请求头
        @Override
        public Map<String, String> getHeaders ( ) throws AuthFailureError{
            //Cookie = "";
            if ( Headers != null ){
                Map<String, String> headers = new HashMap<>();
                for ( String key : Headers.keySet() ){
                    headers.put(key, Headers.get(key));
                }
                return headers;
            }
            return super.getHeaders();
        }

        // 记录Cookie
        @Override
        protected Response<Bitmap> parseNetworkResponse ( NetworkResponse response ){ 
            Map<String, String> responseHeaders = response.headers;
            Cookie = responseHeaders.get("Set-Cookie");
            System.out.println("VolleyImageRequestCookie:" + Cookie);
            str = response.toString();
            return super.parseNetworkResponse(response);
        }
    }



    /* 文本请求 */
    public static class VolleyStringRequest extends StringRequest{
        private Map<String, String> headers = new HashMap<String, String>();
        private String cookie = null;
        private Map<String, String> params = new HashMap<String, String>();

        
        public VolleyStringRequest ( int method, String url, Response.Listener<String> listener,
        Response.ErrorListener errorListener ){
            super(method, url, listener, errorListener);
            super.setRetryPolicy(new DefaultRetryPolicy(8000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            
        }

        public void setHeaders(Map<String, String> headers){
            if(headers!=null){
                for(String header : headers.keySet()){
                    this.headers.put(header, headers.get(header));
                }
            }
        }
        public void setParams(Map<String, String> params){
            if(params != null){
                for(String param : params.keySet()){
                    this.params.put(param, params.get(param));
                }
            }
        }
        
        
        /*
        public void setPostData ( Map<String, String> postData ){
            PostData = new HashMap<String, String>();
            for ( String str : postData.keySet() ){
                PostData.put(str, postData.get(str));
            }
        }

        public Map getPostData ( ){
            return PostData;
        }*/

        /**
         * 重写以解决乱码问题
         */
        @Override
        protected Response<String> parseNetworkResponse ( NetworkResponse response ){
            String str = null;
            try{
                str = new String(response.data, "utf-8");
                Map<String, String> responseHeaders = response.headers;

                for ( String Headerstr : responseHeaders.keySet() ){
                    //System.out.println("
                }

                //判断 请求头 是否有包含 Set-Cookie
                if ( responseHeaders.containsKey("Set-Cookie") ){
                    this.cookie = responseHeaders.get("Set-Cookie");
                }
                //System.out.println("VolleyStringRequest的Set-Cookie:" + responseHeaders.get("Set-Cookie"));
                //Log.i("StringRequest的返回值", str);
            }
            catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
            return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
        }


        /*
        public void setHeaders ( Map<String, String> headers ){
            Headers = new HashMap<String, String>();
            Headers = headers;
        }*/

        public void setCookie ( String cookie ){
            this.cookie = cookie;
            Map<String, String> headers = new HashMap<String, String>();
            headers.put("Cookie", cookie);
            setHeaders(headers);
        }

        public String getCookie ( ){
            return this.cookie;
        }

        // 设置请求头
        @Override
        public Map<String, String> getHeaders ( ) throws AuthFailureError{

            Map<String, String> headers = new HashMap<>();

            if ( this.headers == null){
                return super.getHeaders();
            } else {
                for ( String str : this.headers.keySet() ){
                    headers.put(str, this.headers.get(str));
                }
            }
            return headers;
        }

        @Override
        protected Map<String, String> getParams ( ) throws AuthFailureError{
            //Cookie = "";
            if ( this.params.size() <= 0 ){
                return super.getParams();
            } else {
                Map<String, String> params = new HashMap<String, String>();
                for ( String str : this.params.keySet() ){
                    params.put(str, this.params.get(str));
                }
                return params;
            }

            
        }
    }
    
    

/*
    public void httpGet (RequestQueue mQueue, String type, String url, Map<String, String> headers, final httpInterface response ){

        Request requestConnent = null;
        
        if ( type.equals("String") ){
            VolleyStringRequest request = new VolleyStringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                @Override
                public void onResponse ( String result ){
                   response.onResponse(result);
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse ( VolleyError volleyError ){}
            });
            request.setHeaders(headers);
            requestConnent = request;
        }
        if(type.equals("Image")){
            VolleyImageRequest request = new VolleyImageRequest(url, new Response.Listener<Bitmap>(){
                @Override
                public void onResponse(Bitmap bitmap){
                    response.onResponse(bitmap);
                }
            }, 0,0, Config.ARGB_8888, new Response.ErrorListener(){
                @Override
                public void onErrorResponse ( VolleyError volleyError ){}
            });
            request.setHeaders(headers);
            requestConnent = request;
        }
        mQueue.add(requestConnent);
    }
*/




}
    
