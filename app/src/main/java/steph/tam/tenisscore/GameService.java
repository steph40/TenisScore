package steph.tam.tenisscore;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GameService {

    @Headers("Accept: application/json")
    @POST("/register_user")
    Call<Void> registerUser(@Body Utilizador user);


}
