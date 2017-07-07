package imastudio.rizki.com.cinemamovie.Fragment;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;



import imastudio.rizki.com.cinemamovie.Network.SyncDataCinemaMovie;
import imastudio.rizki.com.cinemamovie.R;
import imastudio.rizki.com.cinemamovie.activity.DetailCinemaMovieActivity;
import imastudio.rizki.com.cinemamovie.adapter.CinemaMovieListModel;
import imastudio.rizki.com.cinemamovie.adapter.CinemaMovieListAdapter;



public class CinemaMovieFragment extends Fragment {
    public static final String EXTRA_MOVIE_DATA = "EXTRA_MOVIE_DATA";
    private ArrayAdapter<CinemaMovieListModel> mAdapter;


    public CinemaMovieFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {

        SyncDataCinemaMovie syncDataCinemaMovie = new SyncDataCinemaMovie(getActivity(),mAdapter);
        SharedPreferences preference= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = preference.getString(getString(R.string.pref_sort_key),
                getResources().getString(R.string.pref_order_pop));


        syncDataCinemaMovie.execute(sortOrder);
        super.onStart();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_cinemamovie, container, false);

        mAdapter = new CinemaMovieListAdapter(getActivity(), R.layout.item_cinemamovie);

        final GridView gridView = (GridView) rootview.findViewById(R.id.grid_cinema);
        gridView.setAdapter(mAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                CinemaMovieListModel movie = mAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailCinemaMovieActivity.class);
                intent.putExtra(CinemaMovieFragment.EXTRA_MOVIE_DATA, movie);
                startActivity(intent);
            }
        });

        return rootview;

    }


}
