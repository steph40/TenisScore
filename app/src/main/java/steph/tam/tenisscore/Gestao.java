package steph.tam.tenisscore;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Gestao {

    GameDBAdapter db;

    public Gestao(Context ctx) {
        db = new GameDBAdapter(ctx);
    }

    /**
     * Insert new Game in database
     *
     * @param nameTournament nome do torneio
     * @param date           data do jogo
     * @param nameP1         nome player1
     * @param nameP2         nome player2
     * @param set1_1         set1 do player1
     * @param set2_1         set2 do player1
     * @param set3_1         set3 do player1
     * @param set1_2         set1 do player2
     * @param set2_2         set2 do player2
     * @param set3_2         set3 do player2
     * @param vencedor       vencedor do jogo
     * @return return true if insert with success and false if have an error
     */
    public boolean insertGame(String nameTournament, String date, String nameP1, String nameP2, int set1_1, int set2_1, int set3_1
            , int set1_2, int set2_2, int set3_2, int vencedor) {
        db.open();
        boolean resultado = db.insertGame(nameTournament, date, nameP1, nameP2, set1_1, set2_1, set3_1, set1_2, set2_2, set3_2, vencedor);
        db.close();
        return resultado;
    }

    /**
     * Update a game in database
     *
     * @param id       id do jogo
     * @param set1_1   set1 do player1
     * @param set2_1   set2 do player1
     * @param set3_1   set3 do player1
     * @param set1_2   set1 do player2
     * @param set2_2   set2 do player2
     * @param set3_2   set3 do player2
     * @param vencedor vencedor do jogo
     */
    public void updateGameScore(int id, int set1_1, int set2_1, int set3_1, int set1_2, int set2_2, int set3_2, int vencedor) {
        db.open();
        if (db.updateGameScore(id, set1_1, set2_1, set3_1, set1_2, set2_2, set3_2, vencedor)) {
            db.close();
        }
        db.close();
    }

    /**
     * update a game in database
     *
     * @param id             id do jogo
     * @param nameTournament nome do torneio
     * @param date           data do jogo
     * @param nameP1         nome player1
     * @param nameP2         nome player2
     */
    public void updateGameForm(int id, String nameTournament, String date, String nameP1, String nameP2) {
        db.open();
        if (db.updateGameForm(id, nameTournament, date, nameP1, nameP2) == true) {
            db.close();
        }
        db.close();
    }

    /**
     * Delete a game in database
     *
     * @param id id do jogo
     * @return return true if delete with success and false if have an error
     */
    public boolean deleteGame(int id) {
        db.open();
        if (db.deleteGame(id) == true) {
            db.close();
            return true;
        }
        db.close();
        return false;

    }

    /**
     * Get last id of the database
     *
     * @return return id
     */
    public int lastId() {
        int id = -1;
        db.open();
        Cursor curRes = db.getLastId();
        curRes.moveToFirst();
        while (!curRes.isAfterLast()) {
            id = curRes.getInt(0);
            curRes.moveToNext();
        }
        db.close();
        return id;
    }

    /**
     * Get a game of the database
     *
     * @param id id do jogo
     * @return return a game
     */
    public Game getGame(int id) {
        db.open();
        Cursor curRes = db.getGame(id);
        if (curRes != null) {
            curRes.moveToFirst();
        } else {
            return null;
        }
        if (curRes.getCount() > 0) {
            Game game = new Game(id, curRes.getString(0), curRes.getString(1), curRes.getString(2), curRes.getString(3)
                    , curRes.getInt(4), curRes.getInt(5), curRes.getInt(6), curRes.getInt(7), curRes.getInt(8), curRes.getInt(9)
                    , curRes.getInt(10));
            db.close();
            return game;
        }
        return null;
    }

    /**
     * Get all games of the database
     *
     * @return return a array with all games
     */
    public ArrayList<Game> getGamesArray() {
        ArrayList<Game> games = new ArrayList<>();
        db.open();
        Cursor curRes = db.getAllGames();
        if (curRes.getCount() != 0) {
            curRes.moveToFirst();
            while (!curRes.isAfterLast()) {
                if (curRes.getInt(11) != 0) {
                    games.add(new Game(curRes.getInt(0), curRes.getString(1), curRes.getString(2), curRes.getString(3), curRes.getString(4)
                            , curRes.getInt(5), curRes.getInt(6), curRes.getInt(7), curRes.getInt(8), curRes.getInt(9), curRes.getInt(10)
                            , curRes.getInt(11)));
                }
                curRes.moveToNext();

            }
        }
        db.close();
        return games;
    }


}

