package steph.tam.tenisscore;

import static steph.tam.tenisscore.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameScore extends AppCompatActivity {
    Button ponto1;
    Button ponto2;
    Button fin;
    TextView eName1;
    TextView eName2;
    TextView eNametour;
    TextView eDatetour;
    TextView eset1_1;
    TextView eset1_2;
    TextView eset2_1;
    TextView eset2_2;
    TextView eset3_1;
    TextView eset3_2;
    TextView eR1;
    TextView eR2;
    TextView set1;
    TextView set2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_game_score);

        Intent iIn = getIntent();
        Bundle b = iIn.getExtras();
        ponto1 = (Button) findViewById(id.Ponto1);
        ponto2 = (Button) findViewById(id.Ponto2);
        fin = (Button) findViewById(id.buttonFin);
        eName1 = (TextView) findViewById(id.tvNameP1);
        eName2 = (TextView) findViewById(id.tvNameP2);
        eNametour = (TextView) findViewById(id.tvNameTour);
        eDatetour = (TextView) findViewById(id.tvDate);
        eset1_1 = (TextView) findViewById(id.tvSet1_1);
        eset2_1 = (TextView) findViewById(id.tvSet2_1);
        eset3_1 = (TextView) findViewById(id.tvSet3_1);
        eset1_2 = (TextView) findViewById(id.tvSet1_2);
        eset2_2 = (TextView) findViewById(id.tvSet2_2);
        eset3_2 = (TextView) findViewById(id.tvSet3_2);
        eR1 = (TextView) findViewById(id.tvR1);
        eR2 = (TextView) findViewById(id.tvR2);
        set1 = (TextView) findViewById(id.tvSet1);
        set2 = (TextView) findViewById(id.tvSet2);

        ponto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = eName1.getText().toString();
                String name2 = eName2.getText().toString();
                String nametour = eNametour.getText().toString();
                String datetour = eDatetour.getText().toString();
                String set1_1 = eset1_1.getText().toString();
                String set2_1 = eset2_1.getText().toString();
                String set3_1 = eset3_1.getText().toString();
                String set1_2 = eset1_2.getText().toString();
                String set2_2 = eset2_2.getText().toString();
                String set3_2 = eset3_2.getText().toString();
                String r1 = eR1.getText().toString();
                String r2 = eR2.getText().toString();

                Intent it = new Intent(getApplicationContext(), MainActivity.class);
                Bundle bl = new Bundle();

                bl.putString("b_nametour", nametour);
                bl.putString("b_name1", name1);
                bl.putString("b_name2", name2);
                bl.putString("b_datetour", datetour);
                bl.putString("b_set1_1", set1_1);
                bl.putString("b_set2_1", set2_1);
                bl.putString("b_set3_1", set3_1);
                bl.putString("b_set1_2", set1_2);
                bl.putString("b_set2_2", set2_2);
                bl.putString("b_set3_2", set3_2);
                bl.putString("b_R1", r1);
                bl.putString("b_R2", r2);

                it.putExtras(bl);
                startActivity(it);
                finish();
            }
        });

        TextView tv_nameTour = (TextView) findViewById(id.tvNameTour);
        TextView tv_P1 = (TextView) findViewById(id.tvNameP1);
        TextView tv_P2 = (TextView) findViewById(id.tvNameP2);
        TextView tv_data = (TextView) findViewById(id.tvDate);

        String v_nameTour = b.getString("tourName");
        String v_P1 = b.getString("P1");
        String v_P2 = b.getString("P2");
        String v_data = b.getString("tourDate");

        tv_nameTour.setText(v_nameTour);
        tv_P1.setText(" " + v_P1);
        tv_P2.setText(" " + v_P2);
        tv_data.setText(v_data);
    }
}