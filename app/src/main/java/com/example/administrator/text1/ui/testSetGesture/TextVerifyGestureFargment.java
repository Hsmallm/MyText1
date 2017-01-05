package com.example.administrator.text1.ui.testSetGesture;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testWebView.TextWebViewActivity;
import com.example.administrator.text1.utils.THSharePreferencesHelperUtil;
import com.seaway.android.common.widget.LockPatternView;
import com.seaway.android.toolkit.security.SWMD5;

import java.util.List;

/**功能描述：验证手势密码
 * Created by hzhm on 2016/6/17.
 */
public class TextVerifyGestureFargment extends Fragment implements LockPatternView.OnPatternListener{

    //错误次数
    private int mFailedCounts;

    private TextView txt;
    private LockPatternView lockPatternView;
    //还原LockPatternView
    private Runnable mCleanLockPatternView = new Runnable() {
        @Override
        public void run() {
            lockPatternView.clearPattern();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.frg_text_verifygesture,container,false);
        return  view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txt = (TextView) getActivity().findViewById(R.id.txt);
        lockPatternView = (LockPatternView) getActivity().findViewById(R.id.lockPatternView);
        lockPatternView.setOnPatternListener(this);
    }

    @Override
    public void onPatternStart() {
        lockPatternView.removeCallbacks(mCleanLockPatternView);
    }

    @Override
    public void onPatternCleared() {
        lockPatternView.removeCallbacks(mCleanLockPatternView);
    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        if(pattern == null){
            return;
        }
        String patternTxt = SWMD5.getMD5Str(LockPatternView.patternToString(pattern));
        //a、如果验证正确
        if(THSharePreferencesHelperUtil.getLockPattern().equals(patternTxt)){
            lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
            Intent intent = new Intent(getActivity(), TextWebViewActivity.class);
            startActivity(intent);
            getActivity().finish();
        //b、如果验证错误
        }else {
            lockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            //b.1、当连接点数大于等于5
            if(pattern.size() >= TextSetGesture.MIN_PATTERN_REGISTER_FAIL ){
                mFailedCounts++;
                //重新可绘制次数
                int retry = TextSetGesture.FAILED_ATTEMPTS_BEFORE_TIMEOUT - mFailedCounts;
                //b.1.1、如果重新可绘制次数等于0,则不可再进行绘制
//                if(retry == 0){
//                    UIDefaultDialogHelper.showDefaultAlert(getActivity(), getString(R.string.unlockScrren_access_hands_pwd_error), new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            UIDefaultDialogHelper.dialog.dismiss();
//                            UIDefaultDialogHelper.dialog = null;
//                            Toast.showToast(getActivity(),"跳转到登录页！");
//                        }
//                    });
//                    return;
//                }
                //b.1.2如果重新可绘制次数不等于0,则可再次绘制，显示相应提示
//                String msg = String.format(getString(R.string.unlockScrren_access_pattern_times), retry);
                String msg = "密码错误，请再试一次";
                txt.setText(msg);
                txt.setTextColor(getResources().getColor(R.color.default_lock_patten_view_fill_wrong_color));
            //b.2、当连接点数不大于等于5
            }else {
                txt.setText(getString(R.string.lockscreen_access_pattern_too_short));
                txt.setTextColor(getResources().getColor(R.color.default_lock_patten_view_fill_wrong_color));
            }

            //lockPatternView视图的清空
            if(mFailedCounts >= TextSetGesture.FAILED_ATTEMPTS_BEFORE_TIMEOUT){
                lockPatternView.clearPattern();
                mFailedCounts = 0;
            }else {
                lockPatternView.postDelayed(mCleanLockPatternView,1000);
            }
        }
    }
}
