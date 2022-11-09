package steph.tam.tenisscore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class GameAdapter extends BaseAdapter {
    Context context;
    List<Game> adaptGames;

    public GameAdapter(Context context, List<Game> adaptGames) {
        this.context = context;
        this.adaptGames = adaptGames;
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
        TextView textName = (TextView) rowView.findViewById(R.id.set);
        //TextView textEmail = (TextView) rowView.findViewById(R.id.tv_p1);
        //TextView textEmail1 = (TextView) rowView.findViewById(R.id.tv_p2);


        // obtains the contact for this position
        Game g = adaptGames.get(position);

        // sets the TextView texts
        textName.setText(g.getNameTournament());
        //textEmail.setText(g.getNamePlayer1());

        // sets the icon for this contact
        /*if (c.getType() == Contact.FAMILY) {
            imageType.setImageResource(R.drawable.family);
        } else if (c.getType() == Contact.WORK) {
            imageType.setImageResource(R.drawable.work);
        } else if (c.getType() == Contact.OTHER) {
            imageType.setImageResource(R.drawable.other);
        }*/

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
