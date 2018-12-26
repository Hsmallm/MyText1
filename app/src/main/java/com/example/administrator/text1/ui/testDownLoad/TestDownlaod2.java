package com.example.administrator.text1.ui.testDownLoad;

import android.Manifest;
import android.app.DownloadManager;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.text1.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/**
 * @author HuangMing on 2018/11/21.
 *         Des:多种方式实现文件下载，自己封装URLConnection、Android自定的下载管理（会在notification 显示下载的进度，同时可以暂停、重新连接等）、使用okhttp实现
 */

public class TestDownlaod2 extends AppCompatActivity implements View.OnClickListener {

    private String[] urls = {"https://mirrors.tuna.tsinghua.edu.cn/cygwin/x86_64/setup.bz2",
            "https://mirrors.tuna.tsinghua.edu.cn/centos/filelist.gz",
            "https://mirrors.tuna.tsinghua.edu.cn/anaconda/miniconda/Miniconda-3.6.0-Linux-x86.sh"};

    private Button bt1;
    private Button bt2;
    private Button bt3;
    private TextView tvProgressPre1;
    private TextView tvProgressPre2;
    private TextView tvProgressPre3;
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

        tvProgressPre1 = (TextView) findViewById(R.id.progress_per1);
        tvProgressPre2 = (TextView) findViewById(R.id.progress_per2);
        tvProgressPre3 = (TextView) findViewById(R.id.progress_per3);

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
                fileDownload(new FileDownLoadListener() {
                    @Override
                    public void getProgress(int progress) {
                        tvProgressPre1.setText(progress + "%");
                        progressBar1.setProgress(progress);
                    }
                });
                break;
            case R.id.bt_download2:
                progressBar2.setVisibility(View.VISIBLE);
                checkPermissionToDetect();
                break;
            case R.id.bt_download3:
                progressBar3.setVisibility(View.VISIBLE);
                fileDownLoad3(urls[2], new FileDownLoadListener() {
                    @Override
                    public void getProgress(int progress) {
                        tvProgressPre3.setText(progress + "%");
                        progressBar3.setProgress(progress);
                    }
                });
                break;
            default:
                break;
        }
    }

    /**
     * 1、自己封装URLConnection 连接请求类来处理文件下载
     * Des：这种方式在Android 刚兴起的时候，很少下载封装框架，就自己封装了。虽然一般的文件都能下载，但这种方式缺点很多，不稳定或者各种各样的问题会出现。
     *
     * @param fileDownLoadListener
     */
    private void fileDownload(final FileDownLoadListener fileDownLoadListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String path = getExternalCacheDir().getPath();
                String fileName = urls[0].substring(urls[0].lastIndexOf("/"));

                try {
                    URL url = new URL(urls[0]);
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    int fileSize = connection.getContentLength();
                    if (fileSize <= 0) {
                        throw new RuntimeException("无法获知文件大小");
                    }
                    if (inputStream == null) {
                        throw new RuntimeException("stream is null");
                    }
                    File file1 = new File(path);
                    if (!file1.exists()) {
                        file1.mkdirs();
                    }
                    FileOutputStream fileOutputStream = new FileOutputStream(path + "/" + fileName);
                    byte[] bytes = new byte[1024];
                    int downLoadFileSize = 0;
                    do {
                        int numRead = inputStream.read(bytes);
                        if (numRead == -1) {
                            break;
                        }
                        fileOutputStream.write(bytes, 0, numRead);
                        downLoadFileSize += numRead;
                        final int progress = downLoadFileSize * 100 / fileSize;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fileDownLoadListener.getProgress(progress);
                            }
                        });
                    } while (true);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "下载成功！");
                        }
                    });
                    inputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 2、Android自定的下载管理（会在notification 显示下载的进度，同时可以暂停、重新连接等）
     * Des：种方式其实就是交给了Android系统的另一个app去下载管理。这样的好处不会消耗该APP的 CPU资源。缺点是：控制起来很不灵活
     *
     * @param url
     */
    private void fileDownload2(String url) {
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir(getExternalCacheDir().getPath(), url.substring(url.lastIndexOf("/") + 1));
        //获取下载管理器
        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(request);
    }

    /**
     * 3、okhttp是一个很有名气的开源框架，目前已经很多大公司都直接使用它作为网络请求库（七牛云SDK， 阿里云SDK）。
     * 且里面集成了很多优势，包括 okio (一个I/O框架，优化内存与CPU)。
     *
     * @param url
     */
    private void fileDownLoad3(final String url, final FileDownLoadListener fileDownLoadListener) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "下载失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Sink sink = null;
//                BufferedSink bufferedSink = null;
//                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), url.substring(url.lastIndexOf("/")));
//                sink = Okio.sink(file);
//                bufferedSink = Okio.buffer(sink);
//                bufferedSink.writeAll(response.body().source());
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "下载成功！");
//                    }
//                });
                RandomAccessFile savedFile = null;
                try {
                    if (response != null) {
                        long fileLength = getFileLength(url);
                        String fileName = getFileName(url);
                        File file = new File(getExternalCacheDir(), fileName);
                        savedFile = new RandomAccessFile(file, "rw");
                        InputStream inputStream = response.body().byteStream();
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        int total = 0;
                        while ((len = inputStream.read(bytes)) != -1) {
                            savedFile.write(len);
                            total += len;
                            final int progress = (int) (total * 100 / fileLength);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fileDownLoadListener.getProgress(progress);
                                }
                            });
                        }
                        response.body().close();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "下载成功！");
                            }
                        });
                    } else {
                        com.seaway.android.common.toast.Toast.showToast(com.example.administrator.text1.newAndroid.other.MyApplication.getContext(), "下载失败！");
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
        });
    }

    /**
     * 动态权限申请
     */
    private void checkPermissionToDetect() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = null;
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (permissions == null) {
                    permissions = new ArrayList<>();
                }
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissions != null) {
                String[] permissionArray = new String[permissions.size()];
                permissions.toArray(permissionArray);
                /// Request the permission. The result will be received
                // in onRequestPermissionResult()
                requestPermissions(permissionArray, 0);
            } else {
                fileDownload2(urls[1]);
            }
        } else {
            fileDownload2(urls[1]);
        }
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


    interface FileDownLoadListener {
        void getProgress(int progress);
    }
}
