package com.xiwi.wechat;
import android.graphics.Bitmap;

public interface httpInterface
{
    public void onResponse ( String result );
    public void onResponse ( Bitmap bitmap );
}
