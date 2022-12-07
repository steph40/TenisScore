package steph.tam.tenisscore;

import static steph.tam.tenisscore.R.*;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<Game> games;
    private ListView gamesListView;
    private GameAdapter adapter;
    private TextView titulo;
    SharedPreferences prefs;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        gamesListView = findViewById(id.lv_game);
        titulo = findViewById(id.tv_title_main);

        games = new ArrayList<>();

        adapter = new GameAdapter(this, games);

        gamesListView.setAdapter(adapter);

        //SharedPreferences
        prefs = getSharedPreferences("username", MODE_PRIVATE);
        String user = prefs.getString("nomeUser", null);

        if (user != null) {
            titulo.setText("Jogos do " + user);
        }


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

    int verify = 0;

    public void onClickUser(MainActivity view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set Alert Title
        builder.setTitle("Nome de utilizador");

        EditText inputUser = new EditText(this);
        builder.setView(inputUser);

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false);

        builder.setPositiveButton("Guardar", (DialogInterface.OnClickListener) (dialog, which) -> {
            String user = inputUser.getText().toString();
            titulo.setText("Jogos do " + user);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("nomeUser", user);
            editor.commit();
        });
        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("Cancelar", (DialogInterface.OnClickListener) (dialog, which) -> {
            // When the user click no button then app will close
            dialog.cancel();
        });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();
        // Show the Alert Dialog box
        alertDialog.show();
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

