package com.iitbombay.moodi.moodindigo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.iitbombay.moodi.moodindigo.EventsDatabase.DatabaseHelper;
import com.iitbombay.moodi.moodindigo.EventsDatabase.Event;
import com.iitbombay.moodi.moodindigo.data.Checksum;
import com.iitbombay.moodi.moodindigo.data.EventDetailResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2017;
    private SessionManager sessionManager;
    RetrofitClass rcInitiate;
    SearchInterface client;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(getApplicationContext());

        rcInitiate = new RetrofitClass(SplashActivity.this);
        client = rcInitiate.createBuilder().create(SearchInterface.class);
        rcInitiate.startLogging();
        Call<Checksum> call1 = client.getChecksum();


        call1.enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(Call<Checksum> call, final Response<Checksum> response1) {
//                Log.e("check online", String.valueOf(response.body().getChecksum()));
//                Log.e("check offline", String.valueOf(sessionManager.getInt("checksum")));

                if (response1.isSuccessful()) {

                    if (sessionManager.getInt("checksum") != response1.body().getChecksum()) {

                        rcInitiate = new RetrofitClass(SplashActivity.this);
                        client = rcInitiate.createBuilder().create(SearchInterface.class);
                        rcInitiate.startLogging();
                        final DatabaseHelper db = new DatabaseHelper(getApplicationContext());




                        Call<List<EventDetailResponse>> call2 = client.getEventDetails();

                        call2.enqueue(new Callback<List<EventDetailResponse>>() {
                            @Override
                            public void onResponse(Call<List<EventDetailResponse>> call3, Response<List<EventDetailResponse>> response) {
                                List<EventDetailResponse> events = response.body();
                                Log.e("size", String.valueOf(response.body().size()));

                                for (EventDetailResponse eventDetailResponse : events) {
                                    Event event = new Event(eventDetailResponse.getId(), eventDetailResponse.getName(), eventDetailResponse.getSubtitle(), eventDetailResponse.getDescription(), eventDetailResponse.getVenue(), eventDetailResponse.getStarttime(), eventDetailResponse.getEndtime(), eventDetailResponse.getDay(), eventDetailResponse.getType());
                                    db.updateEventifFound(event);

                                    Log.e("size2", String.valueOf(db.getAllEvents().get(0).getType()));

                                    Log.e("size2", String.valueOf(db.getAllEvents().get(0).getDay()));
                                }

                                Intent intent;
                                intent = new Intent(SplashActivity.this, MainActivity.class);
                                if (sessionManager.getBoolean("isLoggedIn")||sessionManager.getBoolean("isGuest")) {
                                    intent = new Intent(SplashActivity.this, MainActivity.class);
                                } else {
                                    Log.e("E1", "Going to login");
                                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                                }
                                sessionManager.enterInt("checksum", response1.body().getChecksum());
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<List<EventDetailResponse>> call, Throwable t) {

                                   // be executed once the timer is over
                                        // Start your app main activity
                                        Intent intent;
                                        if (!sessionManager.getBoolean("isLoggedIn")&&!sessionManager.getBoolean("isGuest")) {
                                            Log.e("E2", "Going to login");
                                            intent = new Intent(SplashActivity.this, LoginActivity.class);
                                        } else {
                                            intent = new Intent(SplashActivity.this, MainActivity.class);
                                        }
                                        startActivity(intent);
                                        // close this activity
                                        finish();

                            }
                        });

                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // This method will be executed once the timer is over
                                // Start your app main activity
                                Intent intent;
                                if (!sessionManager.getBoolean("isLoggedIn")&&!sessionManager.getBoolean("isGuest")) {
                                    Log.e("E3", "Going to login");
                                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                                } else {
                                    intent = new Intent(SplashActivity.this, MainActivity.class);
                                }
                                startActivity(intent);
                                // close this activity
                                finish();
                            }
                        }, SPLASH_TIME_OUT);
                    }


                }
                else{
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            Intent intent;
                            if (!sessionManager.getBoolean("isLoggedIn")&&!sessionManager.getBoolean("isGuest")) {
                                Log.e("E4", "Going to login");
                                intent = new Intent(SplashActivity.this, LoginActivity.class);
                            } else {
                                intent = new Intent(SplashActivity.this, MainActivity.class);
                            }
                            startActivity(intent);
                            // close this activity
                            finish();
                        }
                    }, SPLASH_TIME_OUT);

                }
            }

            @Override
            public void onFailure(Call<Checksum> call, Throwable t) {


                        // This method will be executed once the timer is over
                        // Start your app main activity
                        Intent intent;
                        if (!sessionManager.getBoolean("isLoggedIn")&&!sessionManager.getBoolean("isGuest")) {
                            Log.e("E4", "Going to login");
                            intent = new Intent(SplashActivity.this, LoginActivity.class);
                        } else {
                            intent = new Intent(SplashActivity.this, MainActivity.class);
                        }
                        startActivity(intent);
                        // close this activity
                        finish();

            }


        });


    }
}