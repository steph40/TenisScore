package steph.tam.tenisscore;

public class Game {

    private String nameTournament;
    private String dateTournament;
    private String namePlayer1;
    private String namePlayer2;

    public Game(String nameTournament, String dateTournament, String namePlayer1, String namePlayer2) {
        this.nameTournament = nameTournament;
        this.dateTournament = dateTournament;
        this.namePlayer1 = namePlayer1;
        this.namePlayer2 = namePlayer2;
    }

    public String getNameTournament() {
        return nameTournament;
    }

    public String getDateTournament(){ return dateTournament; }

    public String getNamePlayer1() { return namePlayer1; }

    public String getNamePlayer2() { return namePlayer2; }
}
