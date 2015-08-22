package zhou.app.jfbs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zhou.appinterface.model.InterfaceModel;

import java.util.Date;

/**
 * Created by zzhoujay on 2015/7/26 0026.
 * 回复
 */
public class Reply extends InterfaceModel implements  Parcelable {

    public String id;
    public String content;
    @SerializedName("quote_content")
    public String quoteContent;
    @SerializedName("in_time")
    public Date inTime;
    @SerializedName("t_id")
    public String tId;
    public String avatar;
    @SerializedName("quote_author_nickname")
    public String quoteAuthorNickName;
    @SerializedName("nickname")
    public String nickName;
    public String quote;
    @SerializedName("author_id")
    public String authorId;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.content);
        dest.writeString(this.quoteContent);
        dest.writeLong(inTime != null ? inTime.getTime() : -1);
        dest.writeString(this.tId);
        dest.writeString(this.avatar);
        dest.writeString(this.quoteAuthorNickName);
        dest.writeString(this.nickName);
        dest.writeString(this.quote);
        dest.writeString(this.authorId);
    }

    public Reply() {
    }

    protected Reply(Parcel in) {
        this.id = in.readString();
        this.content = in.readString();
        this.quoteContent = in.readString();
        long tmpInTime = in.readLong();
        this.inTime = tmpInTime == -1 ? null : new Date(tmpInTime);
        this.tId = in.readString();
        this.avatar = in.readString();
        this.quoteAuthorNickName = in.readString();
        this.nickName = in.readString();
        this.quote = in.readString();
        this.authorId = in.readString();
    }

    public static final Parcelable.Creator<Reply> CREATOR = new Parcelable.Creator<Reply>() {
        public Reply createFromParcel(Parcel source) {
            return new Reply(source);
        }

        public Reply[] newArray(int size) {
            return new Reply[size];
        }
    };

    @Override
    public String toString() {
        return "Reply{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", quoteContent='" + quoteContent + '\'' +
                ", inTime=" + inTime +
                ", tId='" + tId + '\'' +
                ", avatar='" + avatar + '\'' +
                ", quoteAuthorNickName='" + quoteAuthorNickName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", quote='" + quote + '\'' +
                ", authorId='" + authorId + '\'' +
                '}';
    }
}
