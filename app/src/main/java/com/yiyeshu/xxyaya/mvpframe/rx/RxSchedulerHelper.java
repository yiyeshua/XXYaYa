package com.yiyeshu.xxyaya.mvpframe.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/12/31.
 * Rx线程调度帮助类
 */

public class RxSchedulerHelper {

    //线程调度，返回一个绑定了生产线程和消费线程的被观察者，其中被观察者执行在io线程，以后如果订阅了观察者，则观察者执行在主线程
    public static <T> Observable.Transformer<T, T> io_main() {

        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {

                return tObservable
                        // 生产线程
                        .subscribeOn(Schedulers.io())
                        // 消费线程
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
    //线程调度，返回一个绑定了生产线程和消费线程的被观察者，其中被观察者执行在newThread线程，以后如果订阅了观察者，则观察者执行在主线程
    public static <T> Observable.Transformer<T, T> newThread_main() {

        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {

                return tObservable
                        // 生产线程
                        .subscribeOn(Schedulers.newThread())
                        // 消费线程
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
