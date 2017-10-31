package com.example.administrator.text1.ui.testRxJava;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.administrator.text1.R;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hzhm on 2017/2/27.
 */

public class TestRxJava2 extends Activity {

    private ImageView imagView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frg_main_account);
//        imagView = (ImageView) findViewById(img);
        testTransformation4();
    }


    /**
     * 4、Scheduler---调度器，相当于线程控制器，RxJava 通过它来指定每一段代码应该运行在什么样的线程。
     * Schedulers相关Api简介？？？
     * a、Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
     * b、Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
     * c、Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，
     * 区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。
     * 不要把计算工作放在 io() 中，可以避免创建不必要的线程。
     * d、Schedulers.computation()：计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。
     * 这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
     * e、AndroidSchedulers.mainThread()：，它指定的操作将在 Android 主线程运行。
     * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
     * observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
     */
    private void testScheduler() {
        Observable.just("1", "2", "3")
                .subscribeOn(Schedulers.io())//指定事件发生线程使用io线程
                .observeOn(AndroidSchedulers.mainThread())//指定事件消耗线程使用当前主线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("Tag", s);
                    }
                });
    }

    /**
     * 由图片id 取得图片并显示
     */
    private void testScheduler2() {
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getDrawable(R.drawable.kd);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())//加载图片将会发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())//而设置图片则被设定在了主线程
                .subscribe(new Observer<Drawable>() {

                    @Override
                    public void onNext(Drawable drawable) {
//                        imagView.setImageDrawable(drawable);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    /**
     * 5、变换：所谓变换，就是将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列
     * <p>
     * 5.1、map(): 事件对象的直接变换
     * Func1类：它和 Action1 非常相似，也是 RxJava 的一个接口，用于包装含有一个参数的方法。 Func1 和 Action 的区别在于， Func1 包装的是有返回值的方法。
     * 另外，和 ActionX 一样， FuncX 也有多个，用于不同参数个数的方法。FuncX 和 ActionX 的区别在 FuncX 包装的是有返回值的方法。
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void testTransformation() {
        int res = R.drawable.hm;
        Observable.just(res)
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer integer) {
                        return BitmapFactory.decodeResource(getResources(), integer);
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
//                        imagView.setImageBitmap(bitmap);
                    }
                });
    }

    private void testTransformation2() {
        Student[] students = {new Student("张三"), new Student("赵四"), new Student("王五"), new Student("程六")};
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("Student", s);
            }
        };
        Observable.from(students)
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        return student.getName();
                    }
                })
                .subscribe(subscriber);
    }

    private void testTransformation3() {
        List<Course> course1 = new ArrayList<>();
        course1.add(new Course("课一"));
        course1.add(new Course("课二"));
        course1.add(new Course("课三"));
        Student student1 = new Student("张三");
        student1.setCourses(course1);

        List<Course> course2 = new ArrayList<>();
        course2.add(new Course("课四"));
        course2.add(new Course("课五"));
        course2.add(new Course("课六"));
        Student student2 = new Student("赵四");
        student2.setCourses(course2);

        Student[] students = {student1, student2};
        Subscriber<Student> subscriber = new Subscriber<Student>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Student student) {
                List<Course> courses = student.getCourses();
                for (int i = 0; i < courses.size(); i++) {
                    Course course = courses.get(i);
                    Log.d("xxx", course.getCourseName());
                }
            }
        };
        Observable.from(students)
                .subscribe(subscriber);
    }

    /**
     * 5.2、flatMap(): flatMap() 和 map() 有一个相同点：它也是把传入的参数转化之后返回另一个对象。但需要注意，
     * 和 map() 不同的是: flatMap() 中返回的是个 Observable 对象，并且这个 Observable 对象并不是被直接发送到了 Subscriber 的回调方法中。 flatMap
     * flatMap() 的原理是这样的：
     * 1. 使用传入的事件对象创建一个 Observable 对象
     * 2. 并不发送这个 Observable, 而是将它激活，于是它开始发送事件
     * 3. 每一个创建出来的 Observable 发送的事件，都被汇入同一个 Observable ，而这个 Observable 负责将这些事件统一交给 Subscriber 的回调方法
     */
    private void testTransformation4() {
        List<Course> course1 = new ArrayList<>();
        course1.add(new Course("课一"));
        course1.add(new Course("课二"));
        course1.add(new Course("课三"));
        Student student1 = new Student("张三");
        student1.setCourses(course1);

        List<Course> course2 = new ArrayList<>();
        course2.add(new Course("课四"));
        course2.add(new Course("课五"));
        course2.add(new Course("课六"));
        Student student2 = new Student("赵四");
        student2.setCourses(course2);

        Student[] students = {student1, student2};
        Subscriber<Course> subscriber = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {
                Log.d("xxx", course.getCourseName());
            }
        };
        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCourses());
                    }
                })
                .subscribe(subscriber);
    }

    /**
     * 5.3、lift()相关实现原理：
     * 1.lift() 创建了一个 Observable 后，加上之前的原始 Observable，已经有两个 Observable 了；
     * 2.而同样地，新 Observable 里的新 OnSubscribe 加上之前的原始 Observable 中的原始 OnSubscribe，也就有了两个 OnSubscribe；
     * 3.当用户调用经过 lift() 后的 Observable 的 subscribe() 的时候，使用的是 lift() 所返回的新的 Observable ，于是它所触发的 onSubscribe.call(subscriber)，
     *   也是用的新 Observable 中的新 OnSubscribe，即在 lift() 中生成的那个 OnSubscribe；
     * 4.而这个新 OnSubscribe 的 call() 方法中的 onSubscribe ，就是指的原始 Observable 中的原始 OnSubscribe ，
     *   在这个 call() 方法里，新 OnSubscribe 利用 operator.call(subscriber) 生成了一个新的 Subscriber（Operator 就是在这里，
     *   通过自己的 call() 方法将新 Subscriber 和原始 Subscriber 进行关联，并插入自己的『变换』代码以实现变换），
     *   然后利用这个新 Subscriber 向原始 Observable 进行订阅。
     * 简而言之：在 Observable 执行了 lift(Operator) 方法之后，会返回一个新的 Observable，这个新的 Observable 会像一个代理一样，
     * 负责接收原始的 Observable 发出的事件，并在处理后发送给 Subscriber。
     */
    //---- 注意：这不是 lift() 的源码，而是将源码中与性能、兼容性、扩展性有关的代码剔除后的核心代码。
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
     * 5.4、compose：compose() 是针对 Observable 自身进行变换。例如：设在程序中有多个 Observable ，并且他们都需要应用一组相同的 lift() 变换。你可以这么写：
     */
    private void testTransformation5(){
        Observable.Transformer liftAll = new LiftAllTransformer();
        Observable observable1 = Observable.just("1");
        Observable observable2 = Observable.just("2");
        Observable observable3 = Observable.just("3");
        Observable observable4 = Observable.just("4");

        observable1.compose(liftAll).subscribe(new Action1() {
            @Override
            public void call(Object o) {

            }
        });
        observable2.compose(liftAll).subscribe(new Action1() {
            @Override
            public void call(Object o) {

            }
        });
        observable3.compose(liftAll).subscribe(new Action1() {
            @Override
            public void call(Object o) {

            }
        });
        observable4.compose(liftAll).subscribe(new Action1() {
            @Override
            public void call(Object o) {

            }
        });
    }

    //创建一个Transformer内部类
    class LiftAllTransformer implements Observable.Transformer<Integer,String>{

        @Override
        public Observable<String> call(Observable<Integer> integerObservable) {
            return integerObservable.lift(new Observable.Operator<String, Integer>() {//lift方法...
                @Override
                public Subscriber<? super Integer> call(Subscriber<? super String> subscriber) {
                    return new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Integer integer) {

                        }
                    };
                }
            });
        }
    }

    private void testTransformation6(){

    }
}
