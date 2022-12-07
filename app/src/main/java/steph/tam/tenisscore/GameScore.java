package steph.tam.tenisscore;

import static steph.tam.tenisscore.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
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
    TextView tv3;
    TextView tv2;
    int valor1 = 0, valor2 = 0, r1_1 = 0, r1_2 = 0, r2_1 = 0, r2_2 = 0, r3_1 = 0, r3_2 = 0, vencedor1 = 0, vencedor2 = 0, vencedor = 0;
    Game game;
    GameDBAdapter db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_game_score);

        Intent iIn = getIntent();
        game = MainActivity.gameById(iIn.getExtras().getInt("id"));

        //Buttons
        ponto1 = (Button) findViewById(id.Ponto1);
        ponto2 = (Button) findViewById(id.Ponto2);
        fin = (Button) findViewById(id.buttonFin);
        edit = (Button) findViewById(id.buttonEdit);

        //Text View
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
        set1 = (TextView) findViewById(id.tvSet1);
        set2 = (TextView) findViewById(id.tvSet2);
        tv3 = (TextView) findViewById(R.id.textView7);
        tv2 = (TextView) findViewById(R.id.textView8);

        eNametour.setText(game.getNameTournament());
        eDatetour.setText(game.getDateTournament());
        eName1.setText(game.getNamePlayer1());
        eName2.setText(game.getNamePlayer2());
        eset1_1.setText(game.getSet1_1() + "");
        eset1_2.setText(game.getSet1_2() + "");

        View.OnClickListener listener = setResultados();

        ponto1.setOnClickListener(listener);
        ponto2.setOnClickListener(listener);

        /**
         * Ao clicar no botão de editar
         */
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), EditActivity.class);
                in.putExtra("id", game.getId());
                startActivityForResult(in, 1);
            }
        });

        /** Ao clicar no botão de finalizar
         *
         */
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (vencedor == 0) {
                    MainActivity.games.remove(game);
                }
                if (vencedor == 1 || vencedor == 2) {
                    game.setVencedor(vencedor);
                    game.setSet1_1(r1_1);
                    game.setSet1_2(r1_2);
                    game.setSet2_1(r2_1);
                    game.setSet2_2(r2_2);
                    game.setSet3_1(r3_1);
                    game.setSet3_2(r3_2);
                }
                setResult(RESULT_OK);
                finish();
            }
        });

    }

    /** Função para contagem de pontos jogo
     *
     */
    public View.OnClickListener setResultados() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.Ponto1:
                        switch (valor1) {
                            case 0:
                            case 15:
                                valor1 += 15;
                                set1.setText(valor1 + "");
                                break;
                            case 30:
                                valor1 += 10;
                                set1.setText(valor1 + "");
                                break;
                            case 40:
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

                                if ((vencedor1 == 1 && vencedor2 == 2) || vencedor1 == 2 && vencedor2 == 1) { //ir para o set3
                                    r3_1++;
                                    eset3_1.setText(r3_1 + "");
                                    valor1 = 0;
                                    valor2 = 0;
                                    set1.setText(valor1 + "");
                                    set2.setText(valor2 + "");
                                    if (r3_1 >= 6 && r3_2 <= r3_1 - 2) {
                                        vencedor = 1;
                                        Toast.makeText(getApplicationContext(), "Vencedor do Jogo", Toast.LENGTH_SHORT).show();
                                        gameFinished();
                                    }
                                    break;
                                }
                                if (vencedor1 == 1 || vencedor1 == 2) { //ir para o set2
                                    r2_1++;
                                    eset2_1.setText(r2_1 + "");
                                    valor1 = 0;
                                    valor2 = 0;
                                    set1.setText(valor1 + "");
                                    set2.setText(valor2 + "");
                                    if (vencedor1 == 1 && r2_1 >= 6 && r2_2 <= r2_1 - 2) {
                                        vencedor = 1;
                                        Toast.makeText(getApplicationContext(), "Vencedor do jogo", Toast.LENGTH_SHORT).show();
                                        gameFinished();
                                        break;
                                    }
                                    if (vencedor1 == 2 && r2_1 >= 6 && r2_2 <= r2_1 - 2) {
                                        vencedor2 = 1;
                                        winSet2();
                                        break;
                                    }
                                }
                                if (vencedor1 == 0) { //ir para o set1
                                    r1_1++;
                                    eset1_1.setText(r1_1 + "");
                                    valor1 = 0;
                                    valor2 = 0;
                                    set1.setText(valor1 + "");
                                    set2.setText(valor2 + "");
                                    if (r1_1 >= 6 && r1_2 <= r1_1 - 2) {
                                        eset1_1.setText(r1_1 + "");
                                        valor1 = 0;
                                        valor2 = 0;
                                        set1.setText(valor1 + "");
                                        set2.setText(valor2 + "");
                                        vencedor1 = 1;
                                        winSet1();
                                        break;
                                    }
                                }
                                break;
                            case 41:
                                if ((vencedor1 == 1 && vencedor2 == 2) || vencedor1 == 2 && vencedor2 == 1) { //ir para o set3
                                    r3_1++;
                                    eset3_1.setText(r3_1 + "");
                                    valor1 = 0;
                                    valor2 = 0;
                                    set1.setText(valor1 + "");
                                    set2.setText(valor2 + "");
                                    if (r3_1 >= 6 && r3_2 <= r3_1 - 2) {
                                        vencedor = 1;
                                        Toast.makeText(getApplicationContext(), "Vencedor do Jogo", Toast.LENGTH_SHORT).show();
                                        gameFinished();
                                    }
                                    break;
                                }
                                if (vencedor1 == 1 || vencedor1 == 2) { //ir para o set2
                                    r2_1++;
                                    eset2_1.setText(r2_1 + "");
                                    valor1 = 0;
                                    valor2 = 0;
                                    set1.setText(valor1 + "");
                                    set2.setText(valor2 + "");
                                    if (vencedor1 == 1 && r2_1 >= 6 && r2_2 <= r2_1 - 2) {
                                        vencedor = 1;
                                        Toast.makeText(getApplicationContext(), "Vencedor do jogo", Toast.LENGTH_SHORT).show();
                                        gameFinished();
                                        break;
                                    }
                                    if (vencedor1 == 2 && r2_1 >= 6 && r2_2 <= r2_1 - 2) {
                                        vencedor2 = 1;
                                        winSet2();
                                        break;
                                    }
                                }
                                if (vencedor1 == 0) { //ir para o set1
                                    r1_1++;
                                    eset1_1.setText(r1_1 + "");
                                    valor1 = 0;
                                    valor2 = 0;
                                    set1.setText(valor1 + "");
                                    set2.setText(valor2 + "");
                                    if (r1_1 >= 6 && r1_2 <= r1_1 - 2) {
                                        eset1_1.setText(r1_1 + "");
                                        valor1 = 0;
                                        valor2 = 0;
                                        set1.setText(valor1 + "");
                                        set2.setText(valor2 + "");
                                        vencedor1 = 1;
                                        winSet1();
                                        break;
                                    }
                                }
                        }
                        break;
                    case id.Ponto2:
                        switch (valor2) {
                            case 0:
                            case 15:
                                valor2 += 15;
                                set2.setText(valor2 + "");
                                break;
                            case 30:
                                valor2 += 10;
                                set2.setText(valor2 + "");
                                break;
                            case 40:
                                if (valor2 == valor1) {
                                    valor2 += 1;
                                    set2.setText("AD");
                                    set1.setText("");
                                    break;
                                }
                                if (valor1 == 41) {
                                    valor1 = 40;
                                    valor2 = 40;
                                    set1.setText(valor1 + "");
                                    set2.setText(valor2 + "");
                                    break;
                                }
                                if ((vencedor1 == 1 && vencedor2 == 2) || vencedor1 == 2 && vencedor2 == 1) { //ir para o set3
                                    r3_2++;
                                    eset3_2.setText(r3_2 + "");
                                    valor1 = 0;
                                    valor2 = 0;
                                    set1.setText(valor1 + "");
                                    set2.setText(valor2 + "");
                                    if (r3_2 >= 6 && r3_1 <= r3_2 - 2) {
                                        vencedor = 2;
                                        Toast.makeText(getApplicationContext(), "Vencedor do Jogo", Toast.LENGTH_SHORT).show();
                                        gameFinished();
                                    }
                                    break;
                                }
                                if (vencedor1 == 1 || vencedor1 == 2) { //ir para o set2
                                    r2_2++;
                                    eset2_2.setText(r2_2 + "");
                                    valor1 = 0;
                                    valor2 = 0;
                                    set1.setText(valor1 + "");
                                    set2.setText(valor2 + "");
                                    if (vencedor1 == 2 && r2_2 >= 6 && r2_1 <= r2_2 - 2) {
                                        vencedor = 2;
                                        Toast.makeText(getApplicationContext(), "Vencedor do jogo", Toast.LENGTH_SHORT).show();
                                        gameFinished();
                                        break;
                                    }
                                    if (vencedor1 == 1 && r2_2 >= 6 && r2_1 <= r2_2 - 2) {
                                        vencedor2 = 2;
                                        winSet2();
                                        break;
                                    }
                                }
                                if (vencedor1 == 0) { //ir para o set1
                                    r1_2++;
                                    eset1_2.setText(r1_2 + "");
                                    valor1 = 0;
                                    valor2 = 0;
                                    set1.setText(valor1 + "");
                                    set2.setText(valor2 + "");
                                    if (r1_2 >= 6 && r1_1 <= r1_2 - 2) {//ir para o set1
                                        eset1_2.setText(r1_2 + "");
                                        valor1 = 0;
                                        valor2 = 0;
                                        set1.setText(valor1 + "");
                                        set2.setText(valor2 + "");
                                        vencedor1 = 2;
                                        winSet1();
                                        break;
                                    }
                                }
                                break;
                            case 41:
                                if ((vencedor1 == 1 && vencedor2 == 2) || vencedor1 == 2 && vencedor2 == 1) { //ir para o set3
                                    r3_2++;
                                    eset3_2.setText(r3_2 + "");
                                    valor1 = 0;
                                    valor2 = 0;
                                    set1.setText(valor1 + "");
                                    set2.setText(valor2 + "");
                                    if (r3_2 >= 6 && r3_1 <= r3_2 - 2) {
                                        vencedor = 2;
                                        Toast.makeText(getApplicationContext(), "Vencedor do Jogo", Toast.LENGTH_SHORT).show();
                                        gameFinished();
                                    }
                                    break;
                                }
                                if (vencedor1 == 1 || vencedor1 == 2) { //ir para o set2
                                    r2_2++;
                                    eset2_2.setText(r2_2 + "");
                                    valor1 = 0;
                                    valor2 = 0;
                                    set1.setText(valor1 + "");
                                    set2.setText(valor2 + "");
                                    if (vencedor1 == 2 && r2_2 >= 6 && r2_1 <= r2_2 - 2) {
                                        vencedor = 2;
                                        Toast.makeText(getApplicationContext(), "Vencedor do jogo", Toast.LENGTH_SHORT).show();
                                        gameFinished();
                                        break;
                                    }
                                    if (vencedor1 == 1 && r2_2 >= 6 && r2_1 <= r2_2 - 2) {
                                        vencedor2 = 2;
                                        winSet2();
                                        break;
                                    }
                                }
                                if (vencedor1 == 0) { //ir para o set1
                                    r1_2++;
                                    eset1_2.setText(r1_2 + "");
                                    valor1 = 0;
                                    valor2 = 0;
                                    set1.setText(valor1 + "");
                                    set2.setText(valor2 + "");
                                    if (r1_2 >= 6 && r1_1 <= r1_2 - 2) {//ir para o set1
                                        eset1_2.setText(r1_2 + "");
                                        valor1 = 0;
                                        valor2 = 0;
                                        set1.setText(valor1 + "");
                                        set2.setText(valor2 + "");
                                        vencedor1 = 2;
                                        winSet1();
                                        break;
                                    }
                                }
                                break;
                        }
                }
            }
        };
    }

    /**
     * Função para os botões de "ponto" ficarem invisiveis
     */
    public void gameFinished() {
        ponto1.setVisibility(View.INVISIBLE);
        ponto2.setVisibility(View.INVISIBLE);
    }

    /**
     * Função para as TextViews do set 3 aparecerem
     */
    public void winSet2() {
        eset3_1.setVisibility(View.VISIBLE);
        eset3_2.setVisibility(View.VISIBLE);
        tv3.setVisibility(View.VISIBLE);
    }

    /**
     * Função para as TextViews do set 2 aparecerem
     */
    public void winSet1() {
        eset2_1.setVisibility(View.VISIBLE);
        eset2_2.setVisibility(View.VISIBLE);
        tv2.setVisibility(View.VISIBLE);
    }

    /**Função da espera do resultado da EditActivity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // // if it is the request that I did
        if (resultCode == RESULT_OK && requestCode == 1) {  // if the result is RESULT_OK
            eNametour.setText(game.getNameTournament());
            eDatetour.setText(game.getDateTournament());
            eName1.setText(game.getNamePlayer1());
            eName2.setText(game.getNamePlayer2());
        }
    }
}