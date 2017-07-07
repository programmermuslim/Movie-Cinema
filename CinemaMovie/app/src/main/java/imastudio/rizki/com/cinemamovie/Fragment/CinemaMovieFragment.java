package imastudio.rizki.com.cinemamovie.Fragment;


import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import imastudio.rizki.com.cinemamovie.Network.FetchCinemaMoviesTask;
import imastudio.rizki.com.cinemamovie.R;
import imastudio.rizki.com.cinemamovie.activity.DetailCinemaMovieActivity;
import imastudio.rizki.com.cinemamovie.adapter.CinemaMovieListModel;
import imastudio.rizki.com.cinemamovie.adapter.CinemaMovieListAdapter;



public class CinemaMovieFragment extends Fragment {
    public static final String EXTRA_MOVIE_DATA = "EXTRA_MOVIE_DATA";
    private ArrayAdapter<CinemaMovieListModel> mAdapter;
    private FloatingActionMenu menuRed;

    public CinemaMovieFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {

        FetchCinemaMoviesTask fetchMoviesTask = new FetchCinemaMoviesTask(getActivity(),mAdapter);
        SharedPreferences preference= PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = preference.getString(getString(R.string.pref_sort_key),
                getResources().getString(R.string.pref_order_pop));


        fetchMoviesTask.execute(sortOrder);
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        menuRed = (FloatingActionMenu) view.findViewById(R.id.menu_red);


        final FloatingActionButton HighRate = new FloatingActionButton(getActivity());
        HighRate.setButtonSize(FloatingActionButton.SIZE_MINI);
        HighRate.setLabelText("Highest Rated");
        HighRate.setImageResource(R.drawable.ic_star);
        final FloatingActionButton MostPop = new FloatingActionButton((getActivity()));
        MostPop.setButtonSize(FloatingActionButton.SIZE_MINI);
        MostPop.setLabelText("Most Popular");
        MostPop.setImageResource(R.drawable.ic_heart);
        menuRed.addMenuButton(HighRate);
        menuRed.addMenuButton(MostPop);
        menuRed.setMenuButtonColorNormal(ContextCompat.getColor(getContext(),R.color.colorAccent));
        menuRed.setMenuButtonColorPressed(ContextCompat.getColor(getContext(),R.color.colorAccent));

        MostPop.setColorNormal(ContextCompat.getColor(getContext(),R.color.colorAccent));
        MostPop.setColorPressed(ContextCompat.getColor(getContext(),R.color.colorAccent));


        HighRate.setColorNormal(ContextCompat.getColor(getContext(),R.color.colorAccent));
        HighRate.setColorPressed(ContextCompat.getColor(getContext(),R.color.colorAccent));



        HighRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HighRate.setLabelColors(ContextCompat.getColor(getActivity(), R.color.grey),
                        ContextCompat.getColor(getActivity(), R.color.light_grey),
                        ContextCompat.getColor(getActivity(), R.color.white_transparent));
                HighRate.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.black));
            }
        });
        menuRed.setClosedOnTouchOutside(true);
    }
}
