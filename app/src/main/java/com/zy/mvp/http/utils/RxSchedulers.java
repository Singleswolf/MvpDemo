package com.zy.mvp.http.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxSchedulers {
    /**
     * UI线程.
     */
    public static final Scheduler MAIN = AndroidSchedulers.mainThread();

    /**
     * 用于执行网络请求.
     */
    public static final Scheduler NETWORKING = Schedulers.from(
            newFixedThreadPoolExecutor(4));

    /**
     * 用于执行异步任务, 例如读写缓存, 读写pref, 额外的API请求等.
     */
    public static final Scheduler ASYNC = Schedulers.from(new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L,
            TimeUnit.SECONDS, new SynchronousQueue<Runnable>()));

    public static ThreadPoolExecutor newFixedThreadPoolExecutor(int size) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(size, size, 1, TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>());
        executor.allowCoreThreadTimeOut(true);
        return executor;
    }

    public static <T> ObservableTransformer<T, T> compose() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}