package fussen.yu.news.utils.network;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Fussen on 2016/12/1.
 */

public interface NetService {

    @POST()
    @FormUrlEncoded
    Observable<ResponseBody> postRequest(@Url() String url, @FieldMap Map<String, String> params);

    @POST()
    Observable<ResponseBody> postRequest(@Url() String url);

    /**
     * 上传表单
     * @param url
     * @param params
     * @return
     */
    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> postForm(@Url() String url, @FieldMap Map<String, Object> params);

    /**
     * 上传
     * @param url
     * @param object
     * @return
     */
    @POST("{url}")
    Observable<ResponseBody> executePostBody(@Path("url") String url, @Body Object object);

    /**
     * 上传 json 数据
     * @param url
     * @param jsonBody
     * @return
     */
    @POST()
    Observable<ResponseBody> postJson(@Url() String url, @Body RequestBody jsonBody);

    /**
     * 多部件上传
     * @param fileUrl
     * @param filePart
     * @return
     */
    @Multipart
    @POST
    Observable<ResponseBody> uploadFlie(@Url String fileUrl, @Part MultipartBody.Part filePart);

    /**
     * 上传多个文件
     * @param url
     * @param maps
     * @return
     */
    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(@Url() String url, @PartMap() Map<String, RequestBody> maps);

    /**
     * 下载，需要使用注解 @Streaming
     * @param fileUrl
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
