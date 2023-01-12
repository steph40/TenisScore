package steph.tam.tenisscore.games;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Game implements Comparable<Game> {

    @SerializedName("nomeTorneio")
    private String nameTournament;
    @SerializedName("id")
    private int id;
    @SerializedName("dateTorneio")
    private String dateTournament;
    @SerializedName("jogador1")
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
                int set1_2, int set2_2, int set3_2, int vencedor) {
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
        this.vencedor = vencedor;


    }

    /**
     * @return retorna o nome do torneio
     */
    public String getNameTournament() {
        return nameTournament;
    }

    /**
     * @return retonrna o ID do jogo
     */
    public int getId() {
        return id;
    }

    /**
     * @return retorna a data do torneio
     */
    public String getDateTournament() {
        return dateTournament;
    }

    /**
     * @return retorna o nome do primeiro jogador
     */
    public String getNamePlayer1() {
        return namePlayer1;
    }

    /**
     * @return retorna o nome do segundo jogador
     */
    public String getNamePlayer2() {
        return namePlayer2;
    }

    /**
     * @return retorna a data como tipo Date
     */
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

    /**
     * Define o nome do torneio
     *
     * @param nameTournament
     */
    public void setNameTournament(String nameTournament) {
        this.nameTournament = nameTournament;
    }

    /**
     * Define o nome do Jogador 1
     *
     * @param namePlayer1
     */
    public void setNamePlayer1(String namePlayer1) {
        this.namePlayer1 = namePlayer1;
    }

    /**
     * Define o nome do Jogador 2
     *
     * @param namePlayer2
     */
    public void setNamePlayer2(String namePlayer2) {
        this.namePlayer2 = namePlayer2;
    }

    /**
     * Define a data do torneio
     *
     * @param dateTournament
     */
    public void setDateTournament(String dateTournament) {
        this.dateTournament = dateTournament;
    }

    /**
     * Comparar as datas para ordenar
     *
     * @param game
     * @return
     */
    @Override
    public int compareTo(Game game) {
        return getDateToSort().compareTo(game.getDateToSort());
    }

    /**
     * @return retorna o valor do set 1 do jogador 1
     */
    public int getSet1_1() {
        return set1_1;
    }

    /**
     * @return retorna o valor do set 1 do jogador 2
     */
    public int getSet1_2() {
        return set1_2;
    }

    /**
     * Define o valor do set 1 do jogador 1
     *
     * @param set1_1
     */
    public void setSet1_1(int set1_1) {
        this.set1_1 = set1_1;
    }

    /**
     * Define o valor do set 1 do jogador 2
     *
     * @param set1_2
     */
    public void setSet1_2(int set1_2) {
        this.set1_2 = set1_2;
    }

    /**
     * @return retorna o valor do set 2 do jogador 1
     */
    public int getSet2_1() {
        return set2_1;
    }

    /**
     * @return retorna o valor do set 3 do jogador 1
     */
    public int getSet3_1() {
        return set3_1;
    }

    /**
     * @return retorna o valor do set 2 do jogador 2
     */
    public int getSet2_2() {
        return set2_2;
    }

    /**
     * @return retorna o valor do set 3 do jogador 2
     */
    public int getSet3_2() {
        return set3_2;
    }

    /**
     * @return retorna o vencedor do jogo (1 - jogador 1, 2 - jogador 2, 0 - não houve vencedor)
     */
    public int getVencedor() {
        return vencedor;
    }

    /**
     * Define o vencedor do jogo
     *
     * @param vencedor
     */
    public void setVencedor(int vencedor) {
        this.vencedor = vencedor;
    }

    /**
     * Define o set 2 do jogador 1
     *
     * @param set2_1
     */
    public void setSet2_1(int set2_1) {
        this.set2_1 = set2_1;
    }

    /**
     * Define o set 3 do jogador 1
     *
     * @param set3_1
     */
    public void setSet3_1(int set3_1) {
        this.set3_1 = set3_1;
    }

    /**
     * Define o set 2 do jogador 2
     *
     * @param set2_2
     */
    public void setSet2_2(int set2_2) {
        this.set2_2 = set2_2;
    }

    /**
     * Define o set 3 do jogador 2
     *
     * @param set3_2
     */
    public void setSet3_2(int set3_2) {
        this.set3_2 = set3_2;
    }
}
