package imastudio.rizki.com.cinemamovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import imastudio.rizki.com.cinemamovie.Fragment.DetailCinemaMovieFragment;
import imastudio.rizki.com.cinemamovie.MainActivity;
import imastudio.rizki.com.cinemamovie.R;



public class DetailCinemaMovieActivity extends ActionBarActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final boolean DEBUG = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.DetailCinema, new DetailCinemaMovieFragment())
                    .commit();
        }
    }



}
