package steph.tam.tenisscore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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


        // obtains the contact for this position
        Game g = adaptGames.get(position);

        // sets the TextView texts
        tv_tournament.setText(g.getNameTournament());
        tv_player1.setText(g.getNamePlayer1());
        tv_player2.setText(g.getNamePlayer2());


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
}
