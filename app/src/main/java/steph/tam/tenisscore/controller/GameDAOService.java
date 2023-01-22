package steph.tam.tenisscore.controller;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import steph.tam.tenisscore.games.Game;
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
                .baseUrl("http://10.0.2.2:8080")
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
    public void renewToken(String token, RenewTokenListener listener) {
        Call<Token> call = gameService.renew_token(token);
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
                    case 500:
                        listener.onError("sdfjsd");
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
                    case 500:
                        listener.onError("sdfjsd");
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

    @Override
    public void getAllGames(String token, GetGamesListener listener) {
        Call<List<Game>> call = gameService.getGames(token);
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                List<Game> listaGames = response.body();
                switch (response.code()) {
                    case 200:
                        if (listaGames != null) {
                            listener.onSuccess(listaGames);
                        } else {
                            listener.onError("Não há jogos guardados");
                        }
                        break;

                    default:
                        listener.onError("Código de resposta não reconhecido" + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }


    public void addGames(String token, Game game, AddGameListener listener) {
        Call<Void> call = gameService.addGame(token, game);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                switch (response.code()) {
                    case 200:
                        listener.onSuccess("Jogo adicionado com sucesso");
                        break;

                    default:
                        listener.onError("Jogo não adicionado");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getLastId(String token, GetLastIdListener listener) {
        Call<Integer> call = gameService.getLastId(token);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                int id = response.body();
                switch (response.code()) {
                    case 200:
                        listener.onSuccess(id);
                        break;

                    default:
                        listener.onError("Jogo não adicionado");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getGame(String token, int id, GetGameListener listener) {
        Call<Game> call = gameService.getGame(token, id);
        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                Game game = response.body();
                switch (response.code()) {
                    case 200:
                        listener.onSuccess(game);
                        break;

                    default:
                        listener.onError("Jogo não adicionado");
                }
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void editGame(String token, Game gameEdit, GameEditListener listener) {
        Call<Void> call = gameService.updateGameEdit(token, gameEdit);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 200:
                        listener.onSuccess("Jogo editado");
                        break;

                    default:
                        listener.onError("Jogo não editado");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void editGameScore(String token, Game gameScore, GameScoreEditListener listener) {
        Call<Void> call = gameService.updateGameScore(token, gameScore);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()) {
                    case 200:
                        listener.onSuccess("Jogo Score editado");
                        break;
                    default:
                        listener.onError("Jogo Score nao editado");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void deleteGame(String token, int id, DeleteGameListener listener) {
        Call<Void> call = gameService.deleteGame(token,id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                switch (response.code()){
                    case 200:
                        listener.onSuccess("Jogo apagado");
                        break;
                    default:
                        listener.onError("Jogo nao apagado");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getID(String token, GetIDListener listener) {
        Call<Integer> call = gameService.get_ID(token);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                switch (response.code()){
                    case 200:
                        listener.onSuccess(response.body());
                        break;
                    default:
                        listener.onError("ID não retornado");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getUserID(String token, int idUser, GetUserIDListener listener) {
        Call<Integer> call = gameService.get_user_id(token, idUser);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                switch (response.code()){
                    case 200:
                        listener.onSuccess(response.body());
                        break;
                    default:
                        listener.onError("ID não retornado");
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }
}
