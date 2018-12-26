package com.example.administrator.text1.testRetrofit;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.text1.R;
import com.example.administrator.text1.ui.testRxJava.Course;
import com.example.administrator.text1.ui.testRxJava.Student;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author HuangMing on 2018/11/14.
 *         Des：
 *         1、RxJava 到底是什么
 *         一个在 Java VM 上使用可观测的序列来组成异步的、基于事件的程序的库，一个词形容：异步
 *         2、RxJava 好在哪
 *         一个词：简洁。
 *         3、概念：扩展的观察者模式
 *         RxJava 的异步实现，是通过一种扩展的观察者模式来实现的。
 *         4、RxJava 的观察者模式 有四个基本概念
 *         Observable (可观察者，即被观察者)、 Observer (观察者)、 subscribe (订阅)、事件。
 *         Observable 和 Observer 通过 subscribe() 方法实现订阅关系，从而 Observable 可以在需要的时候发出事件来通知 Observer。
 *         与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext() （相当于 onClick() / onEvent()）之外，还定义了两个特殊的事件：onCompleted() 和 onError()。
 *         A、onCompleted(): 事件队列完结。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列。RxJava 规定，当不会再有新的 onNext() 发出时，需要触发 onCompleted() 方法作为标
 *         B、onError(): 事件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出
 *         (注意：onCompleted() 和 onError() 二者也是互斥的，即在队列中调用了其中一个，就不应该再调用另一个)
 */

public class TestRxJava extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TestRxJava";

    private Button btnGet;
    private Button btnChange;
    private TextView tvContributor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mvc_main);

        btnGet = (Button) findViewById(R.id.get);
        btnChange = (Button) findViewById(R.id.change);
        tvContributor = (TextView) findViewById(R.id.top_contributor);

        btnGet.setOnClickListener(this);
        btnChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get:
                test1();
                break;
            case R.id.change:

                break;
            default:
                break;
        }
    }

    /**
     * 创建Observer
     * Observer 即观察者，它决定事件触发的时候将有怎样的行为。 RxJava 中的 Observer 接口的实现方式
     */
    private void createObserver() {
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
    }

    /**
     * Subscriber：
     * RxJava 还内置了一个实现了 Observer 的抽象类。 Subscriber 对 Observer 接口进行了一些扩展，但他们的基本使用方式是完全一样的
     * 不仅基本使用方式一样，实质上，在 RxJava 的 subscribe 过程中，Observer 也总是会先被转换成一个 Subscriber 再使用
     * 1、onStart(): 这是 Subscriber 增加的方法。它会在 subscribe 刚开始，而事件还未发送之前被调用，可以用于做一些准备工作，
     * 例如数据的清零或重置。这是一个可选方法，默认情况下它的实现为空。需要注意的是，如果对准备工作的线程有要求（例如弹出一个显示进度的对话框，这必须在主线程执行），
     * onStart() 就不适用了，因为它总是在 subscribe 所发生的线程被调用，而不能指定线程。要在指定的线程来做准备工作，可以使用 doOnSubscribe() 方法，具体可以在后面的文中看到。
     * 2、unsubscribe(): 这是 Subscriber 所实现的另一个接口 Subscription 的方法，用于取消订阅。
     * 在这个方法被调用后，Subscriber 将不再接收事件。一般在这个方法调用前，可以使用 isUnsubscribed() 先判断一下状态。
     * unsubscribe() 这个方法很重要，因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，这个引用如果不能及时被释放，
     * 将有内存泄露的风险。所以最好保持一个原则：要在不再使用的时候尽快在合适的地方（例如 onPause() onStop() 等方法中）调用 unsubscribe() 来解除引用关系，以避免内存泄露的发生。
     */
    private void createSubscriber() {
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onStart() {
                super.onStart();
            }

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
        subscriber.unsubscribe();
    }

    /**
     * 创建 Observable
     * Observable 即被观察者，它决定什么时候触发事件以及触发怎样的事件。 RxJava 使用 create() 方法来创建一个 Observable ，并为它定义事件触发规则：
     * 这里传入了一个 OnSubscribe 对象作为参数。OnSubscribe 会被存储在返回的 Observable 对象中，它的作用相当于一个计划表，
     * 当 Observable 被订阅的时候，OnSubscribe 的 call() 方法会自动被调用，事件序列就会依照设定依次触发（对于上面的代码，就是观察者Subscriber
     * 将会被调用三次 onNext() 和一次 onCompleted()）。这样，由被观察者调用了观察者的回调方法，就实现了由被观察者向观察者的事件传递，即观察者模式。
     * create()： 方法是 RxJava 最基本的创造事件序列的方法。基于这个方法， RxJava 还提供了一些方法用来快捷创建事件队列
     */
    private void createObservable() {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hellow!");
                subscriber.onNext("Hi!");
                subscriber.onCompleted();
            }
        });
    }

    /**
     * just(T...): 将传入的参数依次发送出来。
     */
    private void createObservableByJust() {
        Observable observable = Observable.just("", "", "");
        //将会依次调用：
        // onNext("Hello");
        // onNext("Hi");
        // onNext("Aloha");
        // onCompleted();
    }

    /**
     * from(T[])：from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
     */
    private void createObservableByFrom() {
        String[] worlds = {"", "", ""};
        Observable observable = Observable.from(worlds);
    }

    /**
     * Subscribe (订阅)
     * 创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了。代码形式很简单：
     */
    private void createSubscribe() {
//        observable.subscribe(observer);
//// 或者：
//        observable.subscribe(subscriber);
    }

    /**
     * 注意：这不是 subscribe() 的源码，而是将源码中与性能、兼容性、扩展性有关的代码剔除后的核心代码。
     * 1、调用 Subscriber.onStart() 。这个方法在前面已经介绍过，是一个可选的准备方法。
     * 2、调用 Observable 中的 OnSubscribe.call(Subscriber) 。在这里，事件发送的逻辑开始运行。从这也可以看出，在 RxJava 中， Observable 并不是在创建的时候就立即开始发送事件，而是在它被订阅的时候，即当 subscribe() 方法执行的时候。
     * 3、将传入的 Subscriber 作为 Subscription 返回。这是为了方便 unsubscribe().
     */
    public Subscription subscribe(Subscriber subscriber) {
        subscriber.onStart();
//        onSubscribe.call(subscriber);
        return subscriber;
    }

    /**
     * 除了 subscribe(Observer) 和 subscribe(Subscriber) ，subscribe() 还支持不完整定义的回调，RxJava 会自动根据定义创建出 Subscriber 。
     */
    private void createAction() {
        Action0 onCompletedAction = new Action0() {
            @Override
            public void call() {

            }
        };
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
        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
//        observable.subscribe(onNextAction);
        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
//        observable.subscribe(onNextAction, onErrorAction);
        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
//        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }

    /**
     * 使用RxJava实现打印字符串数组
     * 在 RxJava 的默认规则中，事件的发出和消费都是在同一个线程的。也就是说，如果只用上面的方法，
     * 实现出来的只是一个同步的观察者模式。观察者模式本身的目的就是『后台处理，前台回调』的异步机制，因此异步对于 RxJava 是至关重要的
     */
    private void test1() {
        final String[] names = {"AAA", "BBB", "CCC"};
        Observable.from(names).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, s);
                tvContributor.setText(s);
            }
        });
    }

    private void test2() {
        final int drawableRes = 0;
        ImageView imageView = new ImageView(this);
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        }).subscribe(new Observer<Drawable>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Drawable drawable) {

            }
        });
    }

    /**
     * 线程控制 —— Scheduler
     * 在RxJava 中，Scheduler ——调度器，相当于线程控制器，RxJava 通过它来指定每一段代码应该运行在什么样的线程
     * RxJava 已经内置了几个 Scheduler ，它们已经适合大多数的使用场景：
     * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
     * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
     * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
     * Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
     * Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
     * * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
     * * observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
     */
    private void testScheduler() {
        Observable.just("1", "1", "1")
                .subscribeOn(Schedulers.io())// 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {

                    }
                });
    }

    /**
     * 变换---map
     * Func1 的类。它和 Action1 非常相似，也是 RxJava 的一个接口，用于包装含有一个参数的方法。 Func1 和 Action 的区别在于， Func1 包装的是有返回值的方法
     * map() 方法将参数中的 String 对象转换成一个 Bitmap 对象后返回，而在经过 map() 方法后，
     * 事件的参数类型也由 String 转为了 Bitmap。这种直接变换对象并返回的，是最常见的也最容易理解的变换
     */
    private void testMap() {
        Observable.just("")// 输入类型 String
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {// 参数类型 String
                        return null;// 返回类型 Bitmap
                    }
                })
                .subscribe(new Action1<Bitmap>() {// 参数类型 Bitmap
                    @Override
                    public void call(Bitmap bitmap) {

                    }
                });
    }

    /**
     * 变换---flatMap
     * flatMap() 和 map() 有一个相同点：它也是把传入的参数转化之后返回另一个对象。但需要注意，和 map() 不同的是， flatMap() 中返回的是个 Observable 对象
     * flatMap() 的原理是这样的：1. 使用传入的事件对象创建一个 Observable 对象；
     * 2. 并不发送这个 Observable, 而是将它激活，于是它开始发送事件；
     * 3. 每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，而这个 Observable 负责将这些事件统一交给 Subscriber 的回调方法
     */
    private void testFlatMap() {
        Student[] students = {};
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return null;
                    }
                }).subscribe(new Action1<Course>() {
            @Override
            public void call(Course course) {

            }
        });
    }

    /**
     * flatMap---实现嵌套请求
     * 传统的嵌套请求需要使用嵌套的 Callback 来实现。而通过 flatMap() ，可以把嵌套的请求写在一条链中，从而保持程序逻辑的清晰。
     */
    private void testFlatMap2() {
//        networkClient.token()
//                .flatMap(new Func1<String, Observable<Messages>>() {
//                    @Override
//                    public Observable<Messages> call(String token) {
//                        // 返回 Observable<Messages>
//                        return networkClient.messages();
//                    }
//                })
//                .subscribe(new Action1<Messages>() {
//                    @Override
//                    public void call(Messages messages) {
//                        // 处理显示消息列表
//                        showMessages(messages);
//                    }
//                });
    }

    /**
     * 变换的原理：lift()
     * (map、flatMap)这些变换虽然功能各有不同，但实质上都是针对事件序列的处理和再发送。
     * 而在 RxJava 的内部，它们是基于同一个基础的变换方法： lift(Operator)。首先看一下 lift() 的内部实现（仅核心代码）
     * lift()原理：在 Observable 执行了 lift(Operator) 方法之后，会返回一个新的 Observable，这个新的 Observable 会像一个代理一样，
     * 负责接收原始的 Observable 发出的事件，并在处理后发送给 Subscriber。
     */
    // 注意：这不是 lift() 的源码，而是将源码中与性能、兼容性、扩展性有关的代码剔除后的核心代码。
    // 如果需要看源码，可以去 RxJava 的 GitHub 仓库下载。
//    public <R> Observable<R> lift(Operator<? extends R, ? super T> operator) {
//        return Observable.create(new OnSubscribe<R>() {
//            @Override
//            public void call(Subscriber subscriber) {
//                Subscriber newSubscriber = operator.call(subscriber);
//                newSubscriber.onStart();
//                onSubscribe.call(newSubscriber);
//            }
//        });
//    }

    /**
     * RxJava 都不建议开发者自定义 Operator 来直接使用 lift()，而是建议尽量使用已有的 lift() 包装方法（如 map() flatMap() 等）
     * 进行组合来实现需求，因为直接使用 lift() 非常容易发生一些难以发现的错误。
     */
    private void testLift() {
//        observable.lift(new Observable.Operator<String, Integer>() {
//            @Override
//            public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
//                // 将事件序列中的 Integer 对象转换为 String 对象
//                return new Subscriber<Integer>() {
//                    @Override
//                    public void onNext(Integer integer) {
//                        subscriber.onNext("" + integer);
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                        subscriber.onCompleted();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        subscriber.onError(e);
//                    }
//                };
//            }
//        });

    }

    /**
     * 除了灵活的变换，RxJava 另一个牛逼的地方，就是线程的自由控制。
     * observeOn() 的多次调用，程序实现了线程的多次切换。
     * 不过，不同于 observeOn() ， subscribeOn() 的位置放在哪里都可以，但它是只能调用一次的。
     */
    private void test() {
//        Observable.just(1, 2, 3, 4) // IO 线程，由 subscribeOn() 指定
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .map(mapOperator) // 新线程，由 observeOn() 指定
//                .observeOn(Schedulers.io())
//                .map(mapOperator2) // IO 线程，由 observeOn() 指定
//                .observeOn(AndroidSchedulers.mainThread)
//                .subscribe(subscriber);  // Android 主线程，由 observeOn() 指定
    }

    /**
     *  Observable.doOnSubscribe()：
     *  它和 Subscriber.onStart() 同样是在 subscribe() 调用后而且在事件发送前执行，但区别在于它可以指定线程。默认情况下，
     *  doOnSubscribe() 执行在 subscribe() 发生的线程；而如果在 doOnSubscribe() 之后有 subscribeOn() 的话，它将执行在离它最近的 subscribeOn() 所指定的线程
     */
    private void testDoOnSubscribe() {
//        Observable.create(onSubscribe)
//                .subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
////                        progressBar.setVisibility(View.VISIBLE); // 需要在主线程执行
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())// 指定主线程
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
    }
}
