package steph.tam.tenisscore.games;

import static steph.tam.tenisscore.R.*;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import steph.tam.tenisscore.R;
import steph.tam.tenisscore.controller.GameDAO;
import steph.tam.tenisscore.controller.GameDAOService;

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
    TextView set1;
    TextView set2;
    TextView tv3;
    TextView tv2;
    int valor1 = 0, valor2 = 0, value1 = 0, value2 = 0, r1_1 = 0, r1_2 = 0, r2_1 = 0, r2_2 = 0, r3_1 = 0, r3_2 = 0, vencedor1 = 0, vencedor2 = 0, vencedor = 0;
    Game game;
    Game game2;

    int aYear, aMonth, aDay;
    String token;
    GameDAO manager;
    SharedPreferences prefs;
    int id , alteracao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_game_score);

        Intent iIn = getIntent();
        id = iIn.getExtras().getInt("id");

        manager = new GameDAOService();
        alteracao=0;
        prefs = getSharedPreferences("infoUser", MODE_PRIVATE);
        token = prefs.getString("token", null);
        manager.getGame(token, id, new GameDAO.GetGameListener() {
            @Override
            public void onSuccess(Game game) {
                aYear = iIn.getExtras().getInt("ano");
                aMonth = iIn.getExtras().getInt("mes");
                aDay = iIn.getExtras().getInt("dia");

                //Buttons
                ponto1 = (Button) findViewById(R.id.Ponto1);
                ponto2 = (Button) findViewById(R.id.Ponto2);
                fin = (Button) findViewById(R.id.buttonFin);
                edit = (Button) findViewById(R.id.buttonEdit);

                fin.setVisibility(View.INVISIBLE);

                //Text View
                eName1 = (TextView) findViewById(R.id.tvNameP1);
                eName2 = (TextView) findViewById(R.id.tvNameP2);
                eNametour = (TextView) findViewById(R.id.tvNameTour);
                eDatetour = (TextView) findViewById(R.id.tvDate);
                eset1_1 = (TextView) findViewById(R.id.tvSet1_1);
                eset2_1 = (TextView) findViewById(R.id.tvSet2_1);
                eset3_1 = (TextView) findViewById(R.id.tvSet3_1);
                eset1_2 = (TextView) findViewById(R.id.tvSet1_2);
                eset2_2 = (TextView) findViewById(R.id.tvSet2_2);
                eset3_2 = (TextView) findViewById(R.id.tvSet3_2);
                set1 = (TextView) findViewById(R.id.tvSet1);
                set2 = (TextView) findViewById(R.id.tvSet2);
                tv3 = (TextView) findViewById(R.id.textView7);
                tv2 = (TextView) findViewById(R.id.textView8);

                eNametour.setText(game.getNameTournament());
                eDatetour.setText(game.getDateTournament());
                eName1.setText(game.getNamePlayer1());
                eName2.setText(game.getNamePlayer2());
                eset1_1.setText(game.getSet1_1() + "");
                eset1_2.setText(game.getSet1_2() + "");

                View.OnClickListener listener = setResultados(game);

                ponto1.setOnClickListener(listener);
                ponto2.setOnClickListener(listener);

                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(getApplicationContext(), EditActivity.class);
                        in.putExtra("id", game.getId());
                        in.putExtra("ano", aYear);
                        in.putExtra("mes", aMonth);
                        in.putExtra("dia", aDay);
                        startActivityForResult(in, 1);
                    }
                });

                fin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });


    }

    /**
     * Função para contagem de pontos jogo
     */
    public View.OnClickListener setResultados(Game game1) {
        alteracao = game1.getAlteracao();
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.Ponto1:
                        switch (valor1) {
                            case 0:
                                if (r1_1 == 6 && r1_2 == 6) { //Tie Break of set1
                                    value1++;
                                    set1.setText(value1 + "");
                                    if (value1 >= 7 && value2 <= value1 - 2) {
                                        r1_1++;
                                        eset1_1.setText(r1_1 + "");
                                        valor1 = 0;
                                        valor2 = 0;
                                        value1 = 0;
                                        value2 = 0;
                                        set1.setText(valor1 + "");
                                        set2.setText(valor2 + "");
                                        vencedor1 = 1;
                                        winSet1();
                                        alteracao++;
                                        game1.setAlteracao(alteracao);
                                    }
                                    break;
                                }
                                if (r2_1 == 6 && r2_2 == 6) { //Tie Break of set2
                                    value1++;
                                    set1.setText(value1 + "");
                                    if (value1 >= 7 && value2 <= value1 - 2) {
                                        r2_1++;
                                        eset2_1.setText(r2_1 + "");
                                        valor1 = 0;
                                        valor2 = 0;
                                        value1 = 0;
                                        value2 = 0;
                                        set1.setText(valor1 + "");
                                        set2.setText(valor2 + "");
                                        alteracao++;
                                        game1.setAlteracao(alteracao);
                                        if (vencedor1 == 1) { //Check if this player won set1
                                            vencedor = 1;
                                            vencedor(vencedor);
                                            gameFinished();
                                            break;
                                        }
                                        vencedor2 = 1;
                                        winSet2();
                                    }
                                    break;
                                }
                                if (r3_1 == 6 && r3_2 == 6) { //Tie Break of set3
                                    value1++;
                                    set1.setText(value1 + "");
                                    if (value1 >= 7 && value2 <= value1 - 2) {
                                        r3_1++;
                                        eset3_1.setText(r3_1 + "");
                                        valor1 = 0;
                                        valor2 = 0;
                                        value1 = 0;
                                        value2 = 0;
                                        set1.setText(valor1 + "");
                                        set2.setText(valor2 + "");
                                        vencedor = 1;
                                        alteracao++;
                                        game1.setAlteracao(alteracao);
                                        vencedor(vencedor);
                                        gameFinished();
                                    }
                                    break;
                                }
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
                                    alteracao++;
                                    game1.setAlteracao(alteracao);
                                    if (r3_1 >= 6 && r3_2 <= r3_1 - 2) {
                                        vencedor = 1;
                                        vencedor(vencedor);
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
                                    alteracao++;
                                    game1.setAlteracao(alteracao);
                                    if (vencedor1 == 1 && r2_1 >= 6 && r2_2 <= r2_1 - 2) {
                                        vencedor = 1;
                                        vencedor(vencedor);
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
                                    }
                                    alteracao++;
                                    game1.setAlteracao(alteracao);
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
                                    alteracao++;
                                    alteracao++;
                                    game1.setAlteracao(alteracao);
                                    if (r3_1 >= 6 && r3_2 <= r3_1 - 2) {
                                        vencedor = 1;
                                        vencedor(vencedor);
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
                                    alteracao++;
                                    game1.setAlteracao(alteracao);
                                    if (vencedor1 == 1 && r2_1 >= 6 && r2_2 <= r2_1 - 2) {
                                        vencedor = 1;
                                        vencedor(vencedor);
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
                                    }
                                    alteracao++;
                                    game1.setAlteracao(alteracao);
                                }
                        }
                        if (vencedor == 1 || vencedor == 2) {
                            game1.setVencedor(vencedor);
                            game1.setEstado(true);
                        }
                        game1.setSet1_1(r1_1);
                        game1.setSet2_1(r2_1);
                        game1.setSet3_1(r3_1);

                        manager.editGameScore(token, game1, new GameDAO.GameScoreEditListener() {
                            @Override
                            public void onSuccess(String message) {

                            }

                            @Override
                            public void onError(String message) {
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (vencedor == 1 || vencedor == 2) {
                            game1.setVencedor(vencedor);
                            game1.setEstado(true);
                        }

                        game1.setSet1_2(r1_2);
                        game1.setSet2_2(r2_2);
                        game1.setSet3_2(r3_2);
                        game1.setAlteracao(alteracao);
                        manager.editGameScore(token, game1, new GameDAO.GameScoreEditListener() {
                            @Override
                            public void onSuccess(String message) {

                            }

                            @Override
                            public void onError(String message) {
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                    case R.id.Ponto2:
                        switch (valor2) {
                            case 0:
                                if (r1_1 == 6 && r1_2 == 6) { //Tie Break of set1
                                    value2++;
                                    set2.setText(value2 + "");
                                    if (value2 >= 7 && value1 <= value2 - 2) {
                                        r1_2++;
                                        eset1_2.setText(r1_2 + "");
                                        valor1 = 0;
                                        valor2 = 0;
                                        value1 = 0;
                                        value2 = 0;
                                        set1.setText(valor1 + "");
                                        set2.setText(valor2 + "");
                                        vencedor1 = 2;
                                        winSet1();
                                        alteracao++;
                                        game1.setAlteracao(alteracao);
                                    }
                                    break;
                                }
                                if (r2_1 == 6 && r2_2 == 6) { //Tie Break of set2
                                    value2++;
                                    set2.setText(value2 + "");
                                    if (value2 >= 7 && value1 <= value2 - 2) {
                                        r2_2++;
                                        eset2_2.setText(r2_2 + "");
                                        valor1 = 0;
                                        valor2 = 0;
                                        value1 = 0;
                                        value2 = 0;
                                        set1.setText(valor1 + "");
                                        set2.setText(valor2 + "");
                                        alteracao++;
                                        game1.setAlteracao(alteracao);
                                        if (vencedor1 == 2) { //Check if this player won set1
                                            vencedor = 2;
                                            vencedor(vencedor);
                                            gameFinished();
                                            break;
                                        }
                                        vencedor2 = 2;
                                        winSet2();
                                    }
                                    break;
                                }
                                if (r3_1 == 6 && r3_2 == 6) { //Tie Break of set3
                                    value2++;
                                    set2.setText(value2 + "");
                                    if (value2 >= 7 && value1 <= value2 - 2) {
                                        r3_2++;
                                        eset3_2.setText(r3_2 + "");
                                        valor1 = 0;
                                        valor2 = 0;
                                        value1 = 0;
                                        value2 = 0;
                                        set1.setText(valor1 + "");
                                        set2.setText(valor2 + "");
                                        vencedor = 2;
                                        vencedor(vencedor);
                                        gameFinished();
                                        alteracao++;
                                        game1.setAlteracao(alteracao);
                                    }
                                    break;
                                }
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
                                    alteracao++;
                                    game1.setAlteracao(alteracao);
                                    if (r3_2 == 6 && r3_1 <= r3_2 - 2) {
                                        vencedor = 2;
                                        vencedor(vencedor);
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
                                    alteracao++;
                                    game1.setAlteracao(alteracao);
                                    if (vencedor1 == 2 && r2_2 >= 6 && r2_1 <= r2_2 - 2) {
                                        vencedor = 2;
                                        vencedor(vencedor);
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
                                    }
                                    alteracao++;
                                    game1.setAlteracao(alteracao);
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
                                    alteracao++;
                                    game1.setAlteracao(alteracao);
                                    if (r3_2 >= 6 && r3_1 <= r3_2 - 2) {
                                        vencedor = 2;
                                        vencedor(vencedor);
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
                                    alteracao++;
                                    game1.setAlteracao(alteracao);
                                    if (vencedor1 == 2 && r2_2 >= 6 && r2_1 <= r2_2 - 2) {
                                        vencedor = 2;
                                        vencedor(vencedor);
                                        Toast.makeText(getApplicationContext(), "Vencedor do Jogo", Toast.LENGTH_SHORT).show();
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
                                    }
                                    alteracao++;
                                    game1.setAlteracao(alteracao);
                                }
                                break;
                        }
                        if (vencedor == 1 || vencedor == 2) {
                            game1.setVencedor(vencedor);
                            game1.setEstado(true);
                        }
                        game1.setSet1_2(r1_2);
                        game1.setSet2_2(r2_2);
                        game1.setSet3_2(r3_2);
                        game1.setAlteracao(alteracao);
                        manager.editGameScore(token, game1, new GameDAO.GameScoreEditListener() {
                            @Override
                            public void onSuccess(String message) {

                            }

                            @Override
                            public void onError(String message) {
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
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
        fin.setVisibility(View.VISIBLE);
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

    /**
     * Função para distinguir o vencedor
     *
     * @param vencedor
     */
    public void vencedor(int vencedor) {
        if (vencedor == 1) {
            eName1.setTypeface(Typeface.DEFAULT_BOLD);
            eName1.setTextSize(16);
        }
        if (vencedor == 2) {
            eName2.setTypeface(Typeface.DEFAULT_BOLD);
            eName2.setTextSize(16);
        }
    }

    /**
     * Função da espera do resultado da EditActivity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // // if it is the request that I did
        if (resultCode == RESULT_OK && requestCode == 1) {  // if the result is RESULT_OK
            manager.getGame(token, id, new GameDAO.GetGameListener() {
                @Override
                public void onSuccess(Game game) {
                    eNametour.setText(game.getNameTournament());
                    eDatetour.setText(game.getDateTournament());
                    eName1.setText(game.getNamePlayer1());
                    eName2.setText(game.getNamePlayer2());

                    aYear = data.getExtras().getInt("ano");
                    aMonth = data.getExtras().getInt("mes");
                    aDay = data.getExtras().getInt("dia");
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Guardar o estado da Activity
     *
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        //Save set's points
        outState.putInt("set1_1", r1_1);
        outState.putInt("set1_2", r1_2);
        outState.putInt("set2_1", r2_1);
        outState.putInt("set2_2", r2_2);
        outState.putInt("set3_1", r3_1);
        outState.putInt("set3_2", r3_2);
        //save actual game's points
        outState.putInt("R1", valor1);
        outState.putInt("R2", valor2);
        outState.putInt("L1", value1);
        outState.putInt("L2", value2);
        //Save set's winners
        outState.putInt("vencedor1", vencedor1);
        outState.putInt("vencedor2", vencedor2);
        outState.putInt("vencedor", vencedor);

        super.onSaveInstanceState(outState);
    }

    /**
     * Restaurar o estado da Activity
     *
     * @param outState
     */
    public void onRestoreInstanceState(Bundle outState) {
        super.onRestoreInstanceState(outState);
        //get set's points
        r1_1 = outState.getInt("set1_1");
        r1_2 = outState.getInt("set1_2");
        r2_1 = outState.getInt("set2_1");
        r2_2 = outState.getInt("set2_2");
        r3_1 = outState.getInt("set3_1");
        r3_2 = outState.getInt("set3_2");
        //get actual game's points
        valor1 = outState.getInt("R1");
        valor2 = outState.getInt("R2");
        value1 = outState.getInt("L1");
        value2 = outState.getInt("L2");
        //get set's winners
        vencedor1 = outState.getInt("vencedor1");
        vencedor2 = outState.getInt("vencedor2");
        vencedor = outState.getInt("vencedor");

        set1.setText(valor1 + "");
        set2.setText(valor2 + "");

        //check if game is in Tie Break
        if (r1_1 == 6 && r1_2 == 6 || r2_1 == 6 && r2_2 == 6 || r3_1 == 6 && r3_2 == 6) {
            set1.setText(value1 + "");
            set2.setText(value2 + "");
        }

        //Check game is in Advantage for player1
        if (valor1 == 41) {
            set1.setText("AD");
            set2.setText("");
        }

        //Check game is in Advantage for player2
        if (valor2 == 41) {
            set1.setText("");
            set2.setText("AD");
        }
        eset1_1.setText(r1_1 + "");
        eset1_2.setText(r1_2 + "");

        //check if set1 have a winner
        if (vencedor1 == 1 || vencedor1 == 2) {
            winSet1();
            eset2_1.setText(r2_1 + "");
            eset2_2.setText(r2_2 + "");
        }

        //check if set2 have a winner
        if (vencedor1 == 1 && vencedor2 == 2 || vencedor1 == 2 && vencedor2 == 1) {
            winSet2();
            eset3_1.setText(r3_1 + "");
            eset3_2.setText(r3_2 + "");
        }

        //check if game have a winner
        if (vencedor != 0) {
            vencedor(vencedor);
            gameFinished();
        }

    }

}