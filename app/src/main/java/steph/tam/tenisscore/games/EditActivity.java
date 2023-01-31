package steph.tam.tenisscore.games;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import steph.tam.tenisscore.R;
import steph.tam.tenisscore.controller.GameDAO;
import steph.tam.tenisscore.controller.GameDAOService;

public class EditActivity extends AppCompatActivity {

    Button fin;
    Button back;
    EditText eName1;
    EditText eName2;
    EditText eNameTour;
    EditText eDate;
    final Calendar myCalendar = Calendar.getInstance();
    boolean dateState;
    int aYear = myCalendar.get(Calendar.YEAR), aMonth = myCalendar.get(Calendar.MONTH), aDay = myCalendar.get(Calendar.DAY_OF_MONTH);
    DatePickerDialog datePicker;
    String token;
    GameDAO manager;
    SharedPreferences prefs;
    int id;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent iIn = getIntent();
        id = iIn.getExtras().getInt("id");
        manager = new GameDAOService();

        prefs = getSharedPreferences("infoUser", MODE_PRIVATE);
        token = prefs.getString("token", null);


        manager.getGame(token, id, new GameDAO.GetGameListener() {
            @Override
            public void onSuccess(Game game) {

                aYear = iIn.getExtras().getInt("ano");
                aMonth = iIn.getExtras().getInt("mes");
                aDay = iIn.getExtras().getInt("dia");

                fin = (Button) findViewById(R.id.buttonFinEdit);
                back = (Button) findViewById(R.id.buttonBackEdit);
                eName1 = (EditText) findViewById(R.id.playerName1Edit);
                eName2 = (EditText) findViewById(R.id.playerName2Edit);
                eNameTour = (EditText) findViewById(R.id.tourNameEdit);
                eDate = (EditText) findViewById(R.id.tourDateEdit);

                //Recebe os dados do objeto que vamos editar para meter no editText
                eName1.setText(game.getNamePlayer1());
                eName2.setText(game.getNamePlayer2());
                eNameTour.setText(game.getNameTournament());
                eDate.setText(game.getDateTournament());


                /**
                 * Carregar na EditText da data
                 */
                eDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dateState = true;
                        datePicker = new DatePickerDialog(EditActivity.this, date, aYear, aMonth, aDay);
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
                 * Carregar no botão de Guardar
                 */
                fin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String name1 = eName1.getText().toString();
                        String name2 = eName2.getText().toString();
                        String nameTour = eNameTour.getText().toString();
                        String date = eDate.getText().toString();

                        //Check if string is empty
                        if (name1.trim().isEmpty() == true || name2.trim().isEmpty() == true || nameTour.trim().isEmpty() == true || date.trim().isEmpty() == true) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);

                            // Set the message show for the Alert time
                            builder.setMessage("Todos os campos são obrigatórios !");

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
                            //if string is not empty, edit the fields of object
                            game.setNamePlayer1(name1);
                            game.setNamePlayer2(name2);
                            game.setNameTournament(nameTour);
                            game.setDateTournament(date);

                            //Update database with object

                            manager.editGame(token, game, new GameDAO.GameEditListener() {
                                @Override
                                public void onSuccess(String message) {
                                    Intent i = new Intent();
                                    i.putExtra("ano", aYear);
                                    i.putExtra("mes", aMonth);
                                    i.putExtra("dia", aDay);
                                    setResult(RESULT_OK, i);
                                    finish();
                                }

                                @Override
                                public void onError(String message) {
                                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    }
                });

                /**
                 * Carregar no botão de Voltar
                 */
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                });
            }

            @Override
            public void onError(String message) {

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
     * Atualizar a data
     */
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.FRANCE);
        eDate.setText(dateFormat.format(myCalendar.getTime()));
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
            new DatePickerDialog(EditActivity.this, date, aYear, aMonth, aDay).show();
        }

    }
}
