package com.metclue.app.networking;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("authentication/register")
    Call<ResponseBody> registerUser(
            @Body RequestBody requestBody
    );

    @POST("authentication/loginwithotp")
    Call<ResponseBody> checkPhoneNumber(
            @Body RequestBody requestBody
    );
}
