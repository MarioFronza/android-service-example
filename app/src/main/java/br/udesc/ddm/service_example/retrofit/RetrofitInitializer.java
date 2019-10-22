package br.udesc.ddm.service_example.retrofit;

import br.udesc.ddm.service_example.service.UserService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInitializer {

    private final Retrofit retrofit;

    public RetrofitInitializer() {
        retrofit = new Retrofit.Builder().baseUrl("https://api.github.com")
                .addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public UserService getUserService() {
        return retrofit.create(UserService.class);
    }

}