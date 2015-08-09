package com.zhou.appinterface.net;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zzhoujay on 2015/8/9 0009.
 * 网络请求的结果
 */
public class Result implements Serializable, Parcelable {

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_PERMISSIONS = 1;
    public static final int CODE_NOT_FOUND = 2;
    public static final int CODE_FAILURE = 3;
    public static final int CODE_PARAM_ERROR = 4;
    public static final int CODE_NETWORK_ERROR = -1;
    public static final int CODE_NETWORK_FAILURE = -2;

    public static final int CODE_UNKNOWN_ERROR = 10;

    public static final String MSG_NO_LOGIN = "请登录后操作";
    public static final String MSG_SUCCESS = "操作成功";
    public static final String MSG_NOT_FOUND = "没有找到";
    public static final String MSG_FAILURE = "操作失败";
    public static final String MSG_PARAM_ERROR = "参数错误";
    public static final String MSG_UNKNOWN_ERROR = "未知错误";
    public static final String MSG_NETWORK_FAILURE = "网络连接失败";

    public static final String MSG_NETWORK_ERROR = "网络未连接，请检查网络连接后重试";

    public static final Result SUCCESS = createWithMsg(CODE_SUCCESS, MSG_SUCCESS);
    public static final Result FAILURE = createWithMsg(CODE_FAILURE, MSG_FAILURE);
    public static final Result PERMISSIONS = createWithMsg(CODE_PERMISSIONS, MSG_NO_LOGIN);
    public static final Result NOT_FOUND = createWithMsg(CODE_NOT_FOUND, MSG_NOT_FOUND);
    public static final Result PARAM_ERROR = createWithMsg(CODE_PARAM_ERROR, MSG_PARAM_ERROR);
    public static final Result UNKNOWN = createWithMsg(CODE_UNKNOWN_ERROR, MSG_UNKNOWN_ERROR);

    public static final Result NETWORK_ERROR = createWithMsg(CODE_NETWORK_ERROR, MSG_NETWORK_ERROR);
    public static final Result NETWORK_FAILURE = createWithMsg(CODE_NETWORK_FAILURE, MSG_NETWORK_FAILURE);

    public int code;
    public String msg;
    public String body;

    public Result(int code, String msg, String body) {
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    public Result(int code) {
        this.code = code;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public boolean isSuccessful() {
        return code == CODE_SUCCESS;
    }

    public boolean isNoLogin() {
        return code == CODE_PERMISSIONS;
    }


    public static Result createWiteCode(int code) {
        return new Result(code);
    }

    public static Result createWithMsg(int code, String msg) {
        return new Result(code, msg);
    }

    public static Result createWithBody(int code, String msg, String body) {
        return new Result(code, msg, body);
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", body='" + body + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.msg);
        dest.writeString(this.body);
    }

    protected Result(Parcel in) {
        this.code = in.readInt();
        this.msg = in.readString();
        this.body = in.readString();
    }

    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
