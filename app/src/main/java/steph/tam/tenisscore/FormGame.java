package steph.tam.tenisscore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FormGame extends AppCompatActivity {
    EditText eTournamentName;
    EditText eDate;
    EditText eNameP1;
    EditText eNameP2;
    Button add;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_game);

        Intent iIn = getIntent();// get intent

        add = (Button) findViewById(R.id.buttonAdd);
        eTournamentName = (EditText) findViewById(R.id.tourName);
        eDate = (EditText) findViewById(R.id.tourDate);
        eNameP1 = (EditText) findViewById(R.id.playerName1);
        eNameP2 = (EditText) findViewById(R.id.playerName2);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FormGame.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nameP1 = eNameP1.getText().toString();
                String tournamentName = eTournamentName.getText().toString();
                String nameP2 = eNameP2.getText().toString();
                String date = eDate.getText().toString();

                if (nameP1.trim().isEmpty() == true || tournamentName.trim().isEmpty() == true || nameP2.trim().isEmpty() == true || date.trim().isEmpty() == true) {
                    Toast.makeText(getApplicationContext(), "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(getApplicationContext(), GameScore.class);//create new intent

                    Game g = new Game(MainActivity.games.size()+1,tournamentName);
                    MainActivity.games.add(g);
                    startActivityForResult(i,1);

                }

            }
        });

    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.FRANCE);
        eDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    public void onClickBackMain() {
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode==1) {
            setResult(RESULT_OK);
            finish();
        }

    }

}