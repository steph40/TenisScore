package steph.tam.tenisscore.utilizadores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import steph.tam.tenisscore.controller.GameDAO;
import steph.tam.tenisscore.controller.GameDAOService;
import steph.tam.tenisscore.MainActivity;
import steph.tam.tenisscore.R;


public class Login extends AppCompatActivity {

    Button login;
    Button registar;
    GameDAO manager;
    EditText editNome;
    EditText editPassword;

    //GameInterface gameService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        manager = new GameDAOService();
        login = findViewById(R.id.button2);
        registar = findViewById(R.id.button3);
        editNome = findViewById(R.id.editTUsername);
        editPassword = findViewById(R.id.editTPassword);

        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Registar.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editNome.getText().toString();
                String password = editPassword.getText().toString();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);

                Utilizador auxUser = new Utilizador(username,password);
                manager.login(auxUser, new GameDAO.LoginListener() {
                    @Override
                    public void onSuccess(Token token) {

                        //Toast.makeText(getApplicationContext(),token+"",Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }

                    @Override
                    public void onError(String message) {
                    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }


}