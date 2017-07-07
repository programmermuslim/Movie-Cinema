package imastudio.rizki.com.cinemamovie.Network;


import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import imastudio.rizki.com.cinemamovie.BuildConfig;
import imastudio.rizki.com.cinemamovie.adapter.CinemaMovieListAdapter;
import imastudio.rizki.com.cinemamovie.adapter.CinemaMovieListModel;


public class FetchCinemaMoviesTask extends AsyncTask<String, Void, List<CinemaMovieListModel>> {
    private final String LOG_TAG = FetchCinemaMoviesTask.class.getSimpleName();
    private ArrayAdapter<CinemaMovieListModel> mAdapter;
    public static Context mcontext;
    private CinemaMovieListModel mMovie;
    public FetchCinemaMoviesTask(Context context, ArrayAdapter<CinemaMovieListModel> MovieAdapter){
        mcontext = context;
        mAdapter = MovieAdapter;
    }



    public List<CinemaMovieListModel> getMoviesDataFromJson(String moviesJsonStr)
            throws JSONException {

        //These are the names of the json Objects that we need to be extracted
        final String MOVIE_ID = "id";
        final String OWN_RESULT = "results"; // contains arrays of objects
        final String MOVIE_POSTER = "poster_path";
        final String BACK_POSTER = "backdrop_path";
        final String MOVIE_TITLE = "title";
        final String MOVIE_VOTE = "vote_average";
        final String MOVIE_SYNOPSIS = "overview";
        final String MOVIE_RELEASE_DATE = "release_date";

            List<CinemaMovieListModel> moviesdata = new ArrayList<>();


            // this will call the data that is being in the result of the json
            JSONObject movieJson = new JSONObject(moviesJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(OWN_RESULT);
            Vector<ContentValues> cVVector = new Vector<ContentValues>(movieArray.length());
            String imageURl;
            String posterURl;


            for (int i = 0; i < movieArray.length(); i++) {

                //get the JSON object representing the movies of the single movie
                JSONObject movies = movieArray.getJSONObject(i);

                String poster = movies.getString(MOVIE_POSTER);
                String backposter = movies.getString(BACK_POSTER);
                String title = movies.getString(MOVIE_TITLE);
                String release_date = movies.getString(MOVIE_RELEASE_DATE);
                String synopsis = movies.getString(MOVIE_SYNOPSIS);
                String rating = movies.getString(MOVIE_VOTE);
                int id = movies.getInt(MOVIE_ID);


                imageURl = "https://image.tmdb.org/t/p/w185" + poster; //w185
                posterURl = "https://image.tmdb.org/t/p/w500" + backposter; //720

                Log.v(LOG_TAG, "movieid: " + id);


                CinemaMovieListModel movieList = new CinemaMovieListModel(imageURl, title, release_date, synopsis, rating, id,posterURl);
                moviesdata.add(movieList);


            }
        return moviesdata;
    }



    @Override
    protected List<CinemaMovieListModel> doInBackground(String... params) {
        String sortQuery = params[0];

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        String movieJsonStr = null;

        int numMov = 0;
        try {

            switch(sortQuery)
                {   case "0":   sortQuery="popular";
                    break;

                    case "1":   sortQuery="top_rated";
                        break;
                }


            final String MOVIE_BASE_URL =
                    "https://api.themoviedb.org/3/movie";


            final String APPID_PARAM = "api_key";
            final String LANGUAGE_PARAM = "language";


            Uri builtUri = Uri.parse(MOVIE_BASE_URL)
                    .buildUpon()
                    .appendPath(sortQuery)
                    .appendQueryParameter(APPID_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                    .appendQueryParameter(LANGUAGE_PARAM, "en-US")
                    .build();


            URL url = new URL(builtUri.toString());
            Log.v(LOG_TAG, "Built uri" + builtUri.toString());
                       urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonStr = buffer.toString();
            Log.v(LOG_TAG, "Forecast json string: " + movieJsonStr);
        } catch (IOException e) {
            Log.e("ForecastFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("ForecastFragment", "Error closing stream", e);
                }
            }
        }try {
            return getMoviesDataFromJson(movieJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(List<CinemaMovieListModel> movies) {
        if (movies != null) {
            mAdapter.clear(); // first we clear all the previous entry
            for(CinemaMovieListModel movieForecastStr : movies) { // then we add each forecast entery one by one
                mAdapter.add(movieForecastStr); // from the server to the adapter.
            }
        }
    }

}