package com.example.administrator.text1.ui.testSetGesture;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testWebView.TextWebViewActivity;
import com.example.administrator.text1.utils.THSharePreferencesHelperUtil;
import com.example.administrator.text1.utils.VibratorUtil;
import com.seaway.android.common.toast.Toast;
import com.seaway.android.common.widget.LockPatternPreView;
import com.seaway.android.common.widget.LockPatternView;
import com.seaway.android.toolkit.security.SWMD5;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能测试：测试如何设置手势密码（即为：如何使用LockPatternPreView、LockPatternView这两个组建绘制相应的手势密码）
 * Created by hzhm on 2016/6/16.
 */
public class TextSetGestureFragment extends Fragment implements LockPatternView.OnPatternListener {

    //绘制锁屏的预览视图
    private LockPatternPreView lockPatternPreView;
    //绘制锁屏的视图
    private LockPatternView lockPatternView;
    //提示文本
    private TextView txtHint;
    //绘制视图相应的状态（枚举类型）（初始化当前状态）
    private Stage mUiStage = Stage.Introduction;
    //当前绘制的视图
    private List<LockPatternView.Cell> mChosenPattern = null;
    private long[] pattern;
    private TranslateAnimation translateAnimation;

    //还原LockPatternView
    private Runnable mClearPatternRunnable = new Runnable() {
        @Override
        public void run() {
            lockPatternView.clearPattern();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frg_text_setgesture,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.getView().setFocusable(true);
        this.getView().setFocusableInTouchMode(true);
        this.getView().requestFocus();

        pattern = new long[]{0,200,0,200,0,200};
        //------实例化位移动画
        translateAnimation = new TranslateAnimation(0,50,0,0);
        //测试持续时间
        translateAnimation.setDuration(100);
        //设置未重复模式
        translateAnimation.setRepeatMode(Animation.REVERSE);
        //设置重复次数
        translateAnimation.setRepeatCount(2);
        //动画执行完后是否停留在执行完的状态
        translateAnimation.setFillAfter(false);

        txtHint = (TextView) getActivity().findViewById(R.id.hintxt);
        lockPatternPreView = (LockPatternPreView) getActivity().findViewById(R.id.lock_preview);
        lockPatternView = (LockPatternView) getActivity().findViewById(R.id.lock_pattern_view);
        lockPatternView.setOnPatternListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPatternStart() {
        lockPatternView.removeCallbacks(mClearPatternRunnable);
    }

    @Override
    public void onPatternCleared() {
        lockPatternView.removeCallbacks(mClearPatternRunnable);
    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        if(pattern == null){
            return;
        }
        //A、当第一次绘制正确，进行第二、三.....次绘图监听
        if(mChosenPattern != null){
            if(mUiStage == Stage.NeedToConfirm || mUiStage == Stage.ConfirmWrong || mUiStage == Stage.ChoiceTooShort){
                if(mChosenPattern == null){

                }
                //1、如果两次绘制相等，绘制正确
                if(mChosenPattern.equals(pattern) ){
                    //---设置成功
                    updateStage(Stage.ChoiceConfirmed);
                    //2、如果两次绘制不相等，绘制失败
                }else {
                    //a、第一次成功的前提下，第n次绘制的长度小于4
                    if(pattern.size() < TextSetGesture.MIN_LOCK_PATTERN_SIZE){
                        updateStage(Stage.ChoiceTooShort);
                    }else { //b、第一次成功的前提下，第n次绘制的长度大于4
                        //---两次绘制手势密码不一致，请重新输入
                        updateStage(Stage.ConfirmWrong);
                    }

                }
            }
        }else {//B、第一次绘图或多次绘图错误监听
            if(mUiStage == Stage.Introduction || mUiStage == Stage.ChoiceTooShort){
                //1、第一次绘制绘制错误
                if(pattern.size() < TextSetGesture.MIN_LOCK_PATTERN_SIZE){
                    updateStage(Stage.ChoiceTooShort);
                    //2、第一次绘制正确
                }else {
                    //初始化当前视图
                    mChosenPattern = new ArrayList<LockPatternView.Cell>(pattern);
                    updateStage(Stage.FirstChoiceValid);
                }
            }else {

            }
        }

    }

    /**
     * 更新当前视图
     * @param stage
     */
    private void updateStage(Stage stage){
        mUiStage = stage;
        //设置提示文本
        if(stage == Stage.ChoiceTooShort || stage == Stage.ConfirmWrong){
            txtHint.setText(getResources().getString(stage.headerMessage,TextSetGesture.MIN_LOCK_PATTERN_SIZE));
            txtHint.setTextColor(Color.parseColor("#aa0e0e"));
        }else {
            txtHint.setText(stage.headerMessage);
            txtHint.setTextColor(Color.parseColor("#ffffff"));
        }
        if(stage.patternEnabled){
            //开启输入
            lockPatternView.enableInput();
        }else {
            //禁用输入
            lockPatternView.disableInput();
        }

        switch (mUiStage){
            case Introduction:
                lockPatternView.clearPattern();
                break;
            //如果第一次、到第n次绘制太短
            case ChoiceTooShort:
                lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                //-------实现文字抖动、手机震荡
//                VibratorUtil.Vibrate(getActivity(),pattern,true);
                VibratorUtil.Vibrate(getActivity(),100);
                txtHint.setAnimation(translateAnimation);
                //清除当前绘制的视图
                postClearPatternRunnable();
                break;
            //如果第一次绘制正确
            case FirstChoiceValid:
                lockPatternView.clearPattern();
                lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
                updateStage(Stage.NeedToConfirm);
                break;
            //如果再次绘图
            case NeedToConfirm:
                lockPatternView.clearPattern();
                //更新预览图
                updatePreviewViews();
                break;

            //如果第二次、n...绘制错误
            case ConfirmWrong:
                lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                //-------实现文字抖动、手机震荡
//                VibratorUtil.Vibrate(getActivity(),pattern,true);
                VibratorUtil.Vibrate(getActivity(),100);
                txtHint.setAnimation(translateAnimation);
                //清除当前绘制的视图
                postClearPatternRunnable();
                break;
            //如果第二次、n...绘制成功
            case ChoiceConfirmed:
                saveChosenPatternAndFinish();
                break;
        }
    }

    /**
     * 清除当前绘制的视图
     */
    private void postClearPatternRunnable(){
        lockPatternView.removeCallbacks(mClearPatternRunnable);
        lockPatternView.postDelayed(mClearPatternRunnable,2000);
    }

    /**
     * 更新预览图
     */
    private void updatePreviewViews(){
        if(null == mChosenPattern)
            return;
        lockPatternPreView.updateView(mChosenPattern);
    }

    /**
     * 保存手势密码
     */
    private void saveChosenPatternAndFinish(){
        String lockPatternTxt = SWMD5.getMD5Str(LockPatternView.patternToString(mChosenPattern));
        THSharePreferencesHelperUtil.setLockPattern(lockPatternTxt);
        Toast.showToast(getActivity(),"手势密码设置成功!");

        Intent intent = new Intent(getActivity(), TextWebViewActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * 定义一个枚举类型
     */
    protected enum Stage{

        //使用下面的构造函数，实例化并定义相关的枚举类型...
        Introduction(R.string.draw_gesture_passwd, true), ChoiceTooShort(
                R.string.gesture_tip_001, true), FirstChoiceValid(
                R.string.draw_gesture_passwd, false), NeedToConfirm(
                R.string.draw_gesture_paswd_again, true), ConfirmWrong(
                R.string.gesture_tip_002, true), ChoiceConfirmed(
                R.string.gesture_tip_003, false);

        final int headerMessage;
        final boolean patternEnabled;
        //实例化这个枚举的构造函数
        Stage(int headerMessage, boolean patternEnabled) {
            this.headerMessage = headerMessage;
            this.patternEnabled = patternEnabled;
        }
    }

}
