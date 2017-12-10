package banbanmu.github.io.cleanpotty.resource;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import banbanmu.github.io.cleanpotty.gson.GsonUtil;

/**
 * Created by min on 2017. 9. 17..
 */

public class Data implements Parcelable {

    private Date date;

    private int value;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public float getFloatDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return (float)(cal.get(Calendar.YEAR) * 10000.0 + cal.get(Calendar.MONTH) * 100.0 + cal.get(Calendar.DATE));
    }

    public float getFloatValue() {
        return (float)(value / 1000.0 / 60.0);
    }

    public static final Parcelable.Creator<Data> CREATOR = new Parcelable.Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel parcel) {
            return null;
        }

        @Override
        public Data[] newArray(int i) {
            return new Data[0];
        }
    };

    public Data() {}

    public Data(Parcel in) {
        read(GsonUtil.gson.fromJson(in.readString(), Data.class));
    }

    public void read(Data data) {
        date = data.getDate();
        value = data.getValue();
    }

    public String toJson() {
        return GsonUtil.gson.toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(toJson());
    }
}
