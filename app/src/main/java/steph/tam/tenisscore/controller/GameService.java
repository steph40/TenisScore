package steph.tam.tenisscore.controller;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
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
    @POST("/add_game")
    Call <Void> addGame(@Header("Authorization") String token, @Body Game game);

    @Headers("Accept: application/json")
    @GET("/get_last_id")
    Call <Integer> getLastId(@Header("Authorization") String token);

    @Headers("Accept: application/json")
    @GET("/get_game")
    Call <Game> getGame(@Header("Authorization") String token , @Query("id") int id);

    @Headers("Accept: application/json")
    @PUT("/update_game_edit")
    Call <Void> updateGameEdit(@Header("Authorization") String token , @Body Game gameEdit);

    @Headers("Accept: application/json")
    @PUT("/update_game_score")
    Call <Void> updateGameScore(@Header("Authorization") String token , @Body Game gameScore);

    @Headers("Accept: application/json")
    @DELETE("/delete_game")
    Call <Void> deleteGame(@Header("Authorization") String token , @Query("id") int id);






}
