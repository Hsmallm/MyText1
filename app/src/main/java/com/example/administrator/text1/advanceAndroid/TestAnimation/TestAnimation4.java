package com.example.administrator.text1.advanceAndroid.TestAnimation;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.text1.R;

/**
 * @author HuangMing on 2018/2/3.
 *         功能描述：Android Transition：是Android4.4引入的新的动画框架，本质上还是属于属性动画，只是对其做了进一步的封装
 *         与属性动画的不同：为动画前后准备不同的布局，并通过对应的Api实现过度动画的效果，而属性动画只需要一个布局文件
 */

public class TestAnimation4 extends AppCompatActivity {

    private Button startBrn;
    private Button endBtn;
    private ImageView imageView;

    private Scene scene1;
    private Scene scene2;
    private ViewGroup sceneRoot;
    private boolean isScene2 = false;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_animation);

        startBrn = (Button) findViewById(R.id.startBtn);
        endBtn = (Button) findViewById(R.id.endBtn);
        imageView = (ImageView) findViewById(R.id.imageView);
        sceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        startBrn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                //检测view的位置边界创建移动和缩放动画
//                change(new ChangeBounds());
                //检测view的scale和rotation创建缩放和旋转动画
//                change(new ChangeTransform());
                //检测view的剪切区域的位置边界，和ChangeBounds类似。
                //不过ChangeBounds针对的是view而ChangeClipBounds针对的是view的剪切区域(setClipBound(Rect rect) 中的rect)。如果没有设置则没有动画效果
//                change(new ChangeClipBounds());
                //检测ImageView（这里是专指ImageView）的尺寸，位置以及ScaleType，并创建相应动画。
//                change(new ChangeImageTransform());
                //这三个都是根据view的visibility的不同分别创建渐入，滑动，爆炸动画。
                change(new Explode());
            }
        });

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//        initScene(R.id.scene_root,R.layout.scene_1,R.layout.scene_2);
//        initScene(R.id.scene_root, R.layout.scene_1_changetransform, R.layout.scene_2_changetransform);
//        initClipBoundsScene();
//        initScene(R.id.scene_root, R.layout.scene_1_changeimagetransform, R.layout.scene_2_changeimagetransform);
        initScene(R.id.scene_root, R.layout.scene_1_explode, R.layout.scene_2_explode);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initClipBoundsScene() {
        View inflate1 = LayoutInflater.from(this).inflate(R.layout.scene_1_changeclipbounds, null);
        View inflate2 = LayoutInflater.from(this).inflate(R.layout.scene_2_changeclipbounds, null);

        ImageView imageView1 = (ImageView) inflate1.findViewById(R.id.cutegirl);
        ImageView imageView2 = (ImageView) inflate2.findViewById(R.id.cutegirl);

        imageView1.setClipBounds(new Rect(0, 0, 200, 200));
        imageView2.setClipBounds(new Rect(200, 200, 400, 400));

        scene1 = new Scene(sceneRoot, inflate1);
        scene2 = new Scene(sceneRoot, inflate2);
        TransitionManager.go(scene1);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initScene(int scene_root, int scene_1, int scene_2) {
        ViewGroup sceneRoot = (ViewGroup) findViewById(scene_root);
        scene1 = Scene.getSceneForLayout(sceneRoot, scene_1, this);
        scene2 = Scene.getSceneForLayout(sceneRoot, scene_2, this);
        TransitionManager.go(scene1);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void change(Transition transition) {
        TransitionManager.go(isScene2 ? scene1 : scene2, transition);
        isScene2 = !isScene2;
    }
}
