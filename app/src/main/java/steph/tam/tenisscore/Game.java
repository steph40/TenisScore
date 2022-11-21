package steph.tam.tenisscore;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Game implements Comparable<Game> {

    private String nameTournament;
    private int id;
    private String dateTournament;
    private String namePlayer1;
    private String namePlayer2;
    private int set1_1;
    private int set2_1;
    private int set3_1;
    private int set1_2;
    private int set2_2;
    private int set3_2;
    private int vencedor; //se for 1 - jogador 1 a verde, se for 2 - jogador 2 a verde


    public Game(int id, String nameTournament, String dateTournament, String namePlayer1, String namePlayer2, int set1_1, int set2_1, int set3_1,
                int set1_2, int set2_2, int set3_2) {
        this.id = id;
        this.nameTournament = nameTournament;
        this.dateTournament = dateTournament;
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2;
        this.set1_1 = set1_1;
        this.set2_1 = set2_1;
        this.set3_1 = set3_1;
        this.set1_2 = set1_2;
        this.set2_2 = set2_2;
        this.set3_2 = set3_2;


    }

    public String getNameTournament() {
        return nameTournament;
    }

    public int getId() {
        return id;
    }

    public String getDateTournament() {
        return dateTournament;
    }

    public String getNamePlayer1() {
        return namePlayer1;
    }

    public String getNamePlayer2() {
        return namePlayer2;
    }

    public Date getDateToSort() {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = simpledateformat.parse(dateTournament);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


    public void setNameTournament(String nameTournament) {
        this.nameTournament = nameTournament;
    }

    public void setNamePlayer1(String namePlayer1) {
        this.namePlayer1 = namePlayer1;
    }

    public void setNamePlayer2(String namePlayer2) {
        this.namePlayer2 = namePlayer2;
    }

    public void setDateTournament(String dateTournament) {
        this.dateTournament = dateTournament;
    }


    @Override
    public int compareTo(Game game) {
        return getDateToSort().compareTo(game.getDateToSort());
    }

    public int getSet1_1() {
        return set1_1;
    }

    public int getSet1_2() {
        return set1_2;
    }

    public void setSet1_1(int set1_1) {
        this.set1_1 = set1_1;
    }

    public void setSet1_2(int set1_2) {
        this.set1_2 = set1_2;
    }

    public int getSet2_1() {
        return set2_1;
    }

    public int getSet3_1() {
        return set3_1;
    }

    public int getSet2_2() {
        return set2_2;
    }

    public int getSet3_2() {
        return set3_2;
    }

    public int getVencedor() {
        return vencedor;
    }

    public void setVencedor(int vencedor) {
        this.vencedor = vencedor;
    }

    public void setSet2_1(int set2_1) {
        this.set2_1 = set2_1;
    }

    public void setSet3_1(int set3_1) {
        this.set3_1 = set3_1;
    }

    public void setSet2_2(int set2_2) {
        this.set2_2 = set2_2;
    }

    public void setSet3_2(int set3_2) {
        this.set3_2 = set3_2;
    }
}
