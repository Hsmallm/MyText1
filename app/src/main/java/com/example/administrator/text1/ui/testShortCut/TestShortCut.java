package com.example.administrator.text1.ui.testShortCut;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

import java.util.List;

/**
 * 功能描述：如何实现创建快捷方式
 * Created by hzhm on 2016/8/24.
 */
public class TestShortCut extends Activity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_shortcut);

        btn = (Button) findViewById(R.id.shortcut_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createShortCutByBroadCast();
            }
        });
    }

    /**
     * 第一种方式：通过非广播形式来向桌面添加一个快捷方式
     */
    private void creatShortCut() {
        Intent addShortCut;
        //判断是否需要添加快捷方式
        if (getIntent().getAction().equals(Intent.ACTION_CREATE_SHORTCUT)) {
            addShortCut = new Intent();
            //快捷方式的名称
            addShortCut.putExtra(Intent.EXTRA_SHORTCUT_NAME, "我的快捷方式");
            //显示的图片
            Parcelable icon = Intent.ShortcutIconResource.fromContext(this, R.drawable.icon_gcoding);
            addShortCut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
            //快捷方式激活的activity，需要执行的intent，自己定义
            addShortCut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent());
            //OK，生成
            setResult(RESULT_OK, addShortCut);
        } else {
            //取消
            setResult(RESULT_CANCELED);
        }
    }

    /**
     * 第二种方式：以发送广播的方式启动、创建
     */
    private void createShortCutByBroadCast() {
        if (!hasInstallShortcut(this)) {
            //创建快捷方式的Intent
            final Intent shortcutintent = new Intent();
            deleteShortCut(TestShortCut.this);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    crtShortcut(shortcutintent);
                }
            }, 1000);

        }
    }

    /**
     * 创建快捷方式
     * @param shortcutintent
     */
    private void crtShortcut(Intent shortcutintent){
        shortcutintent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        //不允许重复创建
        shortcutintent.putExtra("duplicate", false);
        //需要现实的名称
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "测试APP");
        //快捷图片
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplication(), R.mipmap.ic_launcher);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        //点击快捷图片，运行的程序主入口
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplication(), TestShortCut.class));

        Intent intent = new Intent();
        intent.setClass(getApplication(), TestShortCut.class);
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");

        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,intent);

        //发送广播。OK
        sendBroadcast(shortcutintent);
    }

    /**
     * 删除快捷方式
     * （注：Android6.0,删除权限是被禁止掉的，因为安全问题）
     * */
    public static void deleteShortCut(Context context)
    {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        //快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,"测试APP");
        /**删除和创建需要对应才能找到快捷方式并成功删除**/
        Intent intent = new Intent();
        intent.setClass(context, TestShortCut.class);
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");

        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,intent);
        context.sendBroadcast(shortcut);
    }

    /*
     * 获取launcher包名
     */
    private String getLauncherPkgName(Context context) {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : list) {
            String pkgName = info.processName;
            if (pkgName.contains("launcher") && pkgName.contains("android")) {
                Log.e("TAG", "launcherPkg =  " + pkgName);
                return pkgName;
            }
        }
        return null;
    }

    /**
     * 从相应的Permission里面获取Authority信息
     * @param context
     * @param permission
     * @return
     */
    static String getAuthorityFromPermission(Context context, String permission) {
        if (permission == null) return null;
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
        if (packs != null) {
            for (PackageInfo pack : packs) {
                ProviderInfo[] providers = pack.providers;
                if (providers != null) {
                    for (ProviderInfo provider : providers) {
                        if (permission.equals(provider.readPermission)) return provider.authority;
                        if (permission.equals(provider.writePermission)) return provider.authority;
                    }
                }
            }
        }
        return null;
    }

    /**
     * android获取当前正在运行的桌面launcher包名
     * @param context
     * @return
     */
    public String getLauncherPackageName(Context context) {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        if (res.activityInfo == null) {
            return "";
        }
        //如果是不同桌面主题，可能会出现某些问题，这部分暂未处理
        if (res.activityInfo.packageName.equals("android")) {
            return "";
        } else {
            return res.activityInfo.packageName;
        }
    }


    /**
     * 判断之前桌面快捷键是否已经安装
     *
     * @param context
     * @return
     */
    public boolean hasInstallShortcut(Context context) {
        boolean hasInstall = false;

        String AUTHORITY = "com.android.launcher.settings";
        String appName = getLauncherPackageName(TestShortCut.this);
        int systemversion = Build.VERSION.SDK_INT;
         /*大于8的时候在com.android.launcher2.settings 里查询（未测试）*/
        if (systemversion >= 8) {
            AUTHORITY = "com.android.launcher2.settings";
        }
        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
        //查询MediaStore多媒体库（originalUri：相应的表单；proj:表示相应的列）
        Cursor cursor = context.getContentResolver().query(CONTENT_URI,
                new String[]{"title"}, "title=?",
                new String[]{"泰和网"}, null);

        if (cursor != null && cursor.getCount() > 0) {
            hasInstall = true;
        }

        return hasInstall;
    }
}
