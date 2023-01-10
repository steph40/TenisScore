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
import steph.tam.tenisscore.R;

public class Registar extends AppCompatActivity {

    Button registar;
    Button voltar;
    EditText editUsername;
    EditText editPassword;
    GameDAO manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);
        manager = new GameDAOService();

        registar = findViewById(R.id.button2);
        voltar = findViewById(R.id.button3);
        editUsername = findViewById(R.id.editRUsername);
        editPassword = findViewById(R.id.editRPassword);


        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();
                if (username.trim().isEmpty() == true) {
                    editUsername.setError("Preenchimento obrigatório");
                } else if (password.trim().isEmpty() == true) {
                    editPassword.setError("Preenchimento obrigatório");
                } else {
                    Utilizador auxUser = new Utilizador(username, password);
                    manager.register(auxUser, new GameDAO.RegisterListener() {
                        @Override
                        public void onSuccess(String message) {
                            Intent i = new Intent(getApplicationContext(), Login.class);
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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