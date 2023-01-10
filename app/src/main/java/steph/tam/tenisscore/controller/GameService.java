package steph.tam.tenisscore.controller;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import steph.tam.tenisscore.utilizadores.Token;
import steph.tam.tenisscore.utilizadores.Utilizador;

public interface GameService {

    @Headers("Accept: application/json")
    @POST("/register_user")
    Call<Void> registerUser(@Body Utilizador user);

    @Headers("Accept: application/json")
    @POST("/login")
    Call <Token> loginUser(@Body Utilizador user);

}
