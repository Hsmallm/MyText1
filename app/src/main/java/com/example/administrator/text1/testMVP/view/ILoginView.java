package com.example.administrator.text1.testMVP.view;

/**
 * @author HuangMing on 2018/11/5.
 */

public interface ILoginView {

    void onClearText();

    void onLoginResult(boolean result, int code);

    void onSetProgressBarVisibility(int visibility);
}
