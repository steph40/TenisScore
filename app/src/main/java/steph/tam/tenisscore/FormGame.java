package steph.tam.tenisscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FormGame extends AppCompatActivity {

    int reqCode = 2; // requestCode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_game);

        Intent iIn = getIntent();// get intent

        Button add = (Button) findViewById(R.id.buttonSave);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                EditText eTournamentName = (EditText) findViewById(R.id.tourName);
                EditText eDate = (EditText) findViewById(R.id.tourDate);
                EditText eNameP1 = (EditText) findViewById(R.id.playerName1);
                EditText eNameP2 = (EditText) findViewById(R.id.playerName2);

                String nameP1 = eNameP1.getText().toString();
                String tournamentName = eTournamentName.getText().toString();
                String nameP2 = eNameP2.getText().toString();
                String date = eDate.getText().toString();

                if (nameP1.trim().isEmpty() == true || tournamentName.trim().isEmpty() == true || nameP2.trim().isEmpty() == true
                        || date.trim().isEmpty() == true) {
                    Toast.makeText(getApplicationContext(), "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getApplicationContext(), "Certo", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),GameScore.class);//create new intent

                    Bundle b = new Bundle();
                    b.putString("tourName", tournamentName);
                    i.putExtras(b);
                    startActivity(i);
                    finish();
                }

                //Bundle b = new Bundle();
                //b.putString("nameP1", nameP1);
                //i.putExtras(b);

               // setResult(RESULT_OK, i);

                // finishes the activity
                //finish();
            }
        });

    }

    public void onClickBackMain() {
        finish();
    }
}