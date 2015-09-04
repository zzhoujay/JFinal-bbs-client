package com.zhou.appinterface.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zzhoujay on 2015/8/28 0028.
 * 分页对象
 */
public class Pageable implements Parcelable, Serializable {

    public int pageNo;
    public int pageSize;

    private int r_n, r_s;

    public Pageable(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;

        r_n = pageNo;
        r_s = pageSize;
    }

    public Pageable() {
        this(1, 20);
    }

    public void next() {
        pageNo++;
    }

    public void prev() {
        pageNo--;
    }

    public void reset() {
        pageNo = r_n;
        pageSize = r_s;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.pageNo);
        dest.writeInt(this.pageSize);
    }

    protected Pageable(Parcel in) {
        this.pageNo = in.readInt();
        this.pageSize = in.readInt();
    }

    public static final Parcelable.Creator<Pageable> CREATOR = new Parcelable.Creator<Pageable>() {
        public Pageable createFromParcel(Parcel source) {
            return new Pageable(source);
        }

        public Pageable[] newArray(int size) {
            return new Pageable[size];
        }
    };

    @Override
    public String toString() {
        return "Pageable{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                '}';
    }
}
