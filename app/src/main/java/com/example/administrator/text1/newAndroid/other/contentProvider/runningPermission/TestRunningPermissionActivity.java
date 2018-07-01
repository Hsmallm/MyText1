package com.example.administrator.text1.newAndroid.other.contentProvider.runningPermission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;
import com.example.administrator.text1.newAndroid.other.MyApplication;
import com.seaway.android.common.toast.Toast;

/**
 * @author HuangMing on 2017/12/28.
 *         功能描述：运行时权限
 *         android权限机制：在Android6.0之前，所以的权限的授权都会在应用安装的时候统一处理（即为：安装的时候就会提示同意即可安装，不同意则不可以安装），
 *         这样造成的就是“店大欺客”的问题，就是一些涉及到用户安全、隐私的权限都会不得不同意授权
 *         Android6.0 时加入的运行时权限：为了防止补缺之前android权限机制的漏洞所造成的“店大欺客”的现象，就引入这个，即在用户用到某个权限时候再授权操作
 */

public class TestRunningPermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_thread);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call();
            }
        });
    }

    private void call() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:61662073"));
            //运行时权限的检测，即为检测当前权限是否已经被授权
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                //申请相关权限
                ActivityCompat.requestPermissions(TestRunningPermissionActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return;
            } else {
                startActivity(intent);
            }
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            //判断当前权限是否被授权...
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                call();
            } else {
                Toast.showToast(MyApplication.getContext(), "you denied the permission!");
            }
        }
    }
}
