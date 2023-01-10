package steph.tam.tenisscore.utilizadores;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences prefs;

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

        //Shared Preferences
        prefs=getSharedPreferences("infoUser",MODE_PRIVATE);
        String token = prefs.getString("token",null);

        if(token!= null){
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            String username = prefs.getString("nomeUser", null);
            String password = prefs.getString("passwordUser",null);
            Utilizador auxUser = new Utilizador(username,password);
            manager.login(auxUser, new GameDAO.LoginListener() {
                @Override
                public void onSuccess(Token token) {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("nomeUser",username);
                    editor.putString("passwordUser",password);
                    editor.putString("token",token.getToken());
                    editor.commit();
                    finish();
                    startActivity(i);
                }

                @Override
                public void onError(String message) {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                }
            });
        }

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

                if (username.trim().isEmpty() == true) {
                    editNome.setError("Preenchimento obrigatório");
                } else if (password.trim().isEmpty() == true) {
                    editPassword.setError("Preenchimento obrigatório");
                } else {
                    Utilizador auxUser = new Utilizador(username, password);
                    manager.login(auxUser, new GameDAO.LoginListener() {
                        @Override
                        public void onSuccess(Token token) {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("nomeUser",username);
                            editor.putString("passwordUser",password);
                            editor.putString("token",token.getToken());
                            editor.commit();
                            finish();
                            startActivity(i);
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }


}