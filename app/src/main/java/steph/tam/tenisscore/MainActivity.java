package steph.tam.tenisscore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Game> games;
    private ListView gamesListView;
    private GameAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gamesListView = findViewById(R.id.lv_game);


        games = new ArrayList<>();

        adapter = new GameAdapter(this, games);
        gamesListView.setAdapter(adapter);

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
            Toast.makeText(getApplicationContext(), "Teste", Toast.LENGTH_SHORT).show();
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

    /**
     * @param id
     * @return
     */
    public static boolean removeGame(int id) {
        if (games != null && !games.isEmpty()) {
            for (Game g : games) {
                if (g.getId() == id) {
                    games.remove(g);
                    return true;
                }
            }
        }
        return false;
    }

    public void removerItem(View view){
        Intent i = new Intent(view.getContext(),Remover.class);
        Game item = (Game) adapter.getItem(gamesListView.getPositionForView(view));
        i.putExtra("id",item.getId());
        startActivityForResult(i,1);
    }
}

