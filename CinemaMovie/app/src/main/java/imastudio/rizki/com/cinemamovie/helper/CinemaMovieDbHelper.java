package imastudio.rizki.com.cinemamovie.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class CinemaMovieDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "megamovie.db";
    private static final int DATABASE_VERSION = 2;

    public CinemaMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate (SQLiteDatabase sqLiteDatabase){
        final String SQL_CREATE_MOVIE_TABLE =

                "CREATE TABLE" + CinemaMovieContract.MovieEntry.TABLE_NAME + "(" +
                        CinemaMovieContract.MovieEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                        CinemaMovieContract.MovieEntry.COLUMN_MOVIE_ID + "TEXT NOT NULL," +
                        CinemaMovieContract.MovieEntry.COLUMN_TITLE + "TEXT NOT NULL," +
                        CinemaMovieContract.MovieEntry.COLUMN_RELEASE_DATE + "TEXT NOT NULL," +
                        CinemaMovieContract.MovieEntry.COLUMN_POSTER_IMAGE + "TEXT NOT NULL," +
                        CinemaMovieContract.MovieEntry.COLUMN_OVERVIEW + "TEXT NOT NULL," +
                        CinemaMovieContract.MovieEntry.COLUMN_AVERAGE_RATING + "REAL NOT NULL,"+
                        CinemaMovieContract.MovieEntry.COLUMN_BACK_POSTER + "REAL NOT NULL )";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }


    @Override
    public void onUpgrade (SQLiteDatabase sqLiteDatabase,int oldVersion, int newVersion){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CinemaMovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
