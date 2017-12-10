package banbanmu.github.io.cleanpotty.resource;

import android.os.Parcel;
import android.os.Parcelable;

import banbanmu.github.io.cleanpotty.gson.GsonUtil;

/**
 * Created by min on 2017. 9. 16..
 */

public class User implements Parcelable {

    private String id;

    private String email;

    private String name;

    private String hash;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User() {}

    public User(Parcel in) {
        read(GsonUtil.gson.fromJson(in.readString(), User.class));
    }

    public void read(User user) {
        id = user.getId();
        email = user.getEmail();
        name = user.getName();
        hash = user.getHash();
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
