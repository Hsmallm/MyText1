package com.example.administrator.text1.ui.testDownLoad;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrator.text1.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author HuangMing on 2018/11/21.
 *         Des：OkHttp实现文件下载保持
 */

public class TestDownload extends AppCompatActivity implements View.OnClickListener {

    private String[] urls = {"https://mirrors.tuna.tsinghua.edu.cn/cygwin/x86_64/setup.bz2",
            "https://mirrors.tuna.tsinghua.edu.cn/centos/filelist.gz",
            "https://mirrors.tuna.tsinghua.edu.cn/anaconda/miniconda/Miniconda-3.6.0-Linux-x86.sh"};

    private Button bt1;
    private Button bt2;
    private Button bt3;
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_download);

        bt1 = (Button) findViewById(R.id.bt_download1);
        bt2 = (Button) findViewById(R.id.bt_download2);
        bt3 = (Button) findViewById(R.id.bt_download3);

        progressBar1 = (ProgressBar) findViewById(R.id.progress1);
        progressBar2 = (ProgressBar) findViewById(R.id.progress2);
        progressBar3 = (ProgressBar) findViewById(R.id.progress3);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_download1:
                progressBar1.setVisibility(View.VISIBLE);
                fileDownload(urls[0], new UpdateProgressListener() {
                    @Override
                    public void updateProgress(int progress) {
                        progressBar1.setProgress(progress);
                    }
                });
                break;
            case R.id.bt_download2:
                progressBar2.setVisibility(View.VISIBLE);
                fileDownload(urls[1], new UpdateProgressListener() {
                    @Override
                    public void updateProgress(int progress) {
                        progressBar2.setProgress(progress);
                    }
                });
                break;
            case R.id.bt_download3:
                progressBar3.setVisibility(View.VISIBLE);
                fileDownload(urls[2], new UpdateProgressListener() {
                    @Override
                    public void updateProgress(int progress) {
                        progressBar3.setProgress(progress);
                    }
                });
                break;
            default:
                break;
        }
    }

    private void fileDownload(final String url, final UpdateProgressListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream is;
                File file = null;
                RandomAccessFile savedFile = null;

                try {
                    long fileLength = getFileLength(url);
                    String fileName = getFileName(url);
                    int downLoadLength = 0;
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response != null && response.isSuccessful()) {
                        // 应用关联目录，无需申请读写存储的运行时权限
                        // getExternalCacheDir：位于/sdcard/Android/data/包名/cache
                        file = new File(getExternalCacheDir() + fileName);
                        // 随机访问，可通过seek方法定位到文件的任意位置，方便断点续传
                        savedFile = new RandomAccessFile(file, "rw");
                        is = response.body().byteStream();
                        byte[] buffer = new byte[1024];
                        int len;

                        int total = 0;
                        //循环读取文件流(len表示一节一节被读取的量，“-1”则表示读取完成)
                        while ((len = is.read(buffer)) != -1) {
                            savedFile.write(buffer, 0, len);
                            total += len;
                            // 注意这里要先乘以100再除，否则java的除法中小数直接抹去后面的，我们得到的比值比如0.5直接就变成0，progress也就为0了
                            final int progress = (int) ((total + downLoadLength) * 100 / fileLength);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listener.updateProgress(progress);
                                }
                            });
                        }
                        // response.body().string()只能调用一次，再次调用报错。
                        // 写完后可以把body关了
                        response.body().close();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "下载成功！");
                            }
                        });
                    } else {
                        // response为空或者请求的状态码没有成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "下载失败！");
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (savedFile != null) {
                            savedFile.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * 获得文件长度
     *
     * @param url
     * @return
     * @throws IOException
     */
    private long getFileLength(String url) throws IOException {
        long contentLength = 0;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response != null && response.isSuccessful()) {
            contentLength = response.body().contentLength();
            response.body().close();
        }
        return contentLength;
    }

    /**
     * 获取文件名称
     *
     * @param url
     * @return
     */
    private String getFileName(String url) {
        //即表示：截取最后一个“/”后的文本作为文件名称
        return url.substring(url.lastIndexOf("/"));
    }

    interface UpdateProgressListener {
        void updateProgress(int progress);
    }
}
