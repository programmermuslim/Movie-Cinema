package imastudio.rizki.com.cinemamovie.Fragment;


import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import imastudio.rizki.com.cinemamovie.R;
import imastudio.rizki.com.cinemamovie.adapter.CinemaMovieListModel;



public class DetailCinemaMovieFragment extends Fragment {
    View.OnClickListener monClickListener;
    private final String LOG_TAG = DetailCinemaMovieFragment.class.getSimpleName();
    private static final boolean DEBUG = false;


    public DetailCinemaMovieFragment() {
        setHasOptionsMenu(true);
    }

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolBar;
    @BindView(R.id.title) TextView nCinemaMovieTitle;
    @BindView(R.id.poster) ImageView nCinemaMoviePoster;
    @BindView(R.id.backposter) ImageView nBackPoster;
    @BindView(R.id.release_date) TextView nReleaseDate;
    @BindView(R.id.rating) TextView nRatingAvg;
    @BindView(R.id.synopsis) TextView nSynopsis;
    @BindView(R.id.toolbar)
    Toolbar toolbar;



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

        nCinemaMovieTitle.setText(movie.getTitle());
        Picasso.with(getContext())
                .load(movie.getImageurl())
                .placeholder(R.drawable.movie_place)
                .error(R.drawable.image_error2)
                .into(nCinemaMoviePoster);
        Picasso.with(getContext())
                .load((movie.getBackPoster()))
                .placeholder(R.drawable.movie_place)
                .error(R.drawable.image_error2)
                .into(nBackPoster);

        nReleaseDate.setText("Released :\n" + movie.getRelease_date());
        nRatingAvg.setText(String.valueOf("Rating :\n" + movie.getRating() + "/10"));
        nSynopsis.setText("Synopsis :\n" + movie.getSynopsis());


        return rootView;
    }


}

