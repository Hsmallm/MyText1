//package com.example.administrator.text1.ui.testQRcode;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.widget.ImageView;
//
//import com.example.administrator.text1.R;
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by hzhm on 2016/11/30.
// * 功能描述：生成二维码
// *
// */
//
//public class TestProductQRBitmap extends Activity {
//
//    private static final String URL = "http://jr.trc.com/thapp/newUser.html?r=18069788129&c=A";
//    private ImageView qrCodeImg;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_productqrcode);
//
//        qrCodeImg = (ImageView) findViewById(R.id.test_qrcode_img);
//        qrCodeImg.setImageBitmap(getQRBitmmap(URL,500,500));
//    }
//
//    /**
//     * 生成二维码
//     * @param url
//     * @param x
//     * @param y
//     * @return
//     */
//    private Bitmap getQRBitmmap(String url,int x,int y){
//        //二维码写入转化帮助类
//        MultiFormatWriter multiFormatWriter = null;
//        Bitmap bitmap = null;
//
//        try {
//            multiFormatWriter = new MultiFormatWriter();
//            //参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数(即：实例化二维码位图)
//            BitMatrix bitMatrix = multiFormatWriter.encode(url, BarcodeFormat.QR_CODE,x,y,getDecodeHintType());
//            //获取位图的宽高
//            int w = bitMatrix.getWidth();
//            int h = bitMatrix.getHeight();
//            //生成图片
//            bitmap = Bitmap.createBitmap(w,h, Bitmap.Config.RGB_565);
//
//            // 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色（即：循环二维码里面每一个坐标点写入相应的黑白点）
//            for(int a = 0; a < w; a++ ){
//                for (int b = 0; b < h; b++){
//                    bitmap.setPixel(a,b,bitMatrix.get(a,b)? Color.BLACK:Color.WHITE);
//                }
//            }
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }
//
//    /**
//     * 设置二维码的格式参数
//     * @return
//     */
//    private Map<EncodeHintType, Object> getDecodeHintType(){
//        // 用于设置QR二维码参数
//        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
//        // 设置QR二维码的纠错级别（H为最高级别）具体级别信息
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//        // 设置编码方式
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//        // 设置QR二维码的宽高
//        hints.put(EncodeHintType.MAX_SIZE, 500);
//        hints.put(EncodeHintType.MIN_SIZE, 500);
//        return hints;
//    }
//}
