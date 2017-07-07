package imastudio.rizki.com.cinemamovie.Network;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
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

import imastudio.rizki.com.cinemamovie.BuildConfig;
import imastudio.rizki.com.cinemamovie.adapter.CinemaMovieListModel;
import imastudio.rizki.com.cinemamovie.adapter.ExtrasCinemaMovieModel;
import imastudio.rizki.com.cinemamovie.adapter.TrailerModel;



public class FetchTrailersCinemaMovieTask extends AsyncTask<Integer,Void, ExtrasCinemaMovieModel> {


    List<CinemaMovieListModel> movieItems;
    private Event event;
    private final Context mContext;
    private ArrayAdapter<ExtrasCinemaMovieModel> adapter;
    private final String LOG_TAG = FetchTrailersCinemaMovieTask.class.getSimpleName();

    public FetchTrailersCinemaMovieTask(Context context, Event event) {
        super();
        this.event=event;
        this.mContext=context;
    }

    private ExtrasCinemaMovieModel getExtraDataFromJson(String movieJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String MOVIE_ID = "id";
        final String MOVIE_TRAILER = "videos";
        final String RESULTS = "results";

        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray movieYoutubeTrailers = movieJson.getJSONArray(RESULTS);
        TrailerModel[] trailers = new TrailerModel[movieYoutubeTrailers.length()];
        for (int i = 0; i < movieYoutubeTrailers.length(); i++) {
            JSONObject trailer = movieYoutubeTrailers.getJSONObject(i);
            trailers[i] = new TrailerModel(trailer.getString("name"), trailer.getString("key"));
        }
        ExtrasCinemaMovieModel extras = new ExtrasCinemaMovieModel(trailers);
        return extras;

    }

    @Override
    protected ExtrasCinemaMovieModel doInBackground(Integer... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String movieJsonStr = null;
        String format = "json";



        try {

            final String APPID_PARAM = "api_key";
            final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/" + params[0] + "/";
            final String MOVIE_TRAILER_URL=MOVIE_BASE_URL+"videos"+"?";
            //final String API_PARAM = BuildConfig.THE_MOVIE_DB_API_KEY;
            Uri builtUri = Uri.parse(MOVIE_TRAILER_URL).buildUpon()
                    .appendQueryParameter(APPID_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());
            Log.v(LOG_TAG, "Built uri " + builtUri.toString());

            // Create the request to themoviedb, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            movieJsonStr = buffer.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the  data, there's no point in attemping
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
                    Log.e(LOG_TAG, "Error in closing stream", e);
                }
            }
        }

        try {
            Log.v(LOG_TAG, movieJsonStr);
            return getExtraDataFromJson(movieJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ExtrasCinemaMovieModel extras) {
        if (extras != null) {
            super.onPostExecute(extras);
            event.onTrailersFetch(extras);
        }
    }

    void addExtrasToMovieItems(ArrayList<ExtrasCinemaMovieModel> extras){
        for(int i=0; i<extras.size();i++){
           // movieItems.get(i).setExtras(extras.get(i));
        }
    }

    public interface Event {
        void onTrailersFetch(ExtrasCinemaMovieModel extras);
    }
}
