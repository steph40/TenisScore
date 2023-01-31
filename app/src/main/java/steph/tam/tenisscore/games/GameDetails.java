package steph.tam.tenisscore.games;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import steph.tam.tenisscore.R;
import steph.tam.tenisscore.controller.GameDAO;
import steph.tam.tenisscore.controller.GameDAOService;

public class GameDetails extends AppCompatActivity {
    TextView tvP1, tvP2, tvNameTour, tvDate, tvSet1_1, tvSet2_1, tvSet3_1, tvSet1_2, tvSet2_2, tvSet3_2;
    Button voltar;
    ImageButton apagar;
    String token;
    GameDAO manager;
    SharedPreferences prefs;
    int id, user_id, game_id, auxAlt;
    final Handler handler = new Handler();
    final int delay = 10000;
    int alt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        manager = new GameDAOService();

        prefs = getSharedPreferences("infoUser", MODE_PRIVATE);
        token = prefs.getString("token", null);

        Intent i = getIntent();
        id = i.getExtras().getInt("id");

        tvP1 = findViewById(R.id.tvNameP1);
        tvP2 = findViewById(R.id.tvNameP2);
        tvNameTour = findViewById(R.id.tvNameTour);
        tvDate = findViewById(R.id.tvDate);
        tvSet1_1 = findViewById(R.id.tvSet1_1);
        tvSet2_1 = findViewById(R.id.tvSet2_1);
        tvSet3_1 = findViewById(R.id.tvSet3_1);
        tvSet1_2 = findViewById(R.id.tvSet1_2);
        tvSet2_2 = findViewById(R.id.tvSet2_2);
        tvSet3_2 = findViewById(R.id.tvSet3_2);
        voltar = findViewById(R.id.button5);
        apagar = findViewById(R.id.delete);


        manager.getGame(token, id, new GameDAO.GetGameListener() {
            @Override
            public void onSuccess(Game game) {
                alt = game.getAlteracao();
                tvNameTour.setText(game.getNameTournament());
                tvDate.setText(game.getDateTournament());
                tvP1.setText(game.getNamePlayer1());
                tvP2.setText(game.getNamePlayer2());
                tvSet1_1.setText(game.getSet1_1() + "");
                tvSet2_1.setText(game.getSet2_1() + "");
                tvSet3_1.setText(game.getSet3_1() + "");
                tvSet1_2.setText(game.getSet1_2() + "");
                tvSet2_2.setText(game.getSet2_2() + "");
                tvSet3_2.setText(game.getSet3_2() + "");

                if (game.getVencedor() == 1) {
                    tvP1.setText(game.getNamePlayer1() + "*");
                    tvP1.setTypeface(Typeface.DEFAULT_BOLD);
                }
                if (game.getVencedor() == 2) {
                    tvP2.setText(game.getNamePlayer2() + "*");
                    tvP2.setTypeface(Typeface.DEFAULT_BOLD);
                }

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "dentre", Toast.LENGTH_SHORT).show();
                        manager.getGame(token, id, new GameDAO.GetGameListener() {
                            @Override
                            public void onSuccess(Game game1) {
                                tvNameTour.setText(game1.getNameTournament());
                                tvDate.setText(game1.getDateTournament());
                                tvP1.setText(game1.getNamePlayer1());
                                tvP2.setText(game1.getNamePlayer2());
                                tvSet1_1.setText(game1.getSet1_1() + "");
                                tvSet2_1.setText(game1.getSet2_1() + "");
                                tvSet3_1.setText(game1.getSet3_1() + "");
                                tvSet1_2.setText(game1.getSet1_2() + "");
                                tvSet2_2.setText(game1.getSet2_2() + "");
                                tvSet3_2.setText(game1.getSet3_2() + "");

                                if (game1.getVencedor() == 1) {
                                    tvP1.setText(game1.getNamePlayer1() + "*");
                                    tvP1.setTypeface(Typeface.DEFAULT_BOLD);
                                }
                                if (game1.getVencedor() == 2) {
                                    tvP2.setText(game1.getNamePlayer2() + "*");
                                    tvP2.setTypeface(Typeface.DEFAULT_BOLD);
                                }

                            }

                            @Override
                            public void onError(String message) {

                            }
                        });
                        handler.postDelayed(this, delay);

                    }
                };
                //Se tiver em live
                if (game.isEstado() == false) {
                    handler.post(runnable);
                }

                manager.getID(token, new GameDAO.GetIDListener() {
                    @Override
                    public void onSuccess(int idUser) {
                        user_id = idUser;
                        manager.getUserID(token, id, new GameDAO.GetUserIDListener() {
                            @Override
                            public void onSuccess(int id) {
                                game_id = id;

                                if (user_id != game_id) {
                                    apagar.setVisibility(View.INVISIBLE);
                                } else {
                                    apagar.setVisibility(View.VISIBLE);
                                }

                            }

                            @Override
                            public void onError(String message) {
                                Toast.makeText(getApplicationContext(), "erro2", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(), "erro1", Toast.LENGTH_SHORT).show();
                    }
                });

                voltar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handler.removeCallbacks(runnable);
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                });

                apagar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(GameDetails.this);

                        // Set the message show for the Alert time
                        builder.setMessage("Deseja remover ?");

                        // Set Alert Title
                        builder.setTitle("Alerta");

                        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                        builder.setCancelable(false);

                        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                        builder.setPositiveButton("Sim", (DialogInterface.OnClickListener) (dialog, which) -> {
                            Toast.makeText(getApplicationContext(), id + "", Toast.LENGTH_SHORT).show();
                            manager.deleteGame(token, id, new GameDAO.DeleteGameListener() {
                                @Override
                                public void onSuccess(String message) {
                                    handler.removeCallbacks(runnable);
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                }

                                @Override
                                public void onError(String message) {

                                }
                            });
                        });

                        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                        builder.setNegativeButton("Não", (DialogInterface.OnClickListener) (dialog, which) -> {
                            // If user click no then dialog box is canceled.
                            dialog.cancel();
                        });

                        // Create the Alert dialog
                        AlertDialog alertDialog = builder.create();
                        // Show the Alert Dialog box
                        alertDialog.show();
                    }
                });
            }

            @Override
            public void onError(String message) {

            }
        });


    }
}