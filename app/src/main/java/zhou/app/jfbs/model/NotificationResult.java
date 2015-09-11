package zhou.app.jfbs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhou.appinterface.model.InterfaceModel;

import java.util.List;

/**
 * Created by zzhoujay on 2015/8/22 0022.
 * 获取通知的主体
 */
public class NotificationResult extends InterfaceModel implements  Parcelable {

    public List<Notification> oldMessages;
    public List<Notification> notifications;

    public NotificationResult(List<Notification> oldMessages, List<Notification> notifications) {
        this.oldMessages = oldMessages;
        this.notifications = notifications;
    }

    @Override
    public boolean isEmpty() {
        return oldMessages == null || (oldMessages.isEmpty() && notifications == null || notifications.isEmpty());
    }

    @Override
    public String toString() {
        return "NotificationResult{" +
                "oldMessages=" + oldMessages +
                ", notifications=" + notifications +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(oldMessages);
        dest.writeTypedList(notifications);
    }

    public NotificationResult() {
    }

    protected NotificationResult(Parcel in) {
        this.oldMessages = in.createTypedArrayList(Notification.CREATOR);
        this.notifications = in.createTypedArrayList(Notification.CREATOR);
    }

    public static final Parcelable.Creator<NotificationResult> CREATOR = new Parcelable.Creator<NotificationResult>() {
        public NotificationResult createFromParcel(Parcel source) {
            return new NotificationResult(source);
        }

        public NotificationResult[] newArray(int size) {
            return new NotificationResult[size];
        }
    };
}
