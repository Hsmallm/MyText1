package com.example.administrator.text1.testMVP;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.text1.R;
import com.example.administrator.text1.testMVP.presenter.LoginPresenterCompl;
import com.example.administrator.text1.testMVP.view.ILoginView;
import com.example.administrator.text1.utils.ToastUtil;

/**
 * @author HuangMing on 2018/11/5.
 * MVP和MVC最大的不同：
 * 首先，和MVC最大的不同，MVP把activity作为了view层，通过代码也可以看到，
 * 整个activity没有任何和model层相关的逻辑代码（仅仅涉及到只是相关接口的调用），取而代之的是把代码放到了presenter层中，
 * presenter获取了model层的数据之后，通过接口的形式将view层需要的数据返回给它就OK了。
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {

    private EditText editName;
    private EditText editPass;
    private Button btnLogin;
    private Button btnClean;

    private LoginPresenterCompl loginPresenterCompl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editName = (EditText) findViewById(R.id.et_login_username);
        editPass = (EditText) findViewById(R.id.et_login_password);
        btnLogin = (Button) findViewById(R.id.btn_login_login);
        btnClean = (Button) findViewById(R.id.btn_login_clear);

        btnLogin.setOnClickListener(this);
        btnClean.setOnClickListener(this);

        loginPresenterCompl = new LoginPresenterCompl(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_login:
                loginPresenterCompl.doLogin(editName.getText().toString(),editPass.getText().toString());
                break;
            case R.id.btn_login_clear:
                loginPresenterCompl.clear();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClearText() {
        editName.setText("");
        editPass.setText("");
    }

    @Override
    public void onLoginResult(boolean result, int code) {
        if (result) {
            ToastUtil.showNormalToast("Login Success!");
        } else {
            ToastUtil.showNormalToast("Login Failed!" + code);
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {

    }
}
