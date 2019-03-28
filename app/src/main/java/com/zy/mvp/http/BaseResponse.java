package com.zy.mvp.http;

/**
 * @Description: Created by yong on 2019/3/26 19:31.
 */
public class BaseResponse<T> {
    public int code;
    public String msg;
    public T data;

    public boolean isSuccess() {
        return code == 200;
    }
}
