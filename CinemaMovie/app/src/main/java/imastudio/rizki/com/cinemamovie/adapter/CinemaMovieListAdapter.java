package imastudio.rizki.com.cinemamovie.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import imastudio.rizki.com.cinemamovie.R;



public class CinemaMovieListAdapter extends ArrayAdapter<CinemaMovieListModel> {
    private final String LOG_TAG = CinemaMovieListAdapter.class.getSimpleName();

    public CinemaMovieListAdapter(Context context, int resources) {

        super(context, 0, resources);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CinemaMovieListModel moviesList = getItem(position);


        if ( convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_cinemamovie, parent, false);

        }
        ImageView posterView = (ImageView) convertView.findViewById(R.id.flavor_image);
        TextView txtTitle = (TextView)convertView.findViewById(R.id.titleMovie);

        Log.v(LOG_TAG,"url "+ moviesList.getImageurl());
        Picasso.with(getContext()).load(moviesList.getImageurl()).placeholder(R.drawable.movie_place).fit().into(posterView);
        txtTitle.setText(moviesList.getTitle());


        return convertView;
    }
}
