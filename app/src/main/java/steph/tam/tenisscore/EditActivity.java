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
        Bundle b = iIn.getExtras();
        fin = (Button) findViewById(R.id.buttonFinEdit);
        eName1 = (EditText) findViewById(R.id.playerName1Edit);
        eName2 = (EditText) findViewById(R.id.playerName2Edit);
        eNameTour = (EditText) findViewById(R.id.tourNameEdit);

        String v_nameTour = b.getString("bu_nametour");
        String v_name1 = b.getString("bu_name1");
        String v_name2 = b.getString("bu_name2");

        eNameTour.setText(v_nameTour);
        eName1.setText(v_name1);
        eName2.setText(v_name2);

        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = eName1.getText().toString();
                String name2 = eName2.getText().toString();
                String nameTour = eNameTour.getText().toString();
                if (name1.trim().isEmpty() == true || name2.trim().isEmpty() == true || nameTour.trim().isEmpty() == true) {
                    Toast.makeText(getApplicationContext(), "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent();
                    Bundle b = new Bundle();

                    b.putString("name1", name1);
                    b.putString("name2", name2);
                    b.putString("nameTour", nameTour);

                    i.putExtras(b);
                    setResult(RESULT_OK, i);
                    finish();
                }
            }
        });
    }
}
