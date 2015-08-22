package zhou.app.jfbs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhou.appinterface.model.InterfaceModel;

import java.util.List;

/**
 * Created by zzhoujay on 2015/8/22 0022.
 * 获取用户信息的主体
 */
public class UserResult extends InterfaceModel implements Parcelable {

    public User user;
    public List<Topic> topics;
    public List<Topic> collects;


    @Override
    public String toString() {
        return "UserResult{" +
                "user=" + user +
                ", topics=" + topics +
                ", collects=" + collects +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.user, 0);
        dest.writeTypedList(topics);
        dest.writeTypedList(collects);
    }

    public UserResult() {
    }

    protected UserResult(Parcel in) {
        this.user = in.readParcelable(User.class.getClassLoader());
        this.topics = in.createTypedArrayList(Topic.CREATOR);
        this.collects = in.createTypedArrayList(Topic.CREATOR);
    }

    public static final Parcelable.Creator<UserResult> CREATOR = new Parcelable.Creator<UserResult>() {
        public UserResult createFromParcel(Parcel source) {
            return new UserResult(source);
        }

        public UserResult[] newArray(int size) {
            return new UserResult[size];
        }
    };
}
