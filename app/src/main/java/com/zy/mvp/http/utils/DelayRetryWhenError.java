package com.zy.mvp.http.utils;

import com.zy.mvp.utils.Logger;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * @Description: Created by yong on 2019/3/28 14:56.
 */
public class DelayRetryWhenError implements Function<Observable<? extends Throwable>, Observable<?>> {

    private int mMaxRetryCount = 3;//retry次数
    private long mDelay = 1000;//延迟
    private long mIncreaseDelay = 1000;//叠加延迟

    public DelayRetryWhenError() {
    }

    public DelayRetryWhenError(int maxRetryCount) {
        mMaxRetryCount = maxRetryCount;
    }

    public DelayRetryWhenError(int maxRetryCount, long delay) {
        mMaxRetryCount = maxRetryCount;
        mDelay = delay;
    }

    public DelayRetryWhenError(int maxRetryCount, long delay, long increaseDelay) {
        mMaxRetryCount = maxRetryCount;
        mDelay = delay;
        mIncreaseDelay = increaseDelay;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable
                .zipWith(Observable.range(1, mMaxRetryCount + 1), new BiFunction<Throwable, Integer, ThrowableWrapper>() {
                    @Override
                    public ThrowableWrapper apply(Throwable throwable, Integer curRetryCount) {
                        return new ThrowableWrapper(throwable, curRetryCount);
                    }
                }).flatMap(new Function<ThrowableWrapper, Observable<?>>() {
                    @Override
                    public Observable<?> apply(ThrowableWrapper wrapper) {
                        //遭遇了IOException等时才重试网络请求，IllegalStateException，NullPointerException或者当你使用gson来解析json时还可能出现的JsonParseException等非I/O异常均不在重试的范围内。
                        if ((wrapper.throwable instanceof ConnectException
                                || wrapper.throwable instanceof SocketTimeoutException
                                || wrapper.throwable instanceof TimeoutException
                                || wrapper.throwable instanceof HttpException
                        )) {
                            //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                            if (wrapper.curRetryCount <= mMaxRetryCount) {
                                Logger.e("DelayRetryWhenError", "网络错误，重试次数:" + wrapper.curRetryCount);
                                long delayTime = mDelay + (wrapper.curRetryCount - 1) * mIncreaseDelay;    //使用二进制指数退避算法，每次都比上次长时间
                                return Observable.timer(delayTime, TimeUnit.MILLISECONDS, Schedulers.trampoline());
                            }
                        }
                        return Observable.error(wrapper.throwable);
                    }
                });
    }

    private class ThrowableWrapper {
        private int curRetryCount;//当前重试次数
        private Throwable throwable;//抛出的异常

        public ThrowableWrapper(Throwable throwable, int curRetryCount) {
            this.curRetryCount = curRetryCount;
            this.throwable = throwable;
        }
    }
}
