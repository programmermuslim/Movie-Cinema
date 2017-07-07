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

/**
 * Created by MAC on 7/7/17.
 */

public class DetailCinemaMovieActivity extends ActionBarActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final boolean DEBUG = false; // Set this to false to disable logs.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.DetailAct, new DetailCinemaMovieFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, MenuSettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
