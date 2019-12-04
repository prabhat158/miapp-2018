package com.iitbombay.moodi.moodindigo;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mrunz on 14/6/17.
 */

public class RetrofitClass {
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit.Builder builder = new Retrofit.Builder();
    Retrofit retrofit;
    String BASE_URL;
    Context context;

    public RetrofitClass(Context context) {
        this.context = context;
    }

    public Retrofit createBuilder() {
        BASE_URL = context.getResources().getString(R.string.BASE_URL);
        builder.baseUrl(BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create());
        retrofit = builder
                .client(
                        httpClient.build()
                )
                .build();
        return retrofit;
    }

    public void startLogging() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
    }

}