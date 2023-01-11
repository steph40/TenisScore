package steph.tam.tenisscore.utilizadores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import steph.tam.tenisscore.R;

public class InfoUser extends AppCompatActivity {
    Button logout;
    Button voltar;
    SharedPreferences prefs;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

        logout = findViewById(R.id.logOut);
        voltar = findViewById(R.id.button4);
        username = findViewById(R.id.username_tv);

        prefs = getSharedPreferences("infoUser", MODE_PRIVATE);

        username.setText(prefs.getString("nomeUser", null));


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.commit();
                Intent i = new Intent(getApplicationContext(), Login.class);
                finish();
                startActivity(i);
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}