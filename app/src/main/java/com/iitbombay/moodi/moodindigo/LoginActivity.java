package com.iitbombay.moodi.moodindigo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.iitbombay.moodi.moodindigo.data.RegistrationResponse;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button button;
    TextView register_later;
    RetrofitClass rcinitiate;
    SearchInterface client;
    String fbid;
    ProgressBar progressBar;
    RegistrationResponse registrationResponse = new RegistrationResponse();
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private SessionManager sessionManager;

    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onStart() {
        super.onStart();

         GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
         if (account != null) {
             nextActivity(account);
         }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = (Button) findViewById(R.id.fancy_login);
        progressBar = (ProgressBar) findViewById(R.id.login_progress_bar);
        progressBar.setVisibility(View.GONE);

        // %%%%%%% GOOGLE LOGIN %%%%%%%%%%
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Button clicked", "Button clicked.");

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);


            }
        });
//           Log.e("Login text",loginButton.getText().toString());

        register_later = (TextView) findViewById(R.id.register_later);
        register_later.setPaintFlags(register_later.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        register_later.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                sessionManager.enterBoolean("isLoggedIn", false);
                sessionManager.enterBoolean("isGuest",true);
                startActivity(intent);
            }
        });

        sessionManager = new SessionManager(getApplicationContext());


        /* %%%%%% FACEBOOK LOGIN %%%%%%%%%
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(getApplication());
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                nextActivity(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        Log.e("LOGIN", "A");
        // loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, callback);
//        LoginManager.getInstance().registerCallback(callbackManager, callback);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.iitbombay.moodi.moodindigo",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        %%%%%% FACEBOOK LOGIN %%%%%%%%%
        */
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Facebook login
        //Profile profile = Profile.getCurrentProfile();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
        // accessTokenTracker.stopTracking();
        // profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //callbackManager.onActivityResult(requestCode, resultCode, data);
        Log.e("getActivity", "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                Log.w("Debug", "Signed In successfully");
                // Signed in successfully, show authenticated UI.
                nextActivity(account);
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information
                Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
                nextActivity(null);
            }
        }
    }



    private void nextActivity(final GoogleSignInAccount profile) {
        if (profile != null) {
            Log.e("Start", "Start");

            rcinitiate = new RetrofitClass(LoginActivity.this);

            client = rcinitiate.createBuilder().create(SearchInterface.class);
            rcinitiate.startLogging();
            fbid = profile.getId();
            Call<RegistrationResponse> call = client.checkUserDetails(fbid);
            progressBar.setVisibility(View.VISIBLE);

            Log.e("HERE", "HERE");

            call.enqueue(new retrofit2.Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                    try {
                        registrationResponse = response.body();
                        //Don't Remove this log. This log is ~haunted~
                        Log.e("Login", "Hie~");

                        String image = null;
                        Uri profilePic = profile.getPhotoUrl();
                        if (profilePic != null) {
                            image = profilePic.toString();
                        }

                        if (response.code() == 200) {
                            Intent main = new Intent(LoginActivity.this, MainActivity.class);

                            sessionManager.enterString("name", registrationResponse.getName());
                            sessionManager.enterString("email", registrationResponse.getEmail());
                            sessionManager.enterString("image", image);
                            sessionManager.enterString("mi number", registrationResponse.getMi_number());
                            sessionManager.enterString("fbid", registrationResponse.getFb_id());
                            sessionManager.enterString("college", registrationResponse.getPresent_college());
                            sessionManager.enterString("city", registrationResponse.getPresent_city());
                            sessionManager.enterString("address", registrationResponse.getPostal_address());
                            sessionManager.enterInt("zip", registrationResponse.getZip_code());
                            sessionManager.enterString("mobile", registrationResponse.getMobile_number());
                            sessionManager.enterString("year", registrationResponse.getYear_of_study());
                            sessionManager.enterBoolean("isLoggedIn", true);
                            sessionManager.enterBoolean("isGuest", false);

                            startActivity(main);
                        } else {
                            Intent main = new Intent(LoginActivity.this, RegistrationActivity.class);

                            sessionManager.enterString("name", profile.getGivenName());
                            sessionManager.enterString("image", image);
                            sessionManager.enterString("fbid", fbid);
                            sessionManager.enterBoolean("isGuest", false);

                            startActivity(main);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
