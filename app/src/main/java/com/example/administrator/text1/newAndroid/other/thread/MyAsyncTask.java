package com.example.administrator.text1.newAndroid.other.thread;

import android.os.AsyncTask;

import com.example.administrator.text1.ui.testVersionUpDate.DialogUpdate;
import com.example.administrator.text1.utils.ToastUtil;

/**
 * @author HuangMing on 2017/12/27.
 *         功能描述：AsyncTask，其实现原理也是异步消息处理机制
 *         继承时指定的3个泛型参数的含义：params:在执行AsyncTask传入，用于后台任务中使用
 *         progress:后台任务中使用，如需要在界面上显示当前进度时候，这里指定当前单位
 *         result:当前任务执行完毕之后，这里需要对结果进行返回，这里指定这个泛型作为返回值类型
 *         publishprogress()：调用此方法轻松由子线程切换到主线程，从而完成UI更新...
 */

public class MyAsyncTask extends AsyncTask<Void, Integer, Boolean> {

    private DialogUpdate dialogUpdate;

    /**
     * 主要进行初始化界面等相关操作
     */
    @Override
    protected void onPreExecute() {
        dialogUpdate.show();
    }

    /**
     * 执行一些耗时任务...
     *
     * @param voids
     * @return
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        while (true) {
            int downloadPercent = doDownload();
            publishProgress(downloadPercent);
            if (downloadPercent >= 100) {
                break;
            }
        }
        return true;
    }

    /**
     * 更新相关任务执行进度，等其他UI操作
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        dialogUpdate.setProgress(values[0]);
    }

    /**
     * 返回执行结果，并对此进行相关操作（如：UI的相关操作...）
     *
     * @param aBoolean
     */
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        dialogUpdate.dismiss();
        if (aBoolean) {
            ToastUtil.showNormalToast("Download success!");
        } else {
            ToastUtil.showNormalToast("Download failed!");
        }
    }

    private int doDownload() {
        return 0;
    }
}
