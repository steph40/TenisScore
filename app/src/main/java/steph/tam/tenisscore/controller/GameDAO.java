package steph.tam.tenisscore.controller;

import steph.tam.tenisscore.utilizadores.Token;
import steph.tam.tenisscore.utilizadores.Utilizador;

public interface GameDAO {

    interface RegisterListener {
        void onSuccess(String message);
        void onError(String message);
    }
    void register(Utilizador user, RegisterListener listener);

    interface  LoginListener{
        void onSuccess(Token token);
        void onError(String message);
    }
    void login(Utilizador user , LoginListener listener);

}
