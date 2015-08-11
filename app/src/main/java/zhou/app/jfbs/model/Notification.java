package zhou.app.jfbs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.zhou.appinterface.model.InterfaceModel;

import java.util.Date;

/**
 * Created by zzhoujay on 2015/7/26 0026.
 */
public class Notification extends InterfaceModel implements Parcelable {

    public int id;
    public int read;
    @SerializedName("in_time")
    public Date inTime;
    @SerializedName("from_author_id")
    public String fromAuthorId;
    @SerializedName("nickname")
    public String nickName;
    public String massage;
    @SerializedName("author_id")
    public String authorId;
    @SerializedName("r_id")
    public String rId;
    public String title;
    @SerializedName("t_id")
    public String tId;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.read);
        dest.writeLong(inTime != null ? inTime.getTime() : -1);
        dest.writeString(this.fromAuthorId);
        dest.writeString(this.nickName);
        dest.writeString(this.massage);
        dest.writeString(this.authorId);
        dest.writeString(this.rId);
        dest.writeString(this.title);
        dest.writeString(this.tId);
    }

    public Notification() {
    }

    protected Notification(Parcel in) {
        this.id = in.readInt();
        this.read = in.readInt();
        long tmpInTime = in.readLong();
        this.inTime = tmpInTime == -1 ? null : new Date(tmpInTime);
        this.fromAuthorId = in.readString();
        this.nickName = in.readString();
        this.massage = in.readString();
        this.authorId = in.readString();
        this.rId = in.readString();
        this.title = in.readString();
        this.tId = in.readString();
    }

    public static final Parcelable.Creator<Notification> CREATOR = new Parcelable.Creator<Notification>() {
        public Notification createFromParcel(Parcel source) {
            return new Notification(source);
        }

        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
}
