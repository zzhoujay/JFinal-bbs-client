package zhou.app.jfbs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.zhou.appinterface.model.InterfaceModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzhoujay on 2015/8/11 0011.
 * 分页返回的话题列表
 */
public class TopicPage extends InterfaceModel implements Parcelable {

    public boolean firstPage;
    public boolean lastPage;
    public int pageSize;
    public int pageNumber;
    public int totalRow;
    public int totalPage;
    public List<Topic> list;

    @Override
    public String toString() {
        return "TopicPage{" +
                "firstPage=" + firstPage +
                ", lastPage=" + lastPage +
                ", pageSize=" + pageSize +
                ", pageNumber=" + pageNumber +
                ", totalRow=" + totalRow +
                ", totalPage=" + totalPage +
                ", list=" + list +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(firstPage ? (byte) 1 : (byte) 0);
        dest.writeByte(lastPage ? (byte) 1 : (byte) 0);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.pageNumber);
        dest.writeInt(this.totalRow);
        dest.writeInt(this.totalPage);
        dest.writeTypedList(list);
    }

    public TopicPage() {
    }

    protected TopicPage(Parcel in) {
        this.firstPage = in.readByte() != 0;
        this.lastPage = in.readByte() != 0;
        this.pageSize = in.readInt();
        this.pageNumber = in.readInt();
        this.totalRow = in.readInt();
        this.totalPage = in.readInt();
        this.list = in.createTypedArrayList(Topic.CREATOR);
    }

    public static final Parcelable.Creator<TopicPage> CREATOR = new Parcelable.Creator<TopicPage>() {
        public TopicPage createFromParcel(Parcel source) {
            return new TopicPage(source);
        }

        public TopicPage[] newArray(int size) {
            return new TopicPage[size];
        }
    };
}
