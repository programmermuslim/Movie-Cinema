package imastudio.rizki.com.cinemamovie.Fragment;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import imastudio.rizki.com.cinemamovie.R;
import imastudio.rizki.com.cinemamovie.adapter.CinemaMovieListModel;
import imastudio.rizki.com.cinemamovie.helper.UpdateFavCinema;

import static imastudio.rizki.com.cinemamovie.helper.UpdateFavCinema.ADDED_TO_FAVORITE;



public class DetailCinemaMovieFragment extends Fragment implements  UpdateFavCinema.DBUpdateListener {
    View.OnClickListener monClickListener;
    private final String LOG_TAG = DetailCinemaMovieFragment.class.getSimpleName();
    private static final boolean DEBUG = false; // Set this to false to disable logs.


    public DetailCinemaMovieFragment() {
        setHasOptionsMenu(true);
    }

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolBar;
    @BindView(R.id.title) TextView mMovieTitle;
    @BindView(R.id.poster) ImageView mMoviePoster;
    @BindView(R.id.backposter) ImageView mBackPoster;
    @BindView(R.id.release_date) TextView mReleaseDate;
    @BindView(R.id.rating) TextView mRatingAverage;
    @BindView(R.id.synopsis) TextView mSynopsis;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.share)
    FloatingActionButton favshare;
    @BindView(R.id.fav)
    FloatingActionButton favsave;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detailcinemamovie, container, false);
       final CinemaMovieListModel movie = getActivity().getIntent().getParcelableExtra(CinemaMovieFragment.EXTRA_MOVIE_DATA);


        Toolbar toolbar = (Toolbar)rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(movie.getTitle());
        ButterKnife.bind(this, rootView);

        mMovieTitle.setText(movie.getTitle());
        Picasso.with(getContext())
                .load(movie.getImageurl())
                .placeholder(R.drawable.movie_place)
                .error(R.drawable.image_error2)
                .into(mMoviePoster);
        Picasso.with(getContext())
                .load((movie.getBackPoster()))
                .placeholder(R.drawable.movie_place)
                .error(R.drawable.image_error2)
                .into(mBackPoster);

        mReleaseDate.setText("Released :\n" + movie.getRelease_date());
        mRatingAverage.setText(String.valueOf("Rating :\n" + movie.getRating() + "/10"));
        mSynopsis.setText("Synopsis :\n" + movie.getSynopsis());//https://www.youtube.com/watch?v=wRaV4SIQY8A


        return rootView;
    }

    public void onSuccess(final int operationType) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String operation;
                if (operationType == ADDED_TO_FAVORITE) {
                    operation = "added to favorite";
                    favsave.setImageResource(R.drawable.ic_star);
                } else {
                    operation = "removed from favorite";
                    favshare.setImageResource(R.drawable.ic_star_nfilled);

                }


            }
        });
    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onStart() {
        if (DEBUG) Log.i(LOG_TAG, "onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        if (DEBUG) Log.i(LOG_TAG, "onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        if (DEBUG) Log.i(LOG_TAG, "onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        if (DEBUG) Log.i(LOG_TAG, "onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (DEBUG) Log.i(LOG_TAG, "onDestroy()");
        super.onDestroy();
    }
}

