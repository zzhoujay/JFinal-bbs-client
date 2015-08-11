package zhou.app.jfbs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zhou.appinterface.model.InterfaceModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zzhoujay on 2015/7/26 0026.
 */
public class Topic extends InterfaceModel implements Parcelable {

    public String id;
    public String title;
    public String content;
    @SerializedName("s_id")
    public int sId;
    @SerializedName("author_id")
    public String authorId;
    public int view;
    @SerializedName("modify_time")
    public Date modifyTime;
    public String signature;
    public int top;
    @SerializedName("show_status")
    public int status;
    @SerializedName("in_time")
    public Date inTime;
    @SerializedName("original_url")
    public String originalUrl;
    @SerializedName("reply_count")
    public int replyCount;
    @SerializedName("nikename")
    public String nikeName;
    public int score;
    public String tab;
    @SerializedName("section_name")
    public String sectionName;
    public int good;
    public String avatar;
    public int reposted;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeInt(this.sId);
        dest.writeString(this.authorId);
        dest.writeInt(this.view);
        dest.writeLong(modifyTime != null ? modifyTime.getTime() : -1);
        dest.writeString(this.signature);
        dest.writeInt(this.top);
        dest.writeInt(this.status);
        dest.writeLong(inTime != null ? inTime.getTime() : -1);
        dest.writeString(this.originalUrl);
        dest.writeInt(this.replyCount);
        dest.writeString(this.nikeName);
        dest.writeInt(this.score);
        dest.writeString(this.tab);
        dest.writeString(this.sectionName);
        dest.writeInt(this.good);
        dest.writeString(this.avatar);
        dest.writeInt(this.reposted);
    }

    public Topic() {
    }

    protected Topic(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.content = in.readString();
        this.sId = in.readInt();
        this.authorId = in.readString();
        this.view = in.readInt();
        long tmpModifyTime = in.readLong();
        this.modifyTime = tmpModifyTime == -1 ? null : new Date(tmpModifyTime);
        this.signature = in.readString();
        this.top = in.readInt();
        this.status = in.readInt();
        long tmpInTime = in.readLong();
        this.inTime = tmpInTime == -1 ? null : new Date(tmpInTime);
        this.originalUrl = in.readString();
        this.replyCount = in.readInt();
        this.nikeName = in.readString();
        this.score = in.readInt();
        this.tab = in.readString();
        this.sectionName = in.readString();
        this.good = in.readInt();
        this.avatar = in.readString();
        this.reposted = in.readInt();
    }

    public static final Parcelable.Creator<Topic> CREATOR = new Parcelable.Creator<Topic>() {
        public Topic createFromParcel(Parcel source) {
            return new Topic(source);
        }

        public Topic[] newArray(int size) {
            return new Topic[size];
        }
    };
}
