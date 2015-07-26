package zhou.app.jfbs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zzhoujay on 2015/7/26 0026.
 */
public class User implements Serializable, Parcelable {

    public String id;
    @SerializedName("original_url")
    public String originalUrl;
    @SerializedName("nickname")
    public String nickName;
    public String tab;
    @SerializedName("open_id")
    public String openId;
    @SerializedName("in_time")
    public Date inTime;
    @SerializedName("thirdlogin_type")
    public String thirdloginType;
    public int score;
    @SerializedName("expire_time")
    public Date expireTime;
    public String url;
    public String token;
    public String mission;
    public String email;
    public String gender;
    public String signature;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.originalUrl);
        dest.writeString(this.nickName);
        dest.writeString(this.tab);
        dest.writeString(this.openId);
        dest.writeLong(inTime != null ? inTime.getTime() : -1);
        dest.writeString(this.thirdloginType);
        dest.writeInt(this.score);
        dest.writeLong(expireTime != null ? expireTime.getTime() : -1);
        dest.writeString(this.url);
        dest.writeString(this.token);
        dest.writeString(this.mission);
        dest.writeString(this.email);
        dest.writeString(this.gender);
        dest.writeString(this.signature);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.originalUrl = in.readString();
        this.nickName = in.readString();
        this.tab = in.readString();
        this.openId = in.readString();
        long tmpInTime = in.readLong();
        this.inTime = tmpInTime == -1 ? null : new Date(tmpInTime);
        this.thirdloginType = in.readString();
        this.score = in.readInt();
        long tmpExpireTime = in.readLong();
        this.expireTime = tmpExpireTime == -1 ? null : new Date(tmpExpireTime);
        this.url = in.readString();
        this.token = in.readString();
        this.mission = in.readString();
        this.email = in.readString();
        this.gender = in.readString();
        this.signature = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
