package imastudio.rizki.com.cinemamovie.adapter;

import android.os.Parcel;
import android.os.Parcelable;



public class TrailerModel implements Parcelable {
    private String title;
    private String source;

    public TrailerModel(String title, String source){
        this.title = title;
        this.source = source;
    }

    public String getTitle(){return title;}
    public String getSource(){return source;}

    @Override
    public int describeContents() {
        return 0;
    }

    public TrailerModel(Parcel in){
        title = in.readString();
        source = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(source);
    }
    public static final Parcelable.Creator<TrailerModel> CREATOR = new Parcelable.Creator<TrailerModel>() {
        @Override
        public TrailerModel createFromParcel(Parcel parcel) {
            return new TrailerModel(parcel);
        }

        @Override
        public TrailerModel[] newArray(int i) {
            return new TrailerModel[i];
        }

    };
}
