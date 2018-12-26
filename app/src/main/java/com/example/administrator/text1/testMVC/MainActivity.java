package com.example.administrator.text1.testMVC;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.sina.weibo.sdk.component.view.AppProgressDialog;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author HuangMing on 2018/11/6.
 *         MVC模式项目分析：
 *         activity作为一个controller，里面的逻辑是监听用户点击按钮并作出相应的操作。比如针对get按钮，做的工作就是调用GithubApi的方法去获取数据。
 *         GithubApi，Contributor等类则表示MVC中的model层，里面是数据和一些具体的逻辑操作。说完了流程再来看看问题，还记得我们前面说的吗，
 *         MVC在Android上的应用，一个具体的问题就是activity的责任过重，既是controller又是view。这里是怎么体现的呢？
 *         看了代码大家发现其中有一个progressDialog，在加载数据的时候显示，加载完了以后取消，逻辑其实是view层的逻辑，但是这个我们没办法写到xml里面啊，
 *         包括TextView.setTextView()，这个也一样。我们只能把这些逻辑写到activity中，这就造成了activity的臃肿，这个例子可能还好，如果是一个复杂的页面呢？大家自己想象一下。
 */

public class MainActivity extends AppCompatActivity {

    private AppProgressDialog appProgressDialog;
    private TextView topContributor;
    /**
     * model层级
     */
    private Contributor contributor = new Contributor();

    /**
     * model层级
     */
    private Subscriber<Contributor> contributorSubscriber = new Subscriber<Contributor>() {
        @Override
        public void onCompleted() {
            showDialog();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Contributor contributor) {
            MainActivity.this.contributor = contributor;
            topContributor.setText(contributor.login);
            dismissDialog();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mvc_main);

        topContributor = (TextView) findViewById(R.id.top_contributor);
    }

    /**
     * controller层级
     *
     * @param view
     */
    public void get(View view) {
        getTopContributor("square", "retrofit");
    }

    /**
     * controller层级
     *
     * @param view
     */
    public void change(View view) {
        contributor.login = "";
        //View层级
        topContributor.setText(contributor.login);
    }

    public void getTopContributor(String owner, String repo) {
        //GitHubApi也是Model层级，下面相关方法的调用就算是涉及到与Model层级相关的逻辑操作了
//        GitHubApi.getContributors(owner, repo)
//                .take(1)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.newThread())
//                .map(new Func1<List<Contributor>, Contributor>() {
//
//                    @Override
//                    public Contributor call(List<Contributor> contributors) {
//                        return contributors.get(0);
//                    }
//                })
//                .subscribe(contributorSubscriber);
    }

    /**
     * View层级
     */
    public void showDialog() {
        if (appProgressDialog == null) {
            appProgressDialog = new AppProgressDialog(this);
        }
        appProgressDialog.setMessage("正在加载...");
    }

    /**
     * View层级
     */
    public void dismissDialog() {
        if (appProgressDialog == null) {
            appProgressDialog = new AppProgressDialog(this);
        }
        appProgressDialog.dismiss();
    }
}
