package steph.tam.tenisscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GameScore extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_score);

        Intent iIn = getIntent();
        Bundle b = iIn.getExtras();

        TextView tv = (TextView) findViewById(R.id.nameTour);
        String var1 = b.getString("tourName");
        tv.setText(var1);
    }
}