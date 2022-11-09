package steph.tam.tenisscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    int reqCode = 1; // requestCode
    //List<Game> games;
    //ListView gamesListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListAdapter adapter = new GameAdapter(this, games);
        //gamesListView = (ListView) findViewById(R.id.lv_game);
        //gamesListView.setAdapter(adapter);

        // enables filtering using the keyboard, if available
        //gamesListView.setTextFilterEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                onClickFormGame(this);
                return true;

            default:
                return false;
        }
    }

    public void onClickFormGame(MainActivity view) {
        Intent i = new Intent(this, FormGame.class);
        startActivityForResult(i, reqCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == reqCode) { // // if it is the request that I did
            if (resultCode == RESULT_OK) {  // if the result is RESULT_OK


                // gets the Bundle of the response intent
                Bundle b = data.getExtras();
                String var1 = b.getString("tourName");








            }
        }
    }


}