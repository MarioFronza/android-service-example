package br.udesc.ddm.service_example.retrofit;


import br.udesc.ddm.service_example.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserService {

    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);

}
