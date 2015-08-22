package zhou.app.jfbs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhou.appinterface.model.InterfaceModel;

/**
 * Created by zzhoujay on 2015/8/22 0022.
 * 获取回复内容的主体
 */
public class ReplyResult extends InterfaceModel implements Parcelable {

    public Topic topic;
    public Reply reply;
    public User user;

    @Override
    public String toString() {
        return "ReplyResult{" +
                "topic=" + topic +
                ", reply=" + reply +
                ", user=" + user +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.topic, 0);
        dest.writeParcelable(this.reply, 0);
        dest.writeParcelable(this.user, 0);
    }

    public ReplyResult() {
    }

    protected ReplyResult(Parcel in) {
        this.topic = in.readParcelable(Topic.class.getClassLoader());
        this.reply = in.readParcelable(Reply.class.getClassLoader());
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Parcelable.Creator<ReplyResult> CREATOR = new Parcelable.Creator<ReplyResult>() {
        public ReplyResult createFromParcel(Parcel source) {
            return new ReplyResult(source);
        }

        public ReplyResult[] newArray(int size) {
            return new ReplyResult[size];
        }
    };
}
