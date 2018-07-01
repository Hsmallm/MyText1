package com.example.administrator.text1.newAndroid.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.text1.R;
import com.example.administrator.text1.utils.ToastUtil;
import com.seaway.android.common.toast.Toast;

/**
 * @author HuangMing on 2017/11/27.
 *         功能描述：Serializable由于会把整个对象都进行序列化，因此效率和性能上会比parcelable方式要低
 */

public class TestIntent extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sharedpreferences);

        Person person = (Person) getIntent().getSerializableExtra("person");
        Person2 person2 = (Person2) getIntent().getParcelableExtra("person");
        Toast.showToast(MyApplication.getContext(), "Name:" + person2.getName() + "  Age:" + person2.getAge());
    }
}
