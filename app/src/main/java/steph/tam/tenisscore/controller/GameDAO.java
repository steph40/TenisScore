package steph.tam.tenisscore.controller;

import java.util.List;

import steph.tam.tenisscore.games.Game;
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

    interface GetGamesListener{
        void onSuccess(List<Game> games);
        void onError(String message);
    }
    void getAllGames(String token, GetGamesListener listener);

}
