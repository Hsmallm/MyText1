package com.example.administrator.text1.ui.testPhotos;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.THSharePreferencesHelperUtil;
import com.seaway.android.common.toast.Toast;

/**
 * 功能描述：修改头像功能测试（拍照、相册选择、裁剪）
 * Created by hzhm on 2016/6/8.
 */
public class TextPhotoActivity extends Activity {
    public static final int REQUEST_CAMERA = 0x001;
    public static final int REQUEST_CROP = 0x002;
    public static final int REQUEST_ALBUM = 0x003;
    private static final String CROP_FILE_NAME = "crop_pic.jpg";
    private static final String OUTPUT_FORMAT = Bitmap.CompressFormat.JPEG.toString();
    private static final String CROP_TYPE = "image/*";
    private static final int DEFAULT_ASPECT = 1;
    private static final int DEFAULT_OUTPUT = 640;
    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";

    private Uri cameraOutPutUri = Uri.parse(IMAGE_FILE_LOCATION);//拍照后保存路径
    private Bitmap uploadBitmap;
    private Uri cropOutPutUri;//裁剪图片后保存的路径

    private ImageView imageView;
    private ImgSelectDialog imgSelectDialog;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_photo);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImgSelectDialog();
            }
        });
        //判断存储的图片是否为空，并对图片进行相关设置
        String url = THSharePreferencesHelperUtil.getUserPhotoUrl();
        if(THSharePreferencesHelperUtil.getUserPhotoUrl() == null || THSharePreferencesHelperUtil.getUserPhotoUrl() == ""){
            imageView.setImageDrawable(getDrawable(R.drawable.trphoto));
        }else {
            imageView.setImageBitmap(decodeSampledBitmapFromPath(THSharePreferencesHelperUtil.getUserPhotoUrl()));
        }
    }

    /**
     * 显示修改图片对话框
     */
    private void showImgSelectDialog(){
        imgSelectDialog = new ImgSelectDialog(TextPhotoActivity.this,R.style.select_pic_dialog);
        imgSelectDialog.setImgSelectDialogListener(new ImgSelectDialog.ImgSelectDialogListener() {
            @Override
            public void cancleDialog() {
                imgSelectDialog.cancel();
            }
        });
        imgSelectDialog.show();

        //实例化一个imgSelectDialog窗体管理对象
        Window dialogWindow = imgSelectDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置dialog布局方式，为底部布局
        dialogWindow.setGravity(Gravity.BOTTOM);
        //实例化一个TextPhotoActivity窗体管理对象
        WindowManager windowManager = TextPhotoActivity.this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        lp.width = (display.getWidth());
        //设置dialog不可取消
        imgSelectDialog.setCanceledOnTouchOutside(false);
        //设置dialog当前宽度
        imgSelectDialog.getWindow().setAttributes(lp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                //拍照之后
                case REQUEST_CAMERA:
                    onCameraResult();
                    break;
                //选择相册之后（注：data.getData()为选取具体图像后的地址）
                case REQUEST_ALBUM:
                    onAlbumResult(data.getData());
                    break;
                //裁剪之后
                case REQUEST_CROP:
                    onCropResult();
                    break;
            }
        }
    }

    /**
     * 拍照之后返回结果处理
     */
    public void onCameraResult() {
        cropPhoto(cameraOutPutUri);
    }

    /**
     * 选择图片之后返回结果处理
     */
    public void onAlbumResult(Uri uri) {
        cropPhoto(uri);
    }

    /**
     * 裁剪图片之后返回结果处理
     */
    public void onCropResult() {
        //将裁剪图片后的图片，由uri转化为String再转化为bitmap
        uploadBitmap = decodeSampledBitmapFromPath(cropOutPutUri.getPath());
        //裁剪后存储其图片
        THSharePreferencesHelperUtil.setUserPhotoUrl(cropOutPutUri.getPath());
        //修改并上传头像
        uploadPic();
        imgSelectDialog.dismiss();
    }

    /**
     * 修改并上传头像
     */
    private void uploadPic() {
        imageView.setImageBitmap(uploadBitmap);
        Toast.showToast(TextPhotoActivity.this,"√ 修改成功！");
    }

    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void cropPhoto(Uri uri) {
        try {
            //裁切照片完成之后存储照片的位置
            cropOutPutUri = Uri.fromFile(TextPhotoActivity.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)).buildUpon()
                    .appendPath(System.currentTimeMillis() + "_" + CROP_FILE_NAME).build();

            //跳转到裁切图片界面(并设置相关的属性、参数)
            Intent intent = new Intent("com.android.camera.action.CROP", null)
                    .setDataAndType(uri, CROP_TYPE)
                    .putExtra("crop", true)
                    .putExtra("scale", true)
                    .putExtra("aspectX", DEFAULT_ASPECT)
                    .putExtra("aspectY", DEFAULT_ASPECT)
                    .putExtra("outputX", DEFAULT_OUTPUT)
                    .putExtra("outputY", DEFAULT_OUTPUT)
                    .putExtra("return-data", false)
                    .putExtra("outputFormat", OUTPUT_FORMAT)
                    .putExtra("noFaceDetection", true)
                    .putExtra("scaleUpIfNeeded", true)
                    //裁切照片完成之后存储照片的位置
                    .putExtra(MediaStore.EXTRA_OUTPUT, cropOutPutUri);

            TextPhotoActivity.this.startActivityForResult(intent, TextPhotoActivity.REQUEST_CROP);
        } catch (Exception e) {
            String msg = "";
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                msg = "图片裁剪失败,SD卡不存在，请插入SD卡";
            } else {
                msg = "图片裁剪失败,请稍后再试";
            }
            Toast.showToast(TextPhotoActivity.this,msg);
        }
    }

    //////////////////
    /**
     * 解码采样图片路径(即为：将图片的String类型转化为Bitmap类型)
     *
     * @param originalPath
     * @return
     */
    public static Bitmap decodeSampledBitmapFromPath(String originalPath) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 400, 400);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(originalPath, options);
    }

    /**
     * 计算缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
