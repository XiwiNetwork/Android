package com.xiwi.cm;

import android.app.*;
import android.os.*;
import android.hardware.ConsumerIrManager;
import android.widget.Toast;

public class MainActivity extends Activity 
{
    //获取红外控制类
    private ConsumerIrManager IR;
    
    //判断是否有红外功能
    boolean IRBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        inItEvent();
    }


    //初始化事务
    private void inItEvent() {
        //获取ConsumerIrManager实例
        IR = (ConsumerIrManager) getSystemService(CONSUMER_IR_SERVICE);

        //如果sdk版本大于4.4才进行是否有红外的功能（手机的android版本）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            IRBack = IR.hasIrEmitter();
            if (!IRBack) {
                showToast("对不起，该设备上没有红外功能!");
            } else {
                showToast("红外设备就绪");//可进行下一步操作
            }
        }
    }

    /**
     * 发射红外信号
     *
     * @param carrierFrequency 红外传输的频率，一般的遥控板都是38KHz
     * @param pattern          指以微秒为单位的红外开和关的交替时间
     */
    private void sendMsg(int carrierFrequency, int[] pattern) {
        IR.transmit(carrierFrequency, pattern);
    }
    
    private void showToast(String msg){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
