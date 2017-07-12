package fussen.yu.news.utils.network.exception;

/**
 * Created by Fussen on 2016/12/22.
 * 自定义联网异常（获取数据失败）
 */

public class NetException extends RuntimeException {
    public int code;
    public String message;

    public NetException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
