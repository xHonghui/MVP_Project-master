package fussen.yu.news.utils.network.service;

import java.util.Map;

import fussen.yu.news.modules.login.bean.UserInfo;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Fussen on 2016/12/29.
 * 登陆的 retrofit 服务类，可以使用这样一个分包的方法：
 * 1、不同的功能创建不同的 retrofit 的服务类
 * 2、通过一个统一的管理类（这里使用 NetworkUtils）来进行管理，通过这个管理类可以获得相应的 Service 的实例
 * 3、这样的方法称之为“模块化开发”
 */

public interface LoginService {
    @POST()
    @FormUrlEncoded
    Observable<UserInfo> toLogin(@Url() String url, @FieldMap Map<String, String> params);
}
