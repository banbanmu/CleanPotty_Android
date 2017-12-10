package banbanmu.github.io.cleanpotty.resource;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import banbanmu.github.io.cleanpotty.gson.GsonUtil;

/**
 * Created by min on 2017. 9. 16..
 */

public class Potty implements Parcelable {

    @SerializedName("_id")
    private String id;

    private float lat;

    private float lng;

    private Date timeCleaned;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public Date getTimeCleaned() {
        return timeCleaned;
    }

    public void setTimeCleaned(Date timeCleaned) {
        this.timeCleaned = timeCleaned;
    }

    public static final Parcelable.Creator<Potty> CREATOR = new Parcelable.Creator<Potty>() {
        @Override
        public Potty createFromParcel(Parcel parcel) {
            return new Potty(parcel);
        }

        @Override
        public Potty[] newArray(int i) {
            return new Potty[i];
        }
    };

    public Potty() {}

    public Potty(Parcel in) {
        read(GsonUtil.gson.fromJson(in.readString(), Potty.class));
    }

    public void read(Potty potty) {
        id = potty.getId();
        lat = potty.getLat();
        lng = potty.getLng();
        timeCleaned = potty.getTimeCleaned();
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
