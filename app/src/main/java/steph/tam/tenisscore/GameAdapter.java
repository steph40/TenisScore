package steph.tam.tenisscore;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.Collections;
import java.util.List;

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

        deleteItem(rowView, position);

        // obtains the contact for this position
        Game g = adaptGames.get(position);

        // sets the TextView texts
        tv_tournament.setText(g.getNameTournament());
        tv_player1.setText(g.getNamePlayer1());
        tv_player2.setText(g.getNamePlayer2());
        tv_date.setText(g.getDateTournament());

        //if(g.getVencedor() == 1){verde o jogador 2}
        //if(g.getVencedor() == 2){ verde o jogador 2}

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

    private void deleteItem(View rowView, int position){
        ImageButton b = (ImageButton) rowView.findViewById(R.id.remove);

        b.setOnClickListener(new View.OnClickListener() {
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
                    MainActivity.games.remove(position);
                    notifyDataSetChanged();

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
}
