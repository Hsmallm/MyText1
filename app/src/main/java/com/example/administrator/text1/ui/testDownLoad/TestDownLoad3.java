package com.example.administrator.text1.ui.testDownLoad;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.othershe.baseadapter.ViewHolder;
import com.othershe.baseadapter.interfaces.OnItemChildClickListener;
import com.othershe.dutil.DUtil;
import com.othershe.dutil.callback.DownloadCallback;
import com.othershe.dutil.data.DownloadData;
import com.othershe.dutil.db.Db;
import com.othershe.dutil.download.DownloadManger;
import com.othershe.dutil.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author HuangMing on 2018/11/22.
 *         Des：DUtil，一个基于okhttp的文件下载、上传工具
 *         下载：支持多线程、断点续传下载，以及下载管理（单任务文件下载、多任务文件下载、service文件下载）
 */

public class TestDownLoad3 extends AppCompatActivity implements View.OnClickListener {

    private String[] urls = {"https://mirrors.tuna.tsinghua.edu.cn/cygwin/x86_64/setup.bz2",
            "https://mirrors.tuna.tsinghua.edu.cn/centos/filelist.gz",
            "https://mirrors.tuna.tsinghua.edu.cn/anaconda/miniconda/Miniconda-3.6.0-Linux-x86.sh"};

    private String url1 = "https://mirrors.tuna.tsinghua.edu.cn/cygwin/x86_64/setup.bz2";//欢乐斗地主
    private String url2 = "https://mirrors.tuna.tsinghua.edu.cn/centos/filelist.gz";//球球大作战
    private String url3 = "https://mirrors.tuna.tsinghua.edu.cn/anaconda/miniconda/Miniconda-3.6.0-Linux-x86.sh";//节奏大师
    /**
     * 外部存储：
     * Environment.getExternalStorageDirectory()：即表示存储为最外层根目录（/storage/emulated/0/DUtil/）
     * getExternalCacheDir：即表示存储为当前APP包目录的cache目录下
     * getExternalFilesDir：即表示存储为当前APP包目录的file目录下
     * 内部存储：
     * context.getFilesDir()	路径是:/data/data/< package name >/files/…
     * context.getCacheDir()	路径是:/data/data/< package name >/cach/…
     */
    private String path = Environment.getExternalStorageDirectory() + "/DUtil/";
//    private String path2 = getExternalFilesDir("/DUtil/").getAbsolutePath();

    private TextView tvTips;
    private TextView tvProgress;
    private ProgressBar progressBar;
    private TextView tvPause;
    private TextView tvGo;
    private TextView tvCancel;
    private TextView tvStartAgain;
    private RecyclerView recyclerView;

    private TextView tvServiceStart;
    private TextView tvServicePause;
    private TextView tvServiceResume;
    private TextView tvServiceCancel;
    private TextView tvServiceRestart;

    private DownloadManger downloadManger;
    private DownloadListAdapter downloadListAdapter;
    private DownloadService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (DownloadService.DownloadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_download3);

        tvTips = (TextView) findViewById(R.id.tip);
        tvProgress = (TextView) findViewById(R.id.progress);
        progressBar = (ProgressBar) findViewById(R.id.progress1);
        tvPause = (TextView) findViewById(R.id.pause);
        tvGo = (TextView) findViewById(R.id.go);
        tvCancel = (TextView) findViewById(R.id.cancel);
        tvStartAgain = (TextView) findViewById(R.id.start_again);
        recyclerView = (RecyclerView) findViewById(R.id.download_list);

        tvServiceStart = (TextView) findViewById(R.id.service_start);
        tvServicePause = (TextView) findViewById(R.id.service_pause);
        tvServiceResume = (TextView) findViewById(R.id.service_resume);
        tvServiceCancel = (TextView) findViewById(R.id.service_cancel);
        tvServiceRestart = (TextView) findViewById(R.id.service_restart);

        tvPause.setOnClickListener(this);
        tvGo.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvStartAgain.setOnClickListener(this);

        tvServiceStart.setOnClickListener(this);
        tvServicePause.setOnClickListener(this);
        tvServiceResume.setOnClickListener(this);
        tvServiceCancel.setOnClickListener(this);
        tvServiceRestart.setOnClickListener(this);

//        checkPermissionToDetect();
//        initRecyclerView();
        initService();
    }

    private void initDownloadManger() {
        downloadManger = DUtil.init(this)
                .url(urls[0])
                .name(urls[0].substring(urls[0].lastIndexOf("/")))
                .path(path)
                .build()
                .start(new DownloadCallback() {
                    @Override
                    public void onStart(long l, long l1, float v) {
                        tvTips.setText(urls[0].substring(urls[0].lastIndexOf("/")) + "准备下载中");
                        progressBar.setProgress((int) v);
                        tvProgress.setText(Utils.formatSize(l) + "/" + Utils.formatSize(l1) + "........" + v + "%");
                    }

                    @Override
                    public void onProgress(long l, long l1, float v) {
                        tvTips.setText(urls[0].substring(urls[0].lastIndexOf("/")) + "下载中");
                        progressBar.setProgress((int) v);
                        tvProgress.setText(Utils.formatSize(l) + "/" + Utils.formatSize(l1) + "........" + v + "%");
                    }

                    @Override
                    public void onPause() {
                        tvTips.setText(urls[0].substring(urls[0].lastIndexOf("/")) + "暂停中");
                    }

                    @Override
                    public void onCancel() {
                        tvTips.setText(urls[0].substring(urls[0].lastIndexOf("/")) + "已取消");
                    }

                    @Override
                    public void onFinish(File file) {
                        tvTips.setText(urls[0].substring(urls[0].lastIndexOf("/")) + "下载完成");
                    }

                    @Override
                    public void onWait() {

                    }

                    @Override
                    public void onError(String s) {
                        tvTips.setText(urls[0].substring(urls[0].lastIndexOf("/")) + "下载出错");
                    }
                });
    }

    private void initRecyclerView() {
        List<DownloadData> list = new ArrayList<>();
        if (Db.getInstance(this).getData(url1) != null) {
            list.add(Db.getInstance(this).getData(url1));
        } else {
            list.add(new DownloadData(url1, path, "欢乐斗地主.apk"));
        }
        if (Db.getInstance(this).getData(url2) != null) {
            list.add(Db.getInstance(this).getData(url2));
        } else {
            list.add(new DownloadData(url2, path, "球球大作战.apk"));
        }
        if (Db.getInstance(this).getData(url3) != null) {
            list.add(Db.getInstance(this).getData(url3));
        } else {
            list.add(new DownloadData(url3, path, "节奏大师.apk"));
        }

        downloadListAdapter = new DownloadListAdapter(this, list, false);
        downloadListAdapter.setOnItemChildClickListener(R.id.start, new OnItemChildClickListener<DownloadData>() {
            @Override
            public void onItemChildClick(ViewHolder viewHolder, DownloadData data, int position) {
                DownloadManger.getInstance(TestDownLoad3.this).start(data.getUrl());
            }
        });
        downloadListAdapter.setOnItemChildClickListener(R.id.pause, new OnItemChildClickListener<DownloadData>() {
            @Override
            public void onItemChildClick(ViewHolder viewHolder, DownloadData data, int position) {
                DownloadManger.getInstance(TestDownLoad3.this).pause(data.getUrl());
            }
        });
        downloadListAdapter.setOnItemChildClickListener(R.id.resume, new OnItemChildClickListener<DownloadData>() {
            @Override
            public void onItemChildClick(ViewHolder viewHolder, DownloadData data, int position) {
                DownloadManger.getInstance(TestDownLoad3.this).resume(data.getUrl());
            }
        });
        downloadListAdapter.setOnItemChildClickListener(R.id.cancel, new OnItemChildClickListener<DownloadData>() {
            @Override
            public void onItemChildClick(ViewHolder viewHolder, DownloadData data, int position) {
                DownloadManger.getInstance(TestDownLoad3.this).cancel(data.getUrl());
            }
        });
        downloadListAdapter.setOnItemChildClickListener(R.id.restart, new OnItemChildClickListener<DownloadData>() {
            @Override
            public void onItemChildClick(ViewHolder viewHolder, DownloadData data, int position) {
                DownloadManger.getInstance(TestDownLoad3.this).restart(data.getUrl());
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(downloadListAdapter);
    }

    private void initService() {
        Intent intent = new Intent(this, DownloadService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
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
                requestPermissions(permissionArray, 0);
            } else {
                initDownloadManger();
            }
        } else {
            initDownloadManger();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pause:
                downloadManger.pause(urls[0]);
                break;
            case R.id.go:
                downloadManger.resume(urls[0]);
                break;
            case R.id.cancel:
                downloadManger.cancel(urls[0]);
                break;
            case R.id.start_again:
                downloadManger.restart(urls[0]);
                break;
            case R.id.service_start:
                downloadBinder.startDownload(getExternalFilesDir("/DUtil/").getAbsolutePath(),
                        "高德地图.apk",
                        url1,
                        (int) System.currentTimeMillis());
                break;
            case R.id.service_pause:
                downloadBinder.pauseDownload(url1);
                break;
            case R.id.service_resume:
                downloadBinder.resumeDownload(url1);
                break;
            case R.id.service_cancel:
                downloadBinder.cancelDownload(url1);
                break;
            case R.id.service_restart:
                downloadBinder.restartDownload(url1);
                break;
            default:
                break;
        }
    }
}
