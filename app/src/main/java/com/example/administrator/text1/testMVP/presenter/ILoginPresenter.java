package com.example.administrator.text1.testMVP.presenter;

/**
 * @author HuangMing on 2018/11/5.
 */

public interface ILoginPresenter {

    void clear();

    void doLogin(String name, String password);

    void setProgressBarVisiblity(int visiblity);
}
