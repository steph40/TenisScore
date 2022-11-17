package steph.tam.tenisscore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Remover extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remover);

        Intent i = getIntent();

        MainActivity.removeGame(i.getExtras().getInt("id"));
        setResult(RESULT_OK);
        finish();
    }
}