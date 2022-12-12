package steph.tam.tenisscore;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    Button fin;
    Button back;
    EditText eName1;
    EditText eName2;
    EditText eNameTour;
    EditText eDate;
    final Calendar myCalendar = Calendar.getInstance();
    Gestao gestao;
    boolean dateState;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        gestao = new Gestao(this);

        Intent iIn = getIntent();
        Game game = gestao.getGame(iIn.getExtras().getInt("id"));

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
                new DatePickerDialog(EditActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                    game.setNamePlayer1(name1);
                    game.setNamePlayer2(name2);
                    game.setNameTournament(nameTour);
                    game.setDateTournament(date);
                    gestao.updateGameForm(game.getId(), game.getNameTournament(), game.getDateTournament(), game.getNamePlayer1(), game.getNamePlayer2());

                    setResult(RESULT_OK);
                    finish();
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

    /**
     * Definir a data
     */
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
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

    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("dialogState", dateState);
        if (dateState == true) {
            outState.putInt("dateYear", myCalendar.get(Calendar.YEAR));
            outState.putInt("dateMonth", myCalendar.get(Calendar.MONTH));
            outState.putInt("dateDay", myCalendar.get(Calendar.DAY_OF_MONTH));
        }

        super.onSaveInstanceState(outState);
    }

    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        dateState = outState.getBoolean("dialogState");


        if (dateState == true) {
            new DatePickerDialog(EditActivity.this, date, outState.getInt("dateYear"), outState.getInt("dateMonth"), outState.getInt("dateDay")).show();
        }

    }
}
