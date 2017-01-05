package com.example.administrator.text1.ui.testPhotos;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.administrator.text1.R;

/**
 * Created by hzhm on 2016/6/8.
 */
public class ImgSelectDialog extends Dialog implements View.OnClickListener {
    //拍照后、选择图片后，保存图片的路径
    private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";
    private Uri cameraOutPutUri = Uri.parse(IMAGE_FILE_LOCATION);

    private Context context;
    private Button btnTakePhoto;
    private Button btnPhotoAlbums;
    private Button btnCancel;

    private ImgSelectDialogListener listener;

    public ImgSelectDialog(Context context, int themeResId) {
        super(context, themeResId);
        initContext(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_select_photo, null));
        btnTakePhoto = (Button) findViewById(R.id.select_take_photo);
        btnPhotoAlbums = (Button) findViewById(R.id.select_photo_album);
        btnCancel = (Button) findViewById(R.id.select_choose_cancel);
        btnTakePhoto.setOnClickListener(this);
        btnPhotoAlbums.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void initContext(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_take_photo:
                //拍照
                takePhoto();
                listener.cancleDialog();
                break;

            case R.id.select_photo_album:
                //选择相册
                selectPhotos();
                listener.cancleDialog();
                break;

            case R.id.select_choose_cancel:
                //取消
                listener.cancleDialog();
                break;

            default:
                break;
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //设置拍摄照片的存储地址
        intent.putExtra(MediaStore.EXTRA_OUTPUT,cameraOutPutUri);
        ((Activity)context).startActivityForResult(intent, TextPhotoActivity.REQUEST_CAMERA);

    }

    /**
     * 选择相册
     */
    private void selectPhotos() {
        Intent intent = new Intent(Intent.ACTION_PICK,null);
        //设置开始跳往相册的路径
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        //设置选择照片的存储地址
        intent.putExtra(MediaStore.EXTRA_OUTPUT,cameraOutPutUri);
        ((Activity)context).startActivityForResult(intent, TextPhotoActivity.REQUEST_ALBUM);
    }

    public void setImgSelectDialogListener(ImgSelectDialogListener listener) {
        this.listener = listener;
    }

    public interface ImgSelectDialogListener {
        void cancleDialog();
    }
}
