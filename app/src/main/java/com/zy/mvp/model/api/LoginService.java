package com.zy.mvp.model.api;

import com.zy.mvp.http.BaseResponse;
import com.zy.mvp.model.entity.User;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @Description: Created by yong on 2019/3/26 20:24.
 */
public interface LoginService {
    //    https://www.apiopen.top/login?key=00d91e8e0cca2b76f515926a36db68f5&phone=13594347817&passwd=123456
    @GET("login")
    Observable<BaseResponse<User>> login(@Query("phone") String account, @Query("passwd") String passwd);
}
