package com.example.administrator.text1.utils;

import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;

import com.google.gson.Gson;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by wangqing on 2018/2/23.
 */

/**
 * 功能描述:发送消息到指定的钉钉群
 * 使用方法：直接调用sengMsg方法，传递三个参数
 * urlPath：设置钉钉机器人的群的请求地址，可以自己在任何顶顶群里设置（必填）。例：
 * private String urlPath = "https://oapi.dingtalk.com/robot/send?access_token=455c76d858fd025a3f94464f9fc6743b918d81c44fd8e3d70cc4adc75d6ddc35";
 * content：要发送的消息的内容（必填）
 * phoneList：发送钉钉消息要@的人的手机号码，只需要手机号码，不需要@符号（选填）
 */
public class DingTalkUtil {

    public static void sendTextMsg(final String urlPath, final String content, final List<String> phoneList, final boolean isAtAll) {
        sendRequest(urlPath, initTextRequestData(urlPath, content, phoneList, isAtAll));
    }

    public static void sendLinkMsg(final String urlPath, String title, String text, String picUrl, String messageUrl) {
        sendRequest(urlPath, initLinkRequestData(title, text, picUrl, messageUrl));
    }

    public static void sendMarkDownMsg(final String urlPath, String title, String text, List<String> phoneList, boolean isAtAll) {
        sendRequest(urlPath, initMarkDownRequestData(title, text, phoneList, isAtAll));
    }

    public static void sendActionCardMsg(final String urlPath, String title, String text, String hideAvatar, String btnOrientation, String singleTitle, String singleURL) {
        sendRequest(urlPath, initActionCardRequestData(title, text, hideAvatar, btnOrientation, singleTitle, singleURL));
    }

    public static void sendActionCardWithBtnMsg(final String urlPath, String title, String text, String hideAvatar, String btnOrientation, List<ActionCardModel.ActionCardBean.BtnBean> btns) {
        sendRequest(urlPath, initActionCardWithBtnRequestData(title, text, hideAvatar, btnOrientation, btns));
    }

    private static NailRobotModle initTextRequestData(String urlPath, String content, List<String> phoneList, boolean isAtAll) {
        NailRobotModle nailRobotModle = new NailRobotModle();
        nailRobotModle.msgtype = "text";
        if (!TextUtils.isEmpty(content)) {
            NailRobotModle.TextBean textBean = new NailRobotModle.TextBean();
            textBean.content = content;
            nailRobotModle.text = textBean;

            if (!isAtAll) {
                if (phoneList != null) {
                    NailRobotModle.AtBean atBean = new NailRobotModle.AtBean();
                    atBean.atMobiles = phoneList;
                    nailRobotModle.at = atBean;
                }
            }
            nailRobotModle.isAtAll = isAtAll;
        }
        return nailRobotModle;
    }

    public static LinkModel initLinkRequestData(String title, String text, String picUrl, String messageUrl) {
        LinkModel linkModel = new LinkModel();
        LinkModel.LinkBean linkBean = new LinkModel.LinkBean();
        linkModel.msgtype = "link";
        if (!TextUtils.isEmpty(title)) {
            linkBean.title = title;
        }
        if (!TextUtils.isEmpty(text)) {
            linkBean.text = text;
        }
        if (!TextUtils.isEmpty(picUrl)) {
            linkBean.picUrl = picUrl;
        }
        if (!TextUtils.isEmpty(messageUrl)) {
            linkBean.messageUrl = messageUrl;
        }
        linkModel.link = linkBean;
        return linkModel;
    }

    private static MarkDownModel initMarkDownRequestData(String title, String text, List<String> phoneList, boolean isAtAll) {
        MarkDownModel markDownModel = new MarkDownModel();
        MarkDownModel.MarkDownBean markDownBean = new MarkDownModel.MarkDownBean();
        markDownModel.msgtype = "markdown";
        if (!TextUtils.isEmpty(title)) {
            markDownBean.title = title;
        }
        if (!TextUtils.isEmpty(text)) {
            markDownBean.text = text;
        }
        if (!isAtAll) {
            if (phoneList != null) {
                MarkDownModel.AtBean atBean = new MarkDownModel.AtBean();
                atBean.atMobiles = phoneList;
                markDownModel.at = atBean;
            }
        }
        markDownModel.isAtAll = isAtAll;
        markDownModel.markdown = markDownBean;
        return markDownModel;
    }

    private static ActionCardModel initActionCardRequestData(String title, String text, String hideAvatar, String btnOrientation, String singleTitle, String singleURL) {
        ActionCardModel actionCardModel = new ActionCardModel();
        ActionCardModel.ActionCardBean actionCardBean = new ActionCardModel.ActionCardBean();
        actionCardModel.msgtype = "actionCard";
        if (!TextUtils.isEmpty(title)) {
            actionCardBean.title = title;
        }
        if (!TextUtils.isEmpty(text)) {
            actionCardBean.text = text;
        }
        if (!TextUtils.isEmpty(hideAvatar)) {
            actionCardBean.hideAvatar = hideAvatar;
        }
        if (!TextUtils.isEmpty(btnOrientation)) {
            actionCardBean.btnOrientation = btnOrientation;
        }
        if (!TextUtils.isEmpty(singleTitle)) {
            actionCardBean.singleTitle = singleTitle;
        }
        if (!TextUtils.isEmpty(singleURL)) {
            actionCardBean.singleURL = singleURL;
        }
        actionCardModel.actionCard = actionCardBean;
        return actionCardModel;
    }

    private static ActionCardModel initActionCardWithBtnRequestData(String title, String text, String hideAvatar, String btnOrientation, List<ActionCardModel.ActionCardBean.BtnBean> btns) {
        ActionCardModel actionCardModel = new ActionCardModel();
        ActionCardModel.ActionCardBean actionCardBean = new ActionCardModel.ActionCardBean();
        actionCardModel.msgtype = "actionCard";
        if (!TextUtils.isEmpty(title)) {
            actionCardBean.title = title;
        }
        if (!TextUtils.isEmpty(text)) {
            actionCardBean.text = text;
        }
        if (!TextUtils.isEmpty(hideAvatar)) {
            actionCardBean.hideAvatar = hideAvatar;
        }
        if (!TextUtils.isEmpty(btnOrientation)) {
            actionCardBean.btnOrientation = btnOrientation;
        }
        if (btns != null){
            actionCardBean.btns = btns;
        }
        actionCardModel.actionCard = actionCardBean;
        return actionCardModel;
    }

    public static void sendRequest(final String urlPath, final Object object) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                request(urlPath, object);
            }
        }).start();
    }

    public static void request(String urlPath, Object object) {
        try {
            String json = new Gson().toJson(object);
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置文件类型:
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            // 设置接收类型否则返回415错误
            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
            conn.setRequestProperty("accept", "application/json");
            // 往服务器里面发送数据
            if (json != null && !TextUtils.isEmpty(json)) {
                byte[] writebytes = json.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(json.getBytes());
                outwritestream.flush();
                outwritestream.close();
                Log.d("hlhupload", "doJsonPost: conn" + conn.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return;
        }
    }

    public static class NailRobotModle {
        /**
         * msgtype : text
         * text : {"content":"我就是我, 是不一样的烟火"}
         * at : {"atMobiles":["156xxxx8827","189xxxx8325"],"isAtAll":false}
         */

        public String msgtype;
        public TextBean text;
        public AtBean at;
        private boolean isAtAll;

        public static class TextBean {
            /**
             * content : 我就是我, 是不一样的烟火
             */

            public String content;
        }

        public static class AtBean {
            /**
             * atMobiles : ["156xxxx8827","189xxxx8325"]
             * isAtAll : false
             */

            public boolean isAtAll;
            public List<String> atMobiles;
        }
    }

    public static class LinkModel {
        public String msgtype;
        public LinkBean link;

        public static class LinkBean {
            public String title;
            public String text;
            public String picUrl;
            public String messageUrl;
        }
    }

    public static class MarkDownModel {
        public String msgtype;
        public MarkDownBean markdown;
        public AtBean at;
        private boolean isAtAll;

        public static class MarkDownBean {
            /**
             * content : 我就是我, 是不一样的烟火
             */

            public String title;
            public String text;
        }

        public static class AtBean {
            /**
             * atMobiles : ["156xxxx8827","189xxxx8325"]
             * isAtAll : false
             */

            public boolean isAtAll;
            public List<String> atMobiles;
        }
    }

    public static class ActionCardModel {
        public String msgtype;
        public ActionCardBean actionCard;

        public static class ActionCardBean {
            public String title;
            public String text;
            public String hideAvatar;
            public String btnOrientation;
            public String singleTitle;
            public String singleURL;
            public List<BtnBean> btns;

            public static class BtnBean{

                public BtnBean(String title, String actionURL){
                    this.title = title;
                    this.actionURL = actionURL;
                }

                public String title;
                public String actionURL;
            }
        }
    }
}
