package steph.tam.tenisscore;

import static steph.tam.tenisscore.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class GameScore extends AppCompatActivity {
    Button ponto1;
    Button ponto2;
    Button fin;
    Button edit;
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
    int valor1 = 0, valor2 = 0, r1 = 0, r2 = 0, r1_1 = 0, r1_2 = 0, r2_1 = 0, r2_2 = 0, r3_1 = 0, r3_2 = 0;
    int reqCode = 1;


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
        edit = (Button) findViewById(id.buttonEdit);
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

        View.OnClickListener listener = setResultados(set1, set2, ponto1, ponto2, eset1_1, eset1_2,eR1, eR2);

        ponto1.setOnClickListener(listener);


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), EditActivity.class);
                Bundle bu = new Bundle();

                String name1 = eName1.getText().toString();
                String name2 = eName2.getText().toString();
                String nameTour = eNametour.getText().toString();



                bu.putString("bu_name1", name1);
                bu.putString("bu_name2", name2);
                bu.putString("bu_nametour", nameTour);

                in.putExtras(bu);
                startActivityForResult(in, reqCode);
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

                MainActivity.games.add(new Game(nametour));
                setResult(RESULT_OK);
                finish();
                /*Intent it = new Intent();
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
                setResult(RESULT_OK, it);
                finish();*/
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

    public View.OnClickListener setResultados(TextView set1, TextView set2, Button ponto1, Button ponto2
            , TextView eset1_1, TextView eset1_2, TextView eR1, TextView eR2){
        return new View.OnClickListener(){

            public void onClick(View v){

                switch (v.getId()){
                    case id.Ponto1:
                        if(valor1==0 || valor1==15) {
                            valor1 += 15;
                            set1.setText(valor1 + "");
                            break;
                        }

                        if(valor1==30){
                            valor1 +=10;
                            set1.setText(valor1+"");
                            break;
                        }


                        if(valor1 == 40) {
                            if (valor1 == valor2) {
                                valor1 += 1;
                                set1.setText("AD");
                                set2.setText("");
                                break;
                            }
                            if (valor2 == 41) {
                                valor1 = 40;
                                valor2 = 40;
                                set1.setText(valor1 + "");
                                set2.setText(valor2 + "");
                                break;
                            }

                            if( r1 < 7 && r2 < 6){

                                r1++;
                                eR1.setText(r1+"");
                                valor1 = 0;
                                valor2 = 0;
                                set1.setText(valor1+"");
                                set2.setText(valor2+"");
                                if((r2_1 == 7 &&  r2_2 <= 5) || (r2_1 <= 5 && r2_2 == 7)){

                                    eR1.setText(r1+"");

                                    if(r1 == 7 && r2 <= 5){
                                        r3_1++;
                                        eset3_1.setText(r3_1+"");
                                        eR1.setText("0");
                                        r1 = 0;
                                        r2 = 0;
                                        if(r3_1 == 7){
                                            Toast.makeText(getApplicationContext(), "Vencedor", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                        break;
                                    }else{

                                    }
                                    break;
                                }
                                if((r1_1 == 7 && r1_2 <= 5) || (r1_1 <= 5 && r1_2 == 7)){
                                    eR1.setText(r1+"");
                                    if(r1 == 7 && r2 <= 5){
                                        r2_1++;
                                        eset2_1.setText(r2_1+"");
                                        eR1.setText("0");
                                        r1 = 0;
                                        r2 = 0;
                                        if(r1_1 == 7 && r2_1 == 7){
                                            Toast.makeText(getApplicationContext(), "Vencedor", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                        break;
                                    }
                                    break;
                                }
                                if(r1 == 7 && r2 <= 5){
                                    r1_1++;
                                    eset1_1.setText(r1_1+"");
                                    eR1.setText("0");
                                    r1 = 0;
                                    r2 = 0;
                                    break;
                                }
                                break;
                            }

                        }
                        if(valor1==41){
                            if(r1 < 7 && r2 < 7){
                                r1++;
                                eset1_1.setText(r1+"");
                                valor1=0;
                                valor2=0;
                            }

                            break;
                        }


                        break;

                }
            }
        };
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == reqCode) { // // if it is the request that I did

            if (resultCode == RESULT_OK) {  // if the result is RESULT_OK


                // gets the Bundle of the response intent
                Bundle b = data.getExtras();

                // gets the data in the Bundle
                String var1 = b.getString("name1");
                String var2 = b.getString("name2");
                String var3 = b.getString("nameTour");


                eName1.setText(var1);
                eName2.setText(var2);
                eNametour.setText(var3);



            }
        }
    }
}