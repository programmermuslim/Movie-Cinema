package imastudio.rizki.com.cinemamovie.helper;



import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;


import imastudio.rizki.com.cinemamovie.adapter.CinemaMovieListModel;



public class UpdateFavCinema extends AsyncTask<Void, Void, Void> {

    private static final String LOG_TAG = UpdateFavCinema.class.getSimpleName();

    private Context mContext;
    private CinemaMovieListModel mMovie;
    private DBUpdateListener mDBUpdateListener;

    public interface DBUpdateListener {
        void onSuccess(int operationType);

        void onFailure();
    }

    public static final int ADDED_TO_FAVORITE = 1;
    public static final int REMOVED_FROM_FAVORITE = 2;

    public UpdateFavCinema(Context context, CinemaMovieListModel movie, DBUpdateListener updateListener) {
        mDBUpdateListener = updateListener;
        mContext = context;
        mMovie = movie;
    }

    @Override
    protected Void doInBackground(Void... params) {
        addMovie();
        return null;
    }

    public void addMovie() {
        //Check if the movie with this movie_id  exists in the db

        Log.d(LOG_TAG, CinemaMovieContract.MovieEntry.CONTENT_URI.getAuthority());

        Cursor favMovieCursor = mContext.getContentResolver().query(
                CinemaMovieContract.MovieEntry.CONTENT_URI,
                new String[]{CinemaMovieContract.MovieEntry.COLUMN_MOVIE_ID},
                CinemaMovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                new String[]{String.valueOf(mMovie.getId())},
                null);

        // If it exists, delete the movie with that movie id
        if (favMovieCursor.moveToFirst()) {
            int rowDeleted = mContext.getContentResolver().delete(CinemaMovieContract.MovieEntry.CONTENT_URI,
                    CinemaMovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                    new String[]{String.valueOf(mMovie.getId())});

            if (rowDeleted > 0) {
                mDBUpdateListener.onSuccess(REMOVED_FROM_FAVORITE);
            } else {
                mDBUpdateListener.onFailure();
            }

        } else {

            // Otherwise, insert it using the content resolver and the base URI
            ContentValues values = new ContentValues();

            //Then add the data, along with the corresponding name of the data type,
            //so the content provider knows what kind of value is being inserted.
            values.put(CinemaMovieContract.MovieEntry.COLUMN_MOVIE_ID, mMovie.getId());
            values.put(CinemaMovieContract.MovieEntry.COLUMN_TITLE, mMovie.getTitle());
            values.put(CinemaMovieContract.MovieEntry.COLUMN_POSTER_IMAGE, mMovie.getImageurl());
            values.put(CinemaMovieContract.MovieEntry.COLUMN_OVERVIEW, mMovie.getSynopsis());
            values.put(CinemaMovieContract.MovieEntry.COLUMN_AVERAGE_RATING, mMovie.getRating());
            values.put(CinemaMovieContract.MovieEntry.COLUMN_RELEASE_DATE, mMovie.getRelease_date());
            values.put(CinemaMovieContract.MovieEntry.COLUMN_BACK_POSTER, mMovie.getBackPoster());


            // Finally, insert movie data into the database.
            Uri insertedUri = mContext.getContentResolver().insert(
                    CinemaMovieContract.MovieEntry.CONTENT_URI,
                    values);

            // The resulting URI contains the ID for the row.  Extract the movie rowId from the Uri.
            long movieRowId = ContentUris.parseId(insertedUri);

            if (movieRowId > 0) {
                mDBUpdateListener.onSuccess(ADDED_TO_FAVORITE);
            } else {
                mDBUpdateListener.onFailure();
            }
        }
        favMovieCursor.close();
    }

    }

