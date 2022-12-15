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
import android.widget.CalendarView;
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
    Button voltar;
    final Calendar myCalendar = Calendar.getInstance();
    Gestao gestao;
    boolean dateState;
    int aYear = myCalendar.get(Calendar.YEAR), aMonth = myCalendar.get(Calendar.MONTH), aDay = myCalendar.get(Calendar.DAY_OF_MONTH);
    boolean firstTime = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_game);


        voltar = findViewById(R.id.buttonBack);
        add = (Button) findViewById(R.id.buttonAdd);
        eTournamentName = (EditText) findViewById(R.id.tourName);
        eDate = (EditText) findViewById(R.id.tourDate);
        eNameP1 = (EditText) findViewById(R.id.playerName1);
        eNameP2 = (EditText) findViewById(R.id.playerName2);
        gestao = new Gestao(this);


        /**
         * Ao carregar na Edit Text para colocar a data
         */
        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateState = true;
                //if (firstTime == true) {
                    //new DatePickerDialog(FormGame.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    //firstTime = false;
                //} else {
                    new DatePickerDialog(FormGame.this, date, aYear, aMonth, aDay).show();
                //}
            }
        });

        /**
         * Carregar no Botão voltar
         */
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_OK);
                finish();
            }
        });

        /**
         * Ao carregar no botão para adicionar um jogo
         */
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tournamentName = eTournamentName.getText().toString();
                String date = eDate.getText().toString();
                String nameP1 = eNameP1.getText().toString();
                String nameP2 = eNameP2.getText().toString();

                //Check if string is empty
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

                    gestao.insertGame(tournamentName, date, nameP1, nameP2, 0, 0, 0, 0, 0, 0, 0);

                    i.putExtra("id", gestao.lastId());

                    startActivityForResult(i, 1);
                }
            }
        });
    }

    /**
     * Definir a data
     */
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            aYear = year;
            aMonth = month;
            aDay = day;
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
            dateState = false;
        }
    };

    /**
     * Definir a data selecionada como String
     */
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.FRANCE);
        eDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    /**
     * Esperar pela resposta da activity seguinte
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            setResult(RESULT_OK);
            finish();
        }
    }

    /**
     * Guardar o estado da Activity
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("date", String.valueOf(myCalendar.getTime()));
        outState.putBoolean("dialogState", dateState); //Save the state of date picker
        outState.putInt("dateYear", aYear);
        outState.putInt("dateMonth", aMonth);
        outState.putInt("dateDay", aDay);
        if (dateState == true) { //if state of date picker is true, save the fields of date picker
            outState.putBoolean("firstTime", firstTime);
            outState.putInt("dateYear", aYear);
            outState.putInt("dateMonth", aMonth);
            outState.putInt("dateDay", aDay);
        }

        super.onSaveInstanceState(outState);
    }

    /**
     * Restaurar o estado da Activity
     *
     * @param outState
     */
    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        dateState = outState.getBoolean("dialogState"); //get the state of date picker
        firstTime = outState.getBoolean("firstTime");
        aYear = outState.getInt("dateYear");
        aMonth = outState.getInt("dateMonth");
        aDay = outState.getInt("dateDay");

        if (dateState == true) { //if state of data picker is true, create a new date picker object
            new DatePickerDialog(FormGame.this, date, aYear, aMonth, aDay).show();
            Toast.makeText(getApplicationContext(),outState.getInt("dateMonth")+"",Toast.LENGTH_SHORT).show();
        }

    }
}