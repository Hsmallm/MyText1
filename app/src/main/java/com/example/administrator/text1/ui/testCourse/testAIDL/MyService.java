package com.example.administrator.text1.ui.testCourse.testAIDL;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.administrator.text1.IGetUser;
import com.example.administrator.text1.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangMing on 2018/11/29.
 */

public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iGetUser;
    }

    private IGetUser.Stub iGetUser = new IGetUser.Stub() {
        @Override
        public List<Person> getPersonInfo(String value) throws RemoteException {
            List<Person> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                list.add(new Person(value + i, value + (i >> 2)));
            }
            return list;
        }
    };
}
