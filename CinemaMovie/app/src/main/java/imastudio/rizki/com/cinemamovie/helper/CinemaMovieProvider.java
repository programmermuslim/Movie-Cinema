package imastudio.rizki.com.cinemamovie.helper;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;



public class CinemaMovieProvider extends ContentProvider {

    public static final UriMatcher sUriMatcher = buildUriMatcher();
    private CinemaMovieDbHelper mOpenHelper;
    private static final String TAG = CinemaMovieProvider.class.getSimpleName();


    static final int FAV_MOVIES = 100;
    static final int FAV_MOVIES_ITEMS = 200;

    static UriMatcher buildUriMatcher(){

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CinemaMovieContract.CONTENT_AUTHORITY;


        matcher.addURI(authority,CinemaMovieContract.PATH_MOVIE,FAV_MOVIES);
        matcher.addURI(authority,CinemaMovieContract.PATH_MOVIE+"/*",FAV_MOVIES_ITEMS);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper =  new CinemaMovieDbHelper(getContext());
        return true;
    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                   String sortOrder)     {


        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            // "favourite_movie"
            case FAV_MOVIES: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        CinemaMovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        // use the Uri matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match){
            // Get all favourite movies records.
            case FAV_MOVIES:
                return CinemaMovieContract.MovieEntry.CONTENT_TYPE;

            case FAV_MOVIES_ITEMS:
                return CinemaMovieContract.MovieEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case FAV_MOVIES: {
                long _id = db.insert(CinemaMovieContract.MovieEntry.TABLE_NAME,null,contentValues);
                if(_id > 0)
                    returnUri = CinemaMovieContract.MovieEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into "+uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int rowsDeleted;


        if (null == selection) selection = "1";
        switch (match) {
            case FAV_MOVIES:
                rowsDeleted = db.delete(CinemaMovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }
        //notify the listeners here
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        //return the actual rows deleted
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case FAV_MOVIES:
                rowsUpdated = db.update(CinemaMovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri : " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // return the number of rows impacted by the update.
        return rowsUpdated;
    }
}
