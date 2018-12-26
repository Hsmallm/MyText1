package com.example.administrator.text1.ui.testCourse;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * @author HuangMing on 2018/11/27.
 */

public class UpdateService extends Service {

    //Android SDk工具基于.aidl文件使用java语言生成一个接口 这个接口有一个内部抽象类，
    //叫做Stub，它是继承Binder并且实现你AIDL接口的 你必须继承这个Stub类并且实现这些方法，实现一个service并且覆盖onBind()方法返回你的Stub实现类。
    private String aidl = "aidl";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyServiceImp();
    }

    private class MyServiceImp extends IMyAidlInterface.Stub {

        @Override
        public String getValue() throws RemoteException {
            return aidl;
        }
    }
}
