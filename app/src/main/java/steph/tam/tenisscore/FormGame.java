package steph.tam.tenisscore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
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

                String tournamentName = eTournamentName.getText().toString();
                String date = eDate.getText().toString();
                String nameP1 = eNameP1.getText().toString();
                String nameP2 = eNameP2.getText().toString();

                if (nameP1.trim().isEmpty() == true || tournamentName.trim().isEmpty() == true || nameP2.trim().isEmpty() == true || date.trim().isEmpty() == true) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FormGame.this);

                    // Set the message show for the Alert time
                    builder.setMessage("Todos os campos são obrigatórios!");

                    // Set Alert Title
                    builder.setTitle("Atenção");

                    // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                    builder.setCancelable(false);

                    // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                    builder.setNeutralButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                        // When the user click yes button then app will close
                        dialog.cancel();
                    });

                    // Create the Alert dialog
                    AlertDialog alertDialog = builder.create();
                    // Show the Alert Dialog box
                    alertDialog.show();
                } else {

                    Intent i = new Intent(getApplicationContext(), GameScore.class);//create new intent
                    int id = MainActivity.games.size() + 1;
                    i.putExtra("id",id);
                    Game g = new Game(id, tournamentName,date,nameP1,nameP2,0,0);
                    MainActivity.games.add(g);

                    startActivityForResult(i, 1);
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
        setResult(RESULT_CANCELED);
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            setResult(RESULT_OK);
            finish();
        }
    }
}