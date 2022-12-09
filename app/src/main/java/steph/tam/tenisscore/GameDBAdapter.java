package steph.tam.tenisscore;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GameDBAdapter {

    String TAG = "TenisScore";

    String DB_NAME = "tenisScoreDB";
    String DB_TABLE = "games";
    int DB_VERSION = 1;

    String SQL_CREATE = "CREATE TABLE " + DB_TABLE +
            " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nameTournament TEXT NOT NULL, " +
            "date TEXT NOT NULL, " +
            "nameP1 TEXT NOT NULL, " +
            "nameP2 TEXT NOT NULL, " +
            "set1_1 INTEGER NOT NULL, " +
            "set2_1 INTEGER NOT NULL, " +
            "set3_1 INTEGER NOT NULL, " +
            "set1_2 INTEGER NOT NULL, " +
            "set2_2 INTEGER NOT NULL, " +
            "set3_2 INTEGER NOT NULL, " +
            "vencedor INTEGER NOT NULL);";

    String SQL_DROP = "DROP TABLE IF EXISTS " + DB_TABLE;

    DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public GameDBAdapter(Context ctx) {
        dbHelper = new DatabaseHelper(ctx);
    }

    class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE);
            Log.d(TAG, "Table games created");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DROP);
            onCreate(db);
            Log.d(TAG, "Table games recreated");
        }
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // returns false if an error occurred
    public boolean insertGame(String nameTournament, String date, String nameP1, String nameP2, int set1_1, int set2_1, int set3_1
            , int set1_2, int set2_2, int set3_2, int vencedor) {

        String sql = "INSERT INTO " + DB_TABLE + " (nameTournament,date,nameP1,nameP2,set1_1,set2_1,set3_1,set1_2,set2_2,set3_2,vencedor) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?);";
        Object[] args = new Object[]{nameTournament, date, nameP1, nameP2, set1_1, set2_1, set3_1, set1_2, set2_2, set3_2, vencedor};

        try {
            db.execSQL(sql, args);
            return true;
        } catch (SQLException e) {
            Log.d(TAG, e.toString());
            return false;
        }

    }

    //Update
    public boolean updateGameForm(int id, String nameTournament, String date, String nameP1, String nameP2) {
        String sql = "UPDATE " + DB_TABLE + " SET nameTournament = ?, date = ?, nameP1 = ?, nameP2 = ? WHERE id = ? ;";
        Object[] args = new Object[]{nameTournament, date, nameP1, nameP2, id};
        try {
            db.execSQL(sql, args);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean updateGameScore(int id, int set1_1, int set2_1, int set3_1, int set1_2, int set2_2, int set3_2, int vencedor) {
        String sql = "UPDATE " + DB_TABLE + " SET set1_1 = ?, set2_1 = ?, set3_1 = ?, set1_2 = ?, set2_2 = ?, set3_2 = ?, vencedor = ? WHERE id = ? ;";
        Object[] args = new Object[]{set1_1, set2_1, set3_1, set1_2, set2_2, set3_2, vencedor, id};
        try {
            db.execSQL(sql, args);
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    // returns a Cursor object positioned before the first entry
    public Cursor getGame(int id) {

        String sql = "SELECT nameTournament,date,nameP1,nameP2,set1_1,set2_1,set3_1,set1_2,set2_2,set3_2,vencedor FROM " + DB_TABLE + " WHERE id=? ;";
        String[] args = new String[]{Integer.toString(id)};
        return db.rawQuery(sql, args);

    }

    public Cursor getAllGames() {
        String sql = "SELECT id,nameTournament,date,nameP1,nameP2,set1_1,set2_1,set3_1,set1_2,set2_2,set3_2,vencedor FROM " + DB_TABLE + ";";
        return db.rawQuery(sql, null);
    }

    public Cursor getLastId() {
        String sql = "SELECT MAX(id) FROM games;";
        return db.rawQuery(sql, null);
    }

    // returns false if an error occurred
    public boolean deleteGame(int id) {

        String sql = "DELETE FROM " + DB_TABLE + " WHERE id=? ;";
        Object[] args = new Object[]{id};
        try {
            db.execSQL(sql, args);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


}
