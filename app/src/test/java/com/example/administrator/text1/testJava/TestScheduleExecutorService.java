package com.example.administrator.text1.testJava;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by hzhm on 2017/1/19.
 *
 * 功能描述：测试ScheduledExecutorService线程池
 */

public class TestScheduleExecutorService {

    @Test
    public void hellow(){
        //实例化线程池服务对象，其容量为2个
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Taskrepeating");
            }
        };
        //开启一个线程池服务1：每隔1s执行当前task1任务...
        final ScheduledFuture future1 = service.scheduleWithFixedDelay(task1,0,5, TimeUnit.SECONDS);
        //开启另外一个线程池服务2：延时10s取消线程池服务1，并返回当前的String“taskcancelled”
        ScheduledFuture future2 = service.schedule(new Callable() {
            @Override
            public Object call() throws Exception {
                future1.cancel(true);
                return "taskcancelled";
            }
        },10,TimeUnit.SECONDS);
        try {
            System.out.println(future2.get());
            //停止所有线程池服务...
            service.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
