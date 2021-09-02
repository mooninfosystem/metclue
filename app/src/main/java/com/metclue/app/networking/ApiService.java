package com.metclue.app.networking;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("new_mobile")
    Call<ResponseBody> checkMobileNumber(
            @Body RequestBody requestBody
    );

    @POST("login")
    Call<ResponseBody> login(
            @Body RequestBody requestBody
    );

    @POST("new_register")
    Call<ResponseBody> registerUser(
            @Body RequestBody requestBody
    );

    @POST("forgetpassword")
    Call<ResponseBody> forgetPassword(
            @Body RequestBody requestBody
    );

    @POST("createnewpassword")
    Call<ResponseBody> createNewPassword(
            @Body RequestBody requestBody
    );

    @POST("updateregister")
    Call<ResponseBody> updateRegisterUser(
            @Body RequestBody requestBody
    );

    @POST("registerpay")
    Call<ResponseBody> registerPayment(
            @Body RequestBody requestBody
    );

    @POST("sendwithdrawalrequest")
    Call<ResponseBody> withdrawalRequest(
            @Body RequestBody requestBody
    );

    @POST("getwithdrawalamount")
    Call<ResponseBody> getWithdrawalAmount(
            @Body RequestBody requestBody
    );

    @POST("showalltransaction")
    Call<ResponseBody> showAllTransaction(
            @Body RequestBody requestBody
    );

    @POST("getearninglist")
    Call<ResponseBody> getEarningList(
            @Body RequestBody requestBody
    );

    @POST("gettrefferallist")
    Call<ResponseBody> getRefferalList(
            @Body RequestBody requestBody
    );


}
