package steph.tam.tenisscore.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.Collections;
import java.util.List;

import steph.tam.tenisscore.MainActivity;
import steph.tam.tenisscore.R;
import steph.tam.tenisscore.games.Game;
import steph.tam.tenisscore.games.Gestao;

public class GameAdapter extends BaseAdapter {
    Context context;
    List<Game> adaptGames;

    public GameAdapter(Context context, List<Game> games) {
        this.context = context;
        this.adaptGames = games;

    }

    public View getView(int position, View convertView, ViewGroup parent) {


        // convertView has the previous View for this position
        View rowView = convertView;

        // we only need to create the view if it does not exist
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item, parent, false);
        }

        // These are the Views inside the ListView item
        TextView tv_tournament = (TextView) rowView.findViewById(R.id.tournament);
        TextView tv_player1 = (TextView) rowView.findViewById(R.id.tv_p1);
        TextView tv_player2 = (TextView) rowView.findViewById(R.id.tv_p2);
        TextView tv_date = (TextView) rowView.findViewById(R.id.tv_data);
        TextView tv_1_1 = (TextView) rowView.findViewById(R.id.textView3);
        TextView tv_1_2 = (TextView) rowView.findViewById(R.id.textView4);
        TextView tv_2_1 = (TextView) rowView.findViewById(R.id.textView12);
        TextView tv_2_2 = (TextView) rowView.findViewById(R.id.textView5);
        TextView tv_3_1 = (TextView) rowView.findViewById(R.id.textView11);
        TextView tv_3_2 = (TextView) rowView.findViewById(R.id.textView10);
        TextView estado = (TextView) rowView.findViewById(R.id.estado);


        deleteItem(rowView, position);


        // obtains the contact for this position
        Game g = adaptGames.get(position);

        // sets the TextView texts
        tv_tournament.setText(g.getNameTournament());
        tv_date.setText(g.getDateTournament());
        tv_player1.setText(g.getNamePlayer1());
        tv_player2.setText(g.getNamePlayer2());
        tv_1_1.setText(g.getSet1_1() + "");
        tv_1_2.setText(g.getSet1_2() + "");
        tv_2_1.setText(g.getSet2_1() + "");
        tv_2_2.setText(g.getSet2_2() + "");
        tv_3_1.setText(g.getSet3_1() + "");
        tv_3_2.setText(g.getSet3_2() + "");



        if(g.isEstado()== true){
            estado.setText("Terminado");
            estado.setTypeface(Typeface.DEFAULT_BOLD);
            if (g.getVencedor() == 1) {
                tv_player1.setText(g.getNamePlayer1() + " * ");
                tv_player1.setTypeface(Typeface.DEFAULT_BOLD);
                tv_player1.setTextSize(16);
            }
            if (g.getVencedor() == 2) {
                tv_player2.setText(g.getNamePlayer2() + " * ");
                tv_player2.setTypeface(Typeface.DEFAULT_BOLD);
                tv_player2.setTextSize(16);
            }
        }

        if(g.isEstado()== false){
            estado.setText("Live");
            estado.setTypeface(Typeface.DEFAULT_BOLD);
            estado.setTextColor(Color.RED);
        }



        // returns the view
        return rowView;
    }

    // These methods must be implemented
    // They may be used to get information about the selected item

    @Override
    public int getCount() {
        return adaptGames.size();
    }

    @Override
    public Object getItem(int position) {
        return adaptGames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void deleteItem(View rowView, int position) {
        ImageButton b = (ImageButton) rowView.findViewById(R.id.remove);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"teste",Toast.LENGTH_SHORT).show();
            }
        });
        /*b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // Set the message show for the Alert time
                builder.setMessage("Deseja remover ?");

                // Set Alert Title
                builder.setTitle("Alerta");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(false);

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Sim", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // When the user click yes button then app will close
                    //Gestao gestao = new Gestao(context.getApplicationContext());
                    //Game game = MainActivity.games1.get(position); //Get ID of game

                    //gestao.deleteGame(game.getId()); //Delete game of database
                    //List<Game> games = gestao.getGamesArray(); //get array of database
                    //updateList(games); //udpate list of Main

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
        });*/
    }

    /**
     * Atualizar a lista que é apresentada no main
     *
     * @param games
     */
    public void updateList(List<Game> games) {
        //Collections.sort(games); //Sort games of database
        adaptGames.clear(); //Delete all of array
        adaptGames.addAll(games); //Add all games of database in array
        notifyDataSetChanged();
    }
}
