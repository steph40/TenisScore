package steph.tam.tenisscore;

import static steph.tam.tenisscore.R.*;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Game> games;
    private ListView gamesListView;
    private GameAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        gamesListView = findViewById(id.lv_game);

        games = new ArrayList<>();

        adapter = new GameAdapter(this, games);

        gamesListView.setAdapter(adapter);

    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case id.item1:
                onClickFormGame(this);
                return true;
            case id.item2:
                onClickAbout(this);
                return true;
            default:
                return false;
        }
    }

    public void onClickAbout(MainActivity view) {
        Intent i = new Intent(this, About.class);
        startActivityForResult(i, 1);
    }

    public void onClickFormGame(MainActivity view) {
        Intent i = new Intent(this, FormGame.class);
        startActivityForResult(i, 1);
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {
            //sort the view by the date
            Collections.sort(games);
            adapter.notifyDataSetChanged();

        }

    }

    /**
     * @param id
     * @return
     */
    public static Game gameById(int id) {
        if (games != null && !games.isEmpty()) {
            for (Game g : games) {
                if (g.getId() == id) {
                    return g;
                }
            }
        }
        return null;
    }
}

