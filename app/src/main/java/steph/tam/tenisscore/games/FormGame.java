package steph.tam.tenisscore.games;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import steph.tam.tenisscore.R;
import steph.tam.tenisscore.controller.GameDAO;
import steph.tam.tenisscore.controller.GameDAOService;

public class FormGame extends AppCompatActivity {
    EditText eTournamentName;
    EditText eDate;
    EditText eNameP1;
    EditText eNameP2;
    Button add;
    Button voltar;
    final Calendar myCalendar = Calendar.getInstance();
    boolean dateState;
    int aYear = myCalendar.get(Calendar.YEAR), aMonth = myCalendar.get(Calendar.MONTH), aDay = myCalendar.get(Calendar.DAY_OF_MONTH);
    DatePickerDialog datePicker;
    String token;
    GameDAO manager;
    SharedPreferences prefs;

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
        manager = new GameDAOService();

        prefs = getSharedPreferences("infoUser", MODE_PRIVATE);
        token = prefs.getString("token", null);


        /**
         * Ao carregar na Edit Text para colocar a data
         */
        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateState = true;
                datePicker = new DatePickerDialog(FormGame.this, date, aYear, aMonth, aDay);
                datePicker.show();
                Button cancel = datePicker.getButton(DialogInterface.BUTTON_NEGATIVE);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateState = false;
                        datePicker.dismiss();
                    }
                });
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
                Log.d("teste", date);
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
                    i.putExtra("ano", aYear);
                    i.putExtra("mes", aMonth);
                    i.putExtra("dia", aDay);


                    Game auxGame = new Game(40, tournamentName, date, nameP1, nameP2, 0, 0, 0, 0, 0, 0, 0, false, 0);
                    manager.addGames(token, auxGame, new GameDAO.AddGameListener() {
                        @Override
                        public void onSuccess(String message) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            manager.getLastId(token, new GameDAO.GetLastIdListener() {
                                @Override
                                public void onSuccess(int id) {
                                    Toast.makeText(getApplicationContext(), id + "", Toast.LENGTH_SHORT).show();
                                    i.putExtra("id", id);
                                    startActivityForResult(i, 1);
                                }

                                @Override
                                public void onError(String message) {
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
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

        aYear = outState.getInt("dateYear");
        aMonth = outState.getInt("dateMonth");
        aDay = outState.getInt("dateDay");

        if (dateState == true) { //if state of data picker is true, create a new date picker object
            new DatePickerDialog(FormGame.this, date, aYear, aMonth, aDay).show();
        }

    }
}