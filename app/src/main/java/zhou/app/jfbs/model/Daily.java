package zhou.app.jfbs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhou.appinterface.model.InterfaceModel;

/**
 * Created by zzhoujay on 2015/8/22 0022.
 * 每日签到返回的内容
 */
public class Daily extends InterfaceModel implements  Parcelable {

    public int score;
    public int day;

    @Override
    public String toString() {
        return "Daily{" +
                "score=" + score +
                ", day=" + day +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.score);
        dest.writeInt(this.day);
    }

    public Daily() {
    }

    protected Daily(Parcel in) {
        this.score = in.readInt();
        this.day = in.readInt();
    }

    public static final Parcelable.Creator<Daily> CREATOR = new Parcelable.Creator<Daily>() {
        public Daily createFromParcel(Parcel source) {
            return new Daily(source);
        }

        public Daily[] newArray(int size) {
            return new Daily[size];
        }
    };
}
