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

    interface LoginListener {
        void onSuccess(Token token);

        void onError(String message);
    }

    void login(Utilizador user, LoginListener listener);

    interface GetGamesListener {
        void onSuccess(List<Game> games);

        void onError(String message);
    }

    void getAllGames(String token, GetGamesListener listener);

    interface AddGameListener {
        void onSuccess(String message);

        void onError(String message);
    }

    void addGames(String token, Game game, AddGameListener listener);

    interface GetLastIdListener {
        void onSuccess(int id);

        void onError(String message);
    }

    void getLastId(String token, GetLastIdListener listener);

    interface GetGameListener {
        void onSuccess(Game game);

        void onError(String message);
    }

    void getGame(String token, int id, GetGameListener listener);

    interface GameEditListener {
        void onSuccess(String message);

        void onError(String message);
    }

    void editGame(String token, Game gameEdit, GameEditListener listener);

    interface GameScoreEditListener {
        void onSuccess(String message);

        void onError(String message);
    }

    void editGameScore(String token, Game gameScore, GameScoreEditListener listener);

    interface DeleteGameListener{
        void onSuccess(String message);

        void onError(String message);
    }

    void deleteGame(String token ,int id , DeleteGameListener listener);


}
