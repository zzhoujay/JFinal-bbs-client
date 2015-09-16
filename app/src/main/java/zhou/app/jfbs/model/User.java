package zhou.app.jfbs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zhou.appinterface.model.InterfaceModel;

import java.util.Date;

/**
 * Created by zzhoujay on 2015/7/26 0026.
 * 用户
 */
public class User extends InterfaceModel implements  Parcelable {

    public String id;
    @SerializedName("nickname")
    public String nickName;
    @SerializedName("open_id")
    public String openId;
    @SerializedName("in_time")
    public Date inTime;
    @SerializedName("thirdlogin_type")
    public String thirdloginType;
    public int score;
    @SerializedName("expire_time")
    public Date expireTime;
    public String avatar;
    public String url;
    public String token;
    public String mission;
    public String email;
    public String gender;
    public String signature;


    public User(String id, String nickName, String openId, Date inTime, String thirdloginType, int score, Date expireTime, String avatar, String url, String token, String mission, String email, String gender, String signature) {
        this.id = id;
        this.nickName = nickName;
        this.openId = openId;
        this.inTime = inTime;
        this.thirdloginType = thirdloginType;
        this.score = score;
        this.expireTime = expireTime;
        this.avatar = avatar;
        this.url = url;
        this.token = token;
        this.mission = mission;
        this.email = email;
        this.gender = gender;
        this.signature = signature;
    }

    public User() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.nickName);
        dest.writeString(this.openId);
        dest.writeLong(inTime != null ? inTime.getTime() : -1);
        dest.writeString(this.thirdloginType);
        dest.writeInt(this.score);
        dest.writeLong(expireTime != null ? expireTime.getTime() : -1);
        dest.writeString(this.avatar);
        dest.writeString(this.url);
        dest.writeString(this.token);
        dest.writeString(this.mission);
        dest.writeString(this.email);
        dest.writeString(this.gender);
        dest.writeString(this.signature);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.nickName = in.readString();
        this.openId = in.readString();
        long tmpInTime = in.readLong();
        this.inTime = tmpInTime == -1 ? null : new Date(tmpInTime);
        this.thirdloginType = in.readString();
        this.score = in.readInt();
        long tmpExpireTime = in.readLong();
        this.expireTime = tmpExpireTime == -1 ? null : new Date(tmpExpireTime);
        this.avatar = in.readString();
        this.url = in.readString();
        this.token = in.readString();
        this.mission = in.readString();
        this.email = in.readString();
        this.gender = in.readString();
        this.signature = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", nickName='" + nickName + '\'' +
                ", openId='" + openId + '\'' +
                ", inTime=" + inTime +
                ", thirdloginType='" + thirdloginType + '\'' +
                ", score=" + score +
                ", expireTime=" + expireTime +
                ", avatar='" + avatar + '\'' +
                ", url='" + url + '\'' +
                ", token='" + token + '\'' +
                ", mission='" + mission + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
