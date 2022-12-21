package steph.tam.tenisscore;

public class Utilizador {

    private String username;
    private String password;

    public Utilizador(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
