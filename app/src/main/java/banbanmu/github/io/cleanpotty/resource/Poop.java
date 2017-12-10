package banbanmu.github.io.cleanpotty.resource;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import banbanmu.github.io.cleanpotty.gson.GsonUtil;

/**
 * Created by min on 2017. 9. 16..
 */

public class Poop implements Parcelable {

    @SerializedName("_id")
    private String id;

    private String user;

    private int timeSpent;

    private String type;

    private Potty potty;

    private Date time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(int timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Potty getPotty() {
        return potty;
    }

    public void setPotty(Potty potty) {
        this.potty = potty;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public static final Parcelable.Creator<Poop> CREATOR = new Parcelable.Creator<Poop>() {
        @Override
        public Poop createFromParcel(Parcel parcel) {
            return new Poop(parcel);
        }

        @Override
        public Poop[] newArray(int i) {
            return new Poop[i];
        }
    };

    public Poop() {}

    public Poop(Parcel in) {
        read(GsonUtil.gson.fromJson(in.readString(), Poop.class));
    }

    public void read(Poop poop) {
        id = poop.getId();
        user = poop.getUser();
        timeSpent = poop.getTimeSpent();
        type = poop.getType();
        potty = poop.getPotty();
        time = poop.getTime();
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
