package steph.tam.tenisscore;

import static steph.tam.tenisscore.R.*;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import steph.tam.tenisscore.controller.GameDAO;
import steph.tam.tenisscore.controller.GameDAOService;
import steph.tam.tenisscore.games.FormGame;
import steph.tam.tenisscore.games.Game;
import steph.tam.tenisscore.controller.GameAdapter;
import steph.tam.tenisscore.games.GameDetails;
import steph.tam.tenisscore.utilizadores.About;
import steph.tam.tenisscore.utilizadores.InfoUser;

public class MainActivity extends AppCompatActivity {

    public static List<Game> games1;
    private ListView gamesListView;
    //private GameAdapter adapter;
    private TextView titulo;
    SharedPreferences prefs;
    EditText inputUser;
    String token;
    boolean dialogState;
    GameDAO manager;
    GameAdapter adapter;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        manager = new GameDAOService();

        prefs = getSharedPreferences("infoUser", MODE_PRIVATE);
        token = prefs.getString("token", null);

        games1 = new ArrayList<>();
        titulo = findViewById(id.tv_title_main);

        manager.getAllGames(token, new GameDAO.GetGamesListener() {
            @Override
            public void onSuccess(List<Game> games) {
                games1 = games;

                adapter = new GameAdapter(getApplicationContext(), games1);
                gamesListView = findViewById(id.lv_game);
                gamesListView.setAdapter(adapter);

                gamesListView.setTextFilterEnabled(true);

                gamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent in = new Intent(getApplicationContext(), GameDetails.class);
                        in.putExtra("id", games1.get(i).getId());
                        startActivityForResult(in, 1);
                    }
                });

            }

            @Override
            public void onError(String message) {

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
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
            case id.item3:
                onClickUser(this);
                return true;
            case id.item4:
                onclickRefresh(this);
                return true;
            default:
                return false;
        }
    }

    private void onclickRefresh(MainActivity view) {
        manager.getAllGames(token, new GameDAO.GetGamesListener() {
            @Override
            public void onSuccess(List<Game> games) {
                games1 = games;
                adapter.updateList(games1);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    /**
     * Click in About's Icon
     *
     * @param view
     */
    public void onClickAbout(MainActivity view) {
        Intent i = new Intent(this, About.class);
        startActivity(i);

    }

    /**
     * Click in New Game Icon
     *
     * @param view
     */
    public void onClickFormGame(MainActivity view) {
        Intent i = new Intent(this, FormGame.class);
        startActivityForResult(i, 1);
    }

    /**
     * Click in User's Icon
     *
     * @param view
     */
    public void onClickUser(MainActivity view) {
        Intent i = new Intent(this, InfoUser.class);
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
            manager.getAllGames(token, new GameDAO.GetGamesListener() {
                @Override
                public void onSuccess(List<Game> games) {
                    games1 = games;
                    adapter.updateList(games1);
                }

                @Override
                public void onError(String message) {

                }
            });
        }

    }
}

