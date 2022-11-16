package steph.tam.tenisscore;

public class Game {

    private String nameTournament;
    private int id;
    //private String dateTournament;
    //private String namePlayer1;
    //private String namePlayer2;
    //private  String set1_1;
    //private  String set2_1;
    //private  String set3_1;
    //private  String set1_2;
    //private  String set2_2;
    //private  String set3_2;
    //private  String r1;
    //private  String r2;

    public Game(int id ,String nameTournament) {
        this.id=id;
        this.nameTournament = nameTournament;
        //this.dateTournament = dateTournament;
        //this.namePlayer1 = namePlayer1;
        //this.namePlayer2 = namePlayer2;
        //this.set1_1 = set1_1;
        //this.set2_1 = set2_1;
        //this.set3_1 = set3_1;
        //this.set1_2 = set1_2;
        //this.set2_2 = set2_2;
        //this.set3_2 = set3_2;
        //this.r1 = r1;
        //this.r2 = r2;
    }

    public String getNameTournament() {
        return nameTournament;
    }


}
