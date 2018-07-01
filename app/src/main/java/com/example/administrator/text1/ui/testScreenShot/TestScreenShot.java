package com.example.administrator.text1.ui.testScreenShot;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testAndroid.testActivity.SecondActivity;
import com.example.administrator.text1.utils.DingTalkUtil;
import com.example.administrator.text1.utils.ScreenShot;
import com.example.administrator.text1.utils.ToastUtil;
import com.seaway.android.common.toast.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangMing on 2018/2/23.
 *         功能描述：测试截取当前视图功能
 */

public class TestScreenShot extends AppCompatActivity {

    private Button btn;
    private Button btn2;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_animation);

        //动态权限获取(存储、读写权限)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1000);
            }
        }
        btn = (Button) findViewById(R.id.startBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenshot();
            }
        });
        editText = (EditText) findViewById(R.id.edit);
        btn2 = (Button) findViewById(R.id.endBtn);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestScreenShot.this,TestGetSdCardPic.class));
            }
        });
    }

    private void screenshot() {
        ScreenShot.savePic(ScreenShot.takeScreenShot(TestScreenShot.this,null), "sdcard/Pictures/xxx.png");
    }

    private void testDingTalk(){
        //                DingTalkUtil.sendLinkMsg("https://oapi.dingtalk.com/robot/send?access_token=756f0386c3ec2d9e3ff4ab8752f75c55ec2f29c5e00cc060b5ebb470a0e7a43b",
//                        "时代的火车向前开", "这个即将发布的新版本，创始人陈航（花名“无招”）称它为“红树林”。\n" +
//                                "而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是“红树林”？",
//                        "", "https://mp.weixin.qq.com/s?__biz=MzA4NjMwMTA2Ng==&mid=2650316842&idx=1&sn=60da3ea2b29f1dcc43a7c8e4a7c97a16&scene=2&srcid=09189AnRJEdIiWVaKltFzNTw&from=timeline&isappinstalled=0&key=&ascene=2&uin=&devicetype=android-23&version=26031933&nettype=WIFI");
//                DingTalkUtil.sendTextMsg("https://oapi.dingtalk.com/robot/send?access_token=756f0386c3ec2d9e3ff4ab8752f75c55ec2f29c5e00cc060b5ebb470a0e7a43b",
//                        "我就是我, 是不一样的烟火", null, true);
//                String text = "### 杭州天气 \n" +
//                        "> 9度，西北风1级，空气良89，相对温度73%\n\n" +
//                        "> ![screenshot](https://goss4.vcg.com/creative/vcg/800/version23/VCG41166308380.jpg)\n" +
//                        "> ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \n";
//                DingTalkUtil.sendMarkDownMsg("https://oapi.dingtalk.com/robot/send?access_token=756f0386c3ec2d9e3ff4ab8752f75c55ec2f29c5e00cc060b5ebb470a0e7a43b",
//                        "杭州天气", text, null, true);
        String text = "![screenshot](@lADOpwk3K80C0M0FoA) \n" +
                "### 乔布斯 20 年前想打造的苹果咖啡厅 \n" +
                "> Apple Store 的设计正从原来满满的科技感走向生活化，而其生活化的走向其实可以追溯到 20 年前苹果一个建立咖啡馆的计划";
        List<DingTalkUtil.ActionCardModel.ActionCardBean.BtnBean> btns = new ArrayList<>();
        btns.add(new DingTalkUtil.ActionCardModel.ActionCardBean.BtnBean("内容不错","https://www.dingtalk.com/"));
        btns.add(new DingTalkUtil.ActionCardModel.ActionCardBean.BtnBean("不感兴趣","https://www.dingtalk.com/"));
//                DingTalkUtil.sendActionCardMsg("https://oapi.dingtalk.com/robot/send?access_token=756f0386c3ec2d9e3ff4ab8752f75c55ec2f29c5e00cc060b5ebb470a0e7a43b",
//                        "乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身", text, "1", "1",
//                        "阅读全文", "https://www.dingtalk.com/");

        DingTalkUtil.sendActionCardWithBtnMsg("https://oapi.dingtalk.com/robot/send?access_token=756f0386c3ec2d9e3ff4ab8752f75c55ec2f29c5e00cc060b5ebb470a0e7a43b",
                "乔布斯 20 年前想打造一间苹果咖啡厅，而它正是 Apple Store 的前身", text, "1", "1",
                btns);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && requestCode == 1000) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("请在系统设置中为App中开启文件权限后重试")
                        .setPositiveButton("确定", null)
                        .show();
            } else {
//                ActionUtils.startActivityForGallery(mActivity, ActionUtils.PHOTO_REQUEST_GALLERY);
            }
        }

    }
}
