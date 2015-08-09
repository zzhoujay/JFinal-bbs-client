package zhou.app.jfbs.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by zzhoujay on 2015/7/26 0026.
 */
public class Section implements Serializable, Parcelable {

    public int id;
    @SerializedName("display_name")
    public int index;
    public String tab;
    public String name;
    @SerializedName("show_status")
    public int state;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.index);
        dest.writeString(this.tab);
        dest.writeString(this.name);
        dest.writeInt(this.state);
    }

    public Section() {
    }

    protected Section(Parcel in) {
        this.id = in.readInt();
        this.index = in.readInt();
        this.tab = in.readString();
        this.name = in.readString();
        this.state = in.readInt();
    }

    public static final Parcelable.Creator<Section> CREATOR = new Parcelable.Creator<Section>() {
        public Section createFromParcel(Parcel source) {
            return new Section(source);
        }

        public Section[] newArray(int size) {
            return new Section[size];
        }
    };
}