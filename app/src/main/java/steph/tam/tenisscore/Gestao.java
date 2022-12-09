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

    public boolean insertGame(String nameTournament, String date, String nameP1, String nameP2, int set1_1, int set2_1, int set3_1
            , int set1_2, int set2_2, int set3_2, int vencedor) {
        db.open();
        boolean resultado = db.insertGame(nameTournament, date, nameP1, nameP2, set1_1, set2_1, set3_1, set1_2, set2_2, set3_2, vencedor);
        db.close();
        return resultado;
    }
    /*
    public void updateGameScore(int id, int set1_1, int set2_1, int set1_2, int set2_2, int set3_1, int set3_2, int vencedor) {
        db.open();
        if (db.updateGameScore(set1_1, set2_1, set3_1, set2_1, set2_2, set3_2, vencedor, id)) {
            db.close();
        }
        db.close();
    }*/

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

