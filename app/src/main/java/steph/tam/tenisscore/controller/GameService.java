package steph.tam.tenisscore.controller;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import steph.tam.tenisscore.games.Game;
import steph.tam.tenisscore.utilizadores.Token;
import steph.tam.tenisscore.utilizadores.Utilizador;

public interface GameService {

    @Headers("Accept: application/json")
    @POST("/register_user")
    Call<Void> registerUser(@Body Utilizador user);

    @Headers("Accept: application/json")
    @POST("/login")
    Call <Token> loginUser(@Body Utilizador user);

    @Headers("Accept: application/json")
    @GET("/get_all_games")
    Call<List<Game>> getGames(@Header("Authorization") String token);

    @Headers("Accept: application/json")
    @GET("/get_game")
    Call <Game> getGame(@Header("Authorization") String token);



}
