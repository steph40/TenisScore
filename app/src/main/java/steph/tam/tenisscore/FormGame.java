package steph.tam.tenisscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class FormGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_game);

        Intent iIn = getIntent();// get intent
    }

    public void onClickSave(){

        Intent i = new Intent();//create new intent

        EditText eTournamentName = (EditText) findViewById(R.id.tourName);
        //EditText eDate = (EditText) findViewById(R.id.tourDate);
        //EditText eNameP1 = (EditText) findViewById(R.id.playerName1);
        //EditText eNameP2 = (EditText) findViewById(R.id.playerName2);

        String tournamentName = eTournamentName.getText().toString();





        setResult(RESULT_OK, i);

        finish();

    }

    public void onClickBackMain() {
        finish();
    }
}