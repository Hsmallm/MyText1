package com.example.administrator.text1.ui.testUMengShare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

/**
 * 功能描述：友盟一盏式集成多平台分享
 * 1、注册并获取友盟Appkey，下载最新的基础Sdk,然后以library项目开源库的形式导入项目中
 * 2、在各大需要分享的平台注册并创建应用，获取相应的Appid、Appkey
 * 3、在导入的开源库中新建一个java文件，并创建相关应用类UmengShareConfig（设置各大分享平台的Appid、Appkey），并在ThApplication里面实现并UmengShareConfig初始化，
 * ThApplication也必须在app项目中的配置文件中完成注册
 * 4、再到导入的开源库umengshare2中的配置文件AndroidManifest中，注册相关分享应用的Activity、权限、以及友盟的AppKey、AppSECRET
 *    (注：在进行配置时候发现：必须注册微博、QQ的Activity，微信的Activity可以不用注册，)
 * 5、最后在TextUMengShare类中实现分享（实例化分享类ShareAction的两种方式：第一种： shareAction = new ShareAction(this).setPlatform(share_media)
 *    第二种：new ShareAction(this).setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE)）
 * （相关应用的文件有：umengshare2中的配置文件AndroidManifest、UmengShareConfig、ThApplication、app中的配置文件AndroidManifest）
 * Created by hzhm on 2016/6/14.
 */
public class TextUMengShare extends Activity implements View.OnClickListener{

    private LinearLayout llWeiXin;
    private LinearLayout llCof;
    private LinearLayout llWeiBo;
    private LinearLayout llQq;
    private LinearLayout llQqzone;
    private Button btnOpenShare;
    private Button btnMyShare;

    private static final String TITLE = "title";
    private ShareAction shareAction;
    private ShareAction shareBoard;
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.showToast(TextUMengShare.this,share_media+"分享成功！");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.showToast(TextUMengShare.this,share_media+"分享失败！");
            Log.e("",Log.getStackTraceString(throwable));
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.showToast(TextUMengShare.this,share_media+"分享取消！");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_umemg_share);

        llWeiXin = (LinearLayout) findViewById(R.id.invitingFrd_weichat);
        llCof = (LinearLayout) findViewById(R.id.invitingFrd_cof);
        llWeiBo = (LinearLayout) findViewById(R.id.invitingFrd_weibo);
        llQq = (LinearLayout) findViewById(R.id.invitingFrd_trqq);
        llQqzone = (LinearLayout) findViewById(R.id.invitingFrd_trqqzone);
        btnOpenShare = (Button) findViewById(R.id.open_share);
        btnMyShare = (Button) findViewById(R.id.myshare);

        llWeiXin.setOnClickListener(this);
        llCof.setOnClickListener(this);
        llWeiBo.setOnClickListener(this);
        llQq.setOnClickListener(this);
        llQqzone.setOnClickListener(this);
        btnOpenShare.setOnClickListener(this);
        btnMyShare.setOnClickListener(this);

        setShareContent(SHARE_MEDIA.EMAIL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //1.setPlatform():实现自定义分享
            case R.id.invitingFrd_weichat:
                if(shareAction != null){
                    shareAction.setPlatform(SHARE_MEDIA.WEIXIN).share();
                }else {
                    setShareContent(SHARE_MEDIA.WEIXIN);
                    shareAction.share();
                }
                break;

            case R.id.invitingFrd_cof:
                if(shareAction != null){
                    shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).share();
                }else {
                    setShareContent(SHARE_MEDIA.WEIXIN_CIRCLE);
                    shareAction.share();
                }
                break;

            case R.id.invitingFrd_weibo:
                if(shareAction != null){
                    shareAction.setPlatform(SHARE_MEDIA.SINA).share();
                }else {
                    setShareContent(SHARE_MEDIA.SINA);
                    shareAction.share();
                }
                break;

            case R.id.invitingFrd_trqq:
                if(shareAction != null){
                    shareAction.setPlatform(SHARE_MEDIA.QQ).share();
                }else {
                    setShareContent(SHARE_MEDIA.QQ);
                    shareAction.share();
                }
                break;

            case R.id.invitingFrd_trqqzone:
                if(shareAction != null){
                    shareAction.setPlatform(SHARE_MEDIA.QZONE).share();
                }else {
                    setShareContent(SHARE_MEDIA.QZONE);
                    shareAction.share();
                }
                break;

            //2.setDisplayList()实现自带控制面板分享
            case R.id.open_share:
                UMImage umImage = new UMImage(this, BitmapFactory.decodeResource(getResources(),R.drawable.share_logo));
                new ShareAction(this).setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE)
                        .withText("")
                        .withMedia(umImage)
                        .setCallback(umShareListener)
                        .open();
                break;
            //3.setDisplayList().addButton实现自定义控制面板分享
            case R.id.myshare:
                new ShareAction(this).setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE)
                        .addButton("umeng_sharebutton_custom","umeng_sharebutton_custom","info_icon_1","info_icon_1")
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                if(snsPlatform.mShowWord.equals("umeng_sharebutton_custom")){
                                    Toast.showToast(TextUMengShare.this,"自定义分享");
                                }else {
                                    new ShareAction(TextUMengShare.this).withText("来自友盟自定义分享面板")
                                            .setPlatform(share_media)
                                            .setCallback(umShareListener)
                                            .share();
                                }
                            }
                        }).open();
                break;
        }
    }

    private void setShareContent(SHARE_MEDIA share_media){
        UMImage umImage = new UMImage(this, BitmapFactory.decodeResource(getResources(),R.drawable.share_logo));
        shareAction = new ShareAction(this)
                .setPlatform(share_media)
                .setCallback(umShareListener)
                .withTitle("注册泰和网，红包马上有！")
                .withText("高至12.6%的年化收益，多重保险保障降低投资风险，就在“泰和网”！")
                .withTargetUrl("www.baidu.com")
                .withMedia(umImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        com.umeng.socialize.utils.Log.d("result","onActivityResult");
    }
}
