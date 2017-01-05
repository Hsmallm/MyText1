package com.example.administrator.text1.ui.testBaiduMap;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 功能描述：方向传感器
 * Created by hzhm on 2016/7/1.
 */
public class MyOrientationListener implements SensorEventListener {

    private SensorManager mSensorManager;
    private Context mcontext;
    private Sensor mSensor;

    private float lastX;
    private OnOrienattionListener mOnOrienattionListener;

    public MyOrientationListener(Context context) {
        this.mcontext = context;
    }

    /**
     * 开启方向传感器监听
     */
    public void start(){
        mSensorManager = (SensorManager) mcontext.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager != null){
            //获得方向传感器
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        if(mSensor != null){
            //注册监听
            mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_UI);
        }
    }

    /**
     * 停止方向传感器监听
     */
    public void stop(){
        mSensorManager.unregisterListener(this);
    }

    /**
     * 当传感器发生改变时调用
     * @param event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        //如果是方向传感器发生改变时
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            //如果偏移角度大于一度且mOnOrienattionListener不为空时，调用这个方向改变方法
            float x = event.values[SensorManager.DATA_X];
            if (Math.abs(x - lastX) > 1.0) {
                if (mOnOrienattionListener != null) {
                    mOnOrienattionListener.OnOrienattionChange(x);
                }
            }
            lastX = x;
        }
    }

    /**
     * 当精度发生改变时调用
     * @param sensor
     * @param accuracy
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setOnOrienattionListener(OnOrienattionListener mOnOrienattionListener) {
        this.mOnOrienattionListener = mOnOrienattionListener;
    }

    public interface OnOrienattionListener {
        void OnOrienattionChange(float x);
    }
}
