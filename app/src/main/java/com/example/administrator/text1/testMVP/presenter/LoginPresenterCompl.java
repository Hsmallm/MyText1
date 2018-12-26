package com.example.administrator.text1.testMVP.presenter;

import android.os.Handler;
import android.os.Looper;

import com.example.administrator.text1.testMVP.model.IUser;
import com.example.administrator.text1.testMVP.model.UserModel;
import com.example.administrator.text1.testMVP.view.ILoginView;

/**
 * @author HuangMing on 2018/11/5.
 */

public class LoginPresenterCompl implements ILoginPresenter {

    private ILoginView iLoginView;
    private IUser iUser;
    private Handler handler;

    public LoginPresenterCompl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
        handler = new Handler(Looper.getMainLooper());
        initUser();
    }

    private void initUser() {
        iUser = new UserModel("mvp", "mvp");
    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void doLogin(String name, String password) {
        Boolean isLoginSuccess = true;
        final int code = iUser.checkUserValidity(name, password);
        if (code != 0) {
            isLoginSuccess = false;
        }
        final Boolean result = isLoginSuccess;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginResult(result, code);
            }
        }, 5000);

    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {
        iLoginView.onSetProgressBarVisibility(visiblity);
    }
}
