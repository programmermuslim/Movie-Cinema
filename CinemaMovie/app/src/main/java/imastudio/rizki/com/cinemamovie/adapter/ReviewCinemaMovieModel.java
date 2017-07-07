package imastudio.rizki.com.cinemamovie.adapter;

import android.os.Parcel;
import android.os.Parcelable;



public class ReviewCinemaMovieModel implements Parcelable {
    private String author;
    private String body;

    public ReviewCinemaMovieModel(String author, String body){
        this.author = author;
        this.body = body;
    }

    public String getAuthor(){return author;}


    public String getBody(){return body;}

    @Override
    public int describeContents() {
        return 0;
    }
    private ReviewCinemaMovieModel(Parcel in){
        author = in.readString();
        body = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(body);
    }

    public static final Creator<ReviewCinemaMovieModel> CREATOR = new Creator<ReviewCinemaMovieModel>() {
        @Override
        public ReviewCinemaMovieModel createFromParcel(Parcel parcel) {
            return new ReviewCinemaMovieModel(parcel);
        }

        @Override
        public ReviewCinemaMovieModel[] newArray(int i) {
            return new ReviewCinemaMovieModel[i];
        }

    };
}
