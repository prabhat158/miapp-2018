package com.iitbombay.moodi.moodindigo;


import com.iitbombay.moodi.moodindigo.data.Checksum;
import com.iitbombay.moodi.moodindigo.data.EventDetailResponse;
import com.iitbombay.moodi.moodindigo.data.RegistrationRequest;
import com.iitbombay.moodi.moodindigo.data.RegistrationResponse;
import com.iitbombay.moodi.moodindigo.data.ResultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mrunz on 12/6/17.
 */

public interface SearchInterface {

    //    @GET("events/")
//    Call<List<E>> getEvents();
//
//    @GET("news/")
//    Call<List<E>> getNews();
//
//
    @GET("user/{id}")
    Call<RegistrationResponse> checkUserDetails(@Path("id") String id);

    @POST("user/create")
    Call<RegistrationResponse> fillRegistrationForm(@Body RegistrationRequest registrationRequest);

    @GET("schedule/check")
    Call<Checksum> getChecksum();

    @GET("results/events")
    Call<List<ResultResponse>> getResults();

    @GET("schedule/events")
    Call<List<EventDetailResponse>> getEventDetails();
}
