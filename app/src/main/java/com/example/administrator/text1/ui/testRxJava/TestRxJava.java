package com.example.administrator.text1.ui.testRxJava;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.administrator.text1.R;
import com.seaway.android.common.toast.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by hzhm on 2017/2/24.
 * 功能描述：RxJava的相关API 介绍和原理简析
 * 1. 概念：扩展的观察者模式（注：RxJava 的异步实现，是通过一种扩展的观察者模式来实现的。）
 * a、何为观察者模式？？？
 * 观察者模式面向的需求是：A 对象（观察者）对 B 对象（被观察者）的某种变化高度敏感，需要在 B 变化的一瞬间做出反应。
 * 例如：Android 开发中一个比较典型的例子是点击监听器 OnClickListener 。对设置 OnClickListener 来说， View 是被观察者， OnClickListener 是观察者，
 * 二者通过 setOnClickListener() 方法达成订阅关系。订阅之后用户点击按钮的瞬间，Android Framework 就会将点击事件发送给已经注册的 OnClickListener 。
 * b、RxJava 的观察者模式？？？
 * RxJava 有四个基本概念：Observable (可观察者，即被观察者)、 Observer (观察者)、 subscribe (订阅)、事件。
 * Observable 和 Observer 通过 subscribe() 方法实现订阅关系，从而 Observable 可以在需要的时候发出事件来通知 Observer。
 * c、 与传统观察者模式不同？？？
 * 与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext() （相当于 onClick() / onEvent()）之外，还定义了两个特殊的事件：onCompleted() 和 onError()。
 * onCompleted(): 事件队列完结。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列。RxJava 规定，当不会再有新的 onNext() 发出时，需要触发 onCompleted() 方法作为标志。
 * onError(): 事件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出。
 * 在一个正确运行的事件序列中, onCompleted() 和 onError() 有且只有一个，并且是事件序列中的最后一个。
 * 需要注意的是，onCompleted() 和 onError() 二者也是互斥的，即在队列中调用了其中一个，就不应该再调用另一个。
 */

public class TestRxJava extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rxjava);
        imageView = (ImageView) findViewById(R.id.img);
    }

    /**
     * a. 打印字符串数组
     */
    private void test1() {
        String[] name = {"黄明", "朱封义", "王清"};
        Observable.from(name).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("name", s);
            }
        });
    }

    /**
     * b. 由id 取得图片并显示
     */
    private void test2() {
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getDrawable(R.drawable.kd);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {

            @Override
            public void onNext(Drawable drawable) {
                imageView.setImageDrawable(drawable);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.showToast(TestRxJava.this, "onError!");
            }
        });
    }

    /**
     * 2、RxJava异步的基本实现
     */
    private void testObserver() {
        //---1.1) 创建 Observer：Observer 即观察者，它决定事件触发的时候将有怎样的行为。Java 中的 Observer 接口的实现方式：
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };
        //---1.2)Subscriber：除了 Observer 接口之外，RxJava 还内置了一个实现了 Observer 的抽象类：Subscriber。
        // Subscriber 对 Observer 接口进行了一些扩展，但他们的基本使用方式是完全一样的：
        // 不仅基本使用方式一样，实质上，在 RxJava 的 subscribe 过程中，Observer 也总是会先被转换成一个 Subscriber 再使用。
        // -------所以如果你只想使用基本功能，选择 Observer 和 Subscriber 是完全一样的。它们的区别对于使用者来说主要有两点：

        // ---a、onStart(): 这是 Subscriber 增加的方法。它会在 subscribe 刚开始，而事件还未发送之前被调用，可以用于做一些准备工作，例如数据的清零或重置。
        // 这是一个可选方法，默认情况下它的实现为空。需要注意的是，如果对准备工作的线程有要求（例如弹出一个显示进度的对话框，这必须在主线程执行），
        // onStart() 就不适用了，因为它总是在 subscribe 所发生的线程被调用，而不能指定线程。要在指定的线程来做准备工作，可以使用 doOnSubscribe() 方法，
        // 具体可以在后面的文中看到。
        // ---b、unsubscribe(): 这是 Subscriber 所实现的另一个接口 Subscription 的方法，用于取消订阅。在这个方法被调用后，Subscriber 将不再接收事件。
        // 一般在这个方法调用前，可以使用 isUnsubscribed() 先判断一下状态。 unsubscribe() 这个方法很重要，因为在 subscribe() 之后，
        // Observable 会持有 Subscriber 的引用，这个引用如果不能及时被释放，将有内存泄露的风险。
        // 所以最好保持一个原则：要在不再使用的时候尽快在合适的地方（例如 onPause() onStop() 等方法中）调用 unsubscribe() 来解除引用关系，以避免内存泄露的发生。
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        };
    }

    /**
     * 2) 创建 Observable： 即被观察者，它决定什么时候触发事件以及触发怎样的事件。
     */
    private void testObservable() {
        //------2.1) RxJava 使用 create() 方法来创建一个 Observable ，并为它定义事件触发规则：
        // 原理解析：这里传入了一个 OnSubscribe 对象作为参数。OnSubscribe 会被存储在返回的 Observable 对象中，它的作用相当于一个计划表，
        // 当 Observable 被订阅的时候，OnSubscribe 的 call() 方法会自动被调用，事件序列就会依照设定依次触发（对于上面的代码，就是观察者Subscriber
        // 将会被调用三次 onNext() 和一次 onCompleted()）。这样，由被观察者调用了观察者的回调方法，就实现了由被观察者向观察者的事件传递，即观察者模式。
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onNext("3");
                subscriber.onCompleted();
            }
        });

        //------2.2) RxJava 还提供了一些方法用来快捷创建事件队列：just、from....
        //(注：下面 just(T...) 的例子和 from(T[]) 的例子，都和之前的 create(OnSubscribe) 的例子是等价的。)
        Observable observable2 = Observable.just("1", "2", "3");

        String[] names = {"1", "2", "3"};
        Observable observable3 = Observable.from(names);
    }

    /**
     * 3) Subscribe (订阅)：创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了。
     */
    private void testSubscribe() {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onNext("3");
                subscriber.onCompleted();
            }
        });

        Observer observer = new Observer() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {

            }
        };
        observable.subscribe(observer);
    }

    // ------3.1)、subscribe()订阅方法的核心代码，可以看到，subscriber() 做了3件事：
    // 1、调用 Subscriber.onStart() 。这个方法在前面已经介绍过，是一个可选的准备方法。
    // 2、调用 Observable 中的 OnSubscribe.call(Subscriber) 。在这里，事件发送的逻辑开始运行。从这也可以看出，在 RxJava 中，
    //    Observable 并不是在创建的时候就立即开始发送事件，而是在它被订阅的时候，即当 subscribe() 方法执行的时候。
    // 3、将传入的 Subscriber 作为 Subscription 返回。这是为了方便 unsubscribe().
    public Subscription subscribe(Subscriber subscriber) {
        subscriber.onStart();
//        onSubscribe.call(subscriber);
        return subscriber;
    }

    /**
     * ------3.2)、除了 subscribe(Observer) 和 subscribe(Subscriber) ，subscribe() 还支持不完整定义的回调
     * <p>
     * Action0：是 RxJava 的一个接口，它只有一个方法 call()，这个方法是无参无返回值的；由于 onCompleted() 方法也是无参无返回值的，
     * 因此 Action0 可以被当成一个包装对象，将 onCompleted() 的内容打包起来将自己作为一个参数传入 subscribe() 以实现不完整定义的回调。
     * 这样其实也可以看做将 onCompleted() 方法作为参数传进了 subscribe()，相当于其他某些语言中的『闭包』。
     * <p>
     * Action1：也是一个接口，它同样只有一个方法 call(T param)，这个方法也无返回值，但有一个参数；
     * 与 Action0 同理，由于 onNext(T obj) 和 onError(Throwable error) 也是单参数无返回值的，
     * 因此 Action1 可以将 onNext(obj) 和 onError(error) 打包起来传入 subscribe() 以实现不完整定义的回调。
     */
    private void testActionX() {
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {

            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        };

        Action0 onCompleteAction = new Action0() {
            @Override
            public void call() {

            }
        };

        Observable observable = Observable.just("", "", "");
        observable.subscribe(onNextAction);
        observable.subscribe(onNextAction, onErrorAction);
        observable.subscribe(onNextAction, onErrorAction, onCompleteAction);
    }
}