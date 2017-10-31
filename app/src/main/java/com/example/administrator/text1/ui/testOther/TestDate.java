package com.example.administrator.text1.ui.testOther;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.text1.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hzhm on 2017/3/22.
 *
 * 功能描述：通过反射的方法来获取某个应用程序的缓存大小，但是没能实现通过反射的方法来清除该应用缓存，所以我只能调用“系统的设置”里面的来清除缓存
 * 相关准备：导入三个aidl问价：PackageStats.aidl、IPackageDataObserver.aidl、IPackageStatsObserver.aidl,之后再ReBuild一下代码...
 */

public class TestDate extends Activity{

    public static final String ENCODING="UTF-8";//常量，代表编码格式。
    private PackageManager packageManager;
    private PackageInfo packageInfo;

    private TextView txt;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_new_anima);
        init();
        initView();
    }

//    public void createCache() {
//        InputStream in = getResources().openRawResource(R.mipmap.pic_1);
//        BufferedInputStream bis = new BufferedInputStream(in);
//        try {
//            FileOutputStream fos = openFileOutput("pic_1.png", MODE_PRIVATE);
//            byte[] buffer=new byte[1024];
//            int len = -1;
//            while ((len=bis.read(buffer))!=-1){
//                fos.write(buffer,0,len);
//            }
//            fos.close();
//            bis.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void init() {
        packageManager = getPackageManager();
    }

    private void initView() {
        txt = (TextView) findViewById(R.id.anima_txt);
        btn = (Button) findViewById(R.id.anima_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        txt.postDelayed(new Runnable() {
            @Override
            public void run() {
                showCacheSize();
            }
        },2000);
    }

    public void clear(){

//        Toast.makeText(MainActivity.this,"点击了clear按钮",0).show();
//        try {
//            Method[] methods = PackageManager.class.getMethods();
//            for(Method m:methods){
//                if("deleteApplicationCacheFiles".equals(m.getName())){
//                    m.invoke(packageManager,packageInfo.packageName,new ClearCacheObserver());
//                }
//                if("deleteApplicationCacheFiles".equals(m.getName())){
//                    m.invoke(packageManager,packageInfo.packageName,new ClearCacheObserver());
//                }
//            }
////            method = PackageManager.class.getDeclaredMethod("clearApplicationUserData",String.class,IPackageDataObserver.class);
////            method.invoke(packageManager,packageInfo.packageName,new ClearCacheObserver());
////            method = PackageManager.class.getDeclaredMethod("deleteApplicationCacheFiles",String.class,IPackageDataObserver.class);
////            method.invoke(packageManager,packageInfo.packageName,new ClearCacheObserver());
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }

        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        // dat=package:com.itheima.mobileguard
        intent.setData(Uri.parse("package:" + packageInfo.packageName));
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        showCacheSize();
    }

    private void showCacheSize(){
        if(packageManager!=null){
            try {
                //实例化相关包的信息
                packageInfo = packageManager.getPackageInfo("com.example.administrator.text1", 0);
                getCacheSize(packageInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 获取某个包名对应的应用程序的缓存大小
     * @param packageInfo  应用程序的包信息
     *
     * IPackageStatsObserver.class编译器报找不到的问题，只需要rebuild一下就行了。
     */
    private void getCacheSize(PackageInfo packageInfo) {

        try {
            //通过反射获取到当前的方法。
            Method method = PackageManager.class.getDeclaredMethod("getPackageSizeInfo",String.class,IPackageStatsObserver.class);
            /**
             * 第一个参数：调用该方法的对象
             * 第二个参数：应用包名
             * 第三个参数：IPackageStatsObserver类型的aidl对象。
             */
            method.invoke(packageManager,packageInfo.packageName,new MyPackObserver(packageInfo));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现一个继承IPackageStatsObserver内部类获取并包的缓存大小
     */
    private class MyPackObserver extends android.content.pm.IPackageStatsObserver.Stub{

        private PackageInfo info;

        public MyPackObserver(PackageInfo info){
            this.info = info;
        }

        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
            long cacheSize = pStats.cacheSize;
            txt.setText(Formatter.formatFileSize(getApplicationContext(), cacheSize));
        }
    }
}
