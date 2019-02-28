package com.test.rxjava;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by MaxWang on 2019/2/15.
 * 项目名称：MyAndroid
 * 类描述  ：
 * 创建人  ：MaxWang
 * 创建时间：2019/2/15 16:53
 * 修改人  ：MaxWang
 * 修改时间：2019/2/15
 * 修改备注：
 */

public class RxJavaT {


    public void sechedule() {
        Observable.just(1)
                .map(Object::toString)
                .subscribeOn(Schedulers.io())//内容在io线程发出
                .observeOn(AndroidSchedulers.mainThread())//接收在android
                .subscribe(s -> {
                    Thread thread = Thread.currentThread();
                    System.out.println(thread.getName() + ":" + s);
                });
    }

    public void map() {
//        Observable.just(1)
//                .map(integer -> integer.toString()).subscribe();
//        Observable.just(1)
//                .map(Object::toString).subscribe();


        Observable.just(1)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer.toString();
                    }
                }).subscribe(String::toString);
    }

    public void t() {

//        Observable observable1 = Observable.just("Helow");
        //背压版
        Flowable flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
                                                @Override
                                                public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                                                    e.onNext(1);
                                                }
                                            }//需要指定背压策略
                , BackpressureStrategy.BUFFER);
        Subscriber subscriber = new Subscriber() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        //被观察者
        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onComplete();
            }
        });
        //观察者
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };


        observable.subscribe(observer);
    }


    public void onDefferent() {
                 /*RxJava 1.0 中 创建被观察者 */
//         Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("Hello");
//                subscriber.onNext("Hi");
//                subscriber.onNext("Aloha");
//                subscriber.onCompleted();
//            }
//        });

       /*  RxJava 2.0 中 创建被观察者 */
//          变化1：Observable.OnSubscribe接口名改成ObservableOnSubscribe
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            // 变化2：复写的call（Subscriber）改成 subscribe （ObservableEmitter）
//            // 注：参数也发生了变化，即Subscriber -> ObservableEmitter = 发射器
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                // 可发出三种类型的事件：next事件、complete事件&error事件
//                // 通过调用emitter.onNext(T value) 、onComplete()和onError(Throwable e)
//                e.onNext(1);
//                e.onNext(2);
//                e.onError(new Exception("发生错误了"));
//                e.onComplete();
//            }
//        });




/*        <-- RxJava 1.0 中 创建观察者（Observer） -->*/
// 方法1：采用 Observer 接口
//                Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onNext(String s) {
//                Log.d(tag, "Item: " + s);
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.d(tag, "Completed!");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(tag, "Error!");
//            }
//        };

// 方法2：采用 Subscriber 接口（实现了Observer接口的抽象类）
// 与Observer接口的区别：对 Observer接口进行了扩展：onStart()、unsubscribe()，但使用方式基本类似
//        Subscriber<String> subscriber = new Subscriber<String>() {
//            @Override
//            public void onNext(String s) {
//                Log.d(tag, "Item: " + s);
//            }
//
//            @Override
//            public void onCompleted() {
//                Log.d(tag, "Completed!");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(tag, "Error!");
//            }
//        };

/*<-- RxJava 2.0 中 创建观察者（Observer） -->*/
//        Observer<Integer> observer = new Observer<Integer>() {
//
//            // 变化1：增加回调方法onSubscribe()
//            // 作用：最先调用该方法，即适合做初始化工作
//            @Override
//            public void onSubscribe(Disposable d) {
//                // 传入的参数Disposable作用 类似于 Subsciption
//                // 即相当于订阅关系的开关，即可切断 观察者和被观察者的订阅关系
//                // 注：调用dispose() = 观察者无法接收事件，但被观察者还是会继续发送事件
//            }
//
//            @Override
//            public void onNext(Integer value) {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//
//            // 变化2：onCompleted（）改成 onComplete（）
//            @Override
//            public void onComplete() {
//            }
//        };
    }
}
