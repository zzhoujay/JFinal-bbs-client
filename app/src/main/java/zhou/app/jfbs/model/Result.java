package zhou.app.jfbs.model;

import com.zhou.appinterface.net.InterfaceResult;

import java.net.SocketTimeoutException;

/**
 * Created by zzhoujay on 2015/8/11 0011.
 * 网络请求返回的结果
 */
public class Result<T> extends InterfaceResult {

    public static final Result TIME_OUT = new Result(-1, "网络连接超时");

    public static final Result UNKNOWN = new Result(-2, "未知错误");

    public int code;
    public String description;
    public T detail;

    @Override
    public boolean isSuccessful() {
        return code == 200;
    }

    @Override
    public InterfaceResult error(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            return TIME_OUT;
        }
        return UNKNOWN;
    }

    public boolean hasData() {
        return detail != null;
    }

    public Result(int code, String description, T detail) {
        this.code = code;
        this.description = description;
        this.detail = detail;
    }

    public Result(int code, String description) {
        this(code, description, null);
    }

    public Result() {
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", description='" + description + '\'' +
                ", detail=" + detail +
                '}';
    }


}
