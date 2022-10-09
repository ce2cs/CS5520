package edu.northeastern.testapplication.linkCollector;

import android.os.Parcel;
import android.os.Parcelable;

public class Link implements Parcelable {

    private final String url;
    private final String name;

    public Link(String name, String url) {
        this.name = name;
        this.url = url;
    }

    protected Link(Parcel in) {
        url = in.readString();
        name = in.readString();
    }

    public static final Creator<Link> CREATOR = new Creator<Link>() {
        @Override
        public Link createFromParcel(Parcel in) {
            return new Link(in.readString(), in.readString());
        }

        @Override
        public Link[] newArray(int size) {
            return new Link[size];
        }
    };

    public String getName() {
                          return name;
                                      }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(name);
    }
}
