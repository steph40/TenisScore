package steph.tam.tenisscore;

public interface GameDAO {

    interface RegisterListener {
        void onSuccess(String message);
        void onError(String message);
    }

    void register(Utilizador user, RegisterListener listener);



}
