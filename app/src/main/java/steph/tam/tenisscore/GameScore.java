package steph.tam.tenisscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_score);

        Intent iIn = getIntent();
        Bundle b = iIn.getExtras();

        TextView tv_nameTour = (TextView) findViewById(R.id.nameTour);
        TextView tv_P1 = (TextView) findViewById(R.id.tvNameP1);
        TextView tv_P2 = (TextView) findViewById(R.id.tvNameP2);
        TextView tv_data = (TextView) findViewById(R.id.tvDate);

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