package com.xiwi.wechat;
import com.android.volley.toolbox.ImageRequest;
import java.util.Map;
import java.util.HashMap;
import com.android.volley.Response;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import android.util.Log;
import com.android.volley.toolbox.StringRequest;
import java.io.UnsupportedEncodingException;
import com.android.volley.toolbox.HttpHeaderParser;

public class XiwiVolley{

        /* 图片请求 */
        public static class VolleyImageRequest extends ImageRequest{

                private Map<String, String> Headers = new HashMap<>();
                private String Cookie = "";
                private String str;

                public VolleyImageRequest(String url, Response.Listener<Bitmap> listener, int heignt, int width, Config config, Response.ErrorListener errorListener){
                        super(url, listener, heignt, width, config, errorListener);
                    }

                public void setHeaders(Map<String ,String> headers){
                        Headers = new HashMap<>();
                        Headers = headers;
                    }

                public void setCookie(String cookie){
                        Cookie = cookie;
                    }

                public String getCookie(){
                        return Cookie;
                    }

                // 设置请求头
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError{
                       Cookie = "";
                    if (Headers.size() <= 0){
                                Map<String, String> headers = new HashMap<>();
                                for (String key : Headers.keySet()){
                                    headers.put(key, Headers.get(key));
                                    }
                                return headers;
                            }
                        return super.getHeaders();
                    }

                // 记录Cookie
                @Override
                protected Response<Bitmap> parseNetworkResponse(NetworkResponse response){ 
                        Map<String, String> responseHeaders = response.headers;
                        Cookie = responseHeaders.get("Set-Cookie");
                        System.out.println("VolleyImageRequestCookie:" + Cookie);
                        str =response.toString();
                        return super.parseNetworkResponse(response);
                    }
            }

            
            
        /* 文本请求 */
        public static class VolleyStringRequest extends StringRequest{
                private Map<String, String> Headers = new HashMap<>();
                private String Cookie = "";
                private Map<String, String> PostData = new HashMap<>();

                public VolleyStringRequest(int method, String url, Response.Listener<String> listener,
                                           Response.ErrorListener errorListener){
                        super(method, url, listener, errorListener);
                    }

                public void setPostData(Map<String, String> postData){
                        PostData = new HashMap<String, String>();
                        for (String str : postData.keySet()){
                                PostData.put(str, postData.get(str));
                            }
                    }

                public Map getPostData(){
                        return PostData;
                    }

                /**
                 * 重写以解决乱码问题
                 */
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response){
                        String str = null;
                        try{
                                str = new String(response.data, "utf-8");
                                Map<String, String> responseHeaders = response.headers;
                                
                                for(String Headerstr : responseHeaders.keySet()){
                                    //System.out.println("
                                }
                                
                                //判断 请求头 是否有包含 Set-Cookie
                                if (responseHeaders.containsKey("Set-Cookie")){
                                        Cookie = responseHeaders.get("Set-Cookie");
                                    }
                                System.out.println("VolleyStringRequest的Set-Cookie:" + responseHeaders.get("Set-Cookie"));
                                //Log.i("StringRequest的返回值", str);
                            }
                        catch (UnsupportedEncodingException e){
                                e.printStackTrace();
                            }
                        return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
                    }


                public void setHeaders(Map<String, String> headers){
                        Headers = new HashMap<>();
                        Headers = headers;
                    }

                public void setCookie(String cookie){
                        Cookie = cookie;
                    }

                public String getCookie(){
                        return Cookie;
                    }

                // 设置请求头
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError{
                        
                    Map<String, String> headers = new HashMap<>();
                    
                    
                    
                        
                            if(Headers.size()<=0&&(Cookie==null||Cookie.equals(""))){
                                    return super.getHeaders();
                                } else if(Headers.size() >0){
                                    for(String str : Headers.keySet()){
                                            headers.put(str, Headers.get(str));
                                        }
                                    } 
                                    if (Cookie != null){
                                        Log.i("VolleyStringRequest的Cookie", Cookie);
                                        headers.put("Cookie", Cookie);
                                        
                                    }
                                    
                        return headers;
                    }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError{
                        //Cookie = "";
                    if (PostData.size() <= 0){
                                return super.getParams();
                            }

                        Map<String, String> params = new HashMap<>();
                        for (String str : PostData.keySet()){
                                params.put(str, PostData.get(str));
                            }
                        return params;
                    }
            }

    }
