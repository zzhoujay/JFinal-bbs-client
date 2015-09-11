package zhou.app.jfbs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhou.appinterface.model.InterfaceModel;

import java.util.List;

/**
 * Created by zzhoujay on 2015/8/22 0022.
 * 话题及其回复
 */
public class TopicWithReply extends InterfaceModel implements Parcelable {

    public Topic topic;
    public List<Reply> replies;

    public TopicWithReply(Topic topic, List<Reply> replies) {
        this.topic = topic;
        this.replies = replies;
    }

    @Override
    public boolean isEmpty() {
        return topic==null&&(replies==null||replies.isEmpty());
    }

    @Override
    public String toString() {
        return "TopicWithReply{" +
                "topic=" + topic +
                ", replies=" + replies +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.topic, 0);
        dest.writeTypedList(replies);
    }

    public TopicWithReply() {
    }

    protected TopicWithReply(Parcel in) {
        this.topic = in.readParcelable(Topic.class.getClassLoader());
        this.replies = in.createTypedArrayList(Reply.CREATOR);
    }

    public static final Parcelable.Creator<TopicWithReply> CREATOR = new Parcelable.Creator<TopicWithReply>() {
        public TopicWithReply createFromParcel(Parcel source) {
            return new TopicWithReply(source);
        }

        public TopicWithReply[] newArray(int size) {
            return new TopicWithReply[size];
        }
    };
}
