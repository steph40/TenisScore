package steph.tam.tenisscore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    Button fin;
    EditText eName1;
    EditText eName2;
    EditText eNameTour;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent iIn = getIntent();
        Game game = MainActivity.gameById(iIn.getExtras().getInt("id"));

        fin = (Button) findViewById(R.id.buttonFinEdit);
        eName1 = (EditText) findViewById(R.id.playerName1Edit);
        eName2 = (EditText) findViewById(R.id.playerName2Edit);
        eNameTour = (EditText) findViewById(R.id.tourNameEdit);

        //Recebe os dados do objeto que vamos editar para meter no editText
        eName1.setText(game.getNamePlayer1());
        eName2.setText(game.getNamePlayer2());
        eNameTour.setText(game.getNameTournament());


        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name1 = eName1.getText().toString();
                String name2 = eName2.getText().toString();
                String nameTour = eNameTour.getText().toString();

                if (name1.trim().isEmpty() == true || name2.trim().isEmpty() == true || nameTour.trim().isEmpty() == true) {
                    Toast.makeText(getApplicationContext(), "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
                } else {
                    game.setNamePlayer1(name1);
                    game.setNamePlayer2(name2);
                    game.setNameTournament(nameTour);

                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
