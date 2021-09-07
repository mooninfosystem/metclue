package com.metclue.app.networking;

public class ApiUtils {

    private ApiUtils() {
    }


    public static final String BASE_URL = "https://metclue.com/api/";

    public static ApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }


}