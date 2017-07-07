package imastudio.rizki.com.cinemamovie.adapter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MAC on 7/7/17.
 */

public class ExtrasCinemaMovieModel implements Parcelable {
    private TrailerModel[] trailers;
    private ReviewCinemaMovieModel[] reviews;
    public ExtrasCinemaMovieModel(TrailerModel[] trailers){
        this.trailers = trailers;
    }

    public ExtrasCinemaMovieModel(ReviewCinemaMovieModel[] reviews){
        this.reviews = reviews;
    }



    public int getTrailersNum(){return this.trailers.length;}
    public int getReviewsNum(){return this.reviews.length;}
    public TrailerModel getTrailerAtIndex(int i){return trailers[i];}
    public ReviewCinemaMovieModel getReviewAtIndex(int i){return reviews[i];}


    @Override
    public int describeContents() {
        return 0;
    }
    private ExtrasCinemaMovieModel(Parcel in){
        trailers= in.createTypedArray(TrailerModel.CREATOR);
        reviews = in.createTypedArray(ReviewCinemaMovieModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(trailers, 0);
        dest.writeTypedArray(reviews, 0);

    }
    public static final Creator<ExtrasCinemaMovieModel> CREATOR = new Creator<ExtrasCinemaMovieModel>() {
        @Override
        public ExtrasCinemaMovieModel createFromParcel(Parcel parcel) {
            return new ExtrasCinemaMovieModel(parcel);
        }

        @Override
        public ExtrasCinemaMovieModel[] newArray(int i) {
            return new ExtrasCinemaMovieModel[i];
        }

    };
}
