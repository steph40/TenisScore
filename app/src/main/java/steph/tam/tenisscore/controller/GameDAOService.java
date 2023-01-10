package steph.tam.tenisscore.controller;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import steph.tam.tenisscore.utilizadores.Token;
import steph.tam.tenisscore.utilizadores.Utilizador;

public class GameDAOService implements GameDAO {

    GameService gameService;

    public GameDAOService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY); //NONE,BASIC,HEADERS

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build());
        Retrofit retrofit = builder.build();

        gameService = retrofit.create(GameService.class);

    }


    public void register(Utilizador user, RegisterListener listener) {
        Call<Void> call = gameService.registerUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.code() == 200) {
                    listener.onSuccess("Registo com Sucesso");
                } else {
                    listener.onError("Ocorreu um erro no registo");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void login(Utilizador user, LoginListener listener) {
        Call<Token> call = gameService.loginUser(user);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token token = response.body();
                switch (response.code()) {
                    case 200:
                        if (token == null) {
                            listener.onError("Token não gerado");
                        } else {
                            listener.onSuccess(token);
                        }
                        break;
                    case 404:
                        listener.onError("Utilizador ou password invalidos");
                        break;
                    default:
                        listener.onError("Codigo: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}
