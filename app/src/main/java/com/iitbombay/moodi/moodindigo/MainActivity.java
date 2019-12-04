package com.iitbombay.moodi.moodindigo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.iitbombay.moodi.moodindigo.Fragments.AboutMIFragment;
import com.iitbombay.moodi.moodindigo.Fragments.ContactFragment;
import com.iitbombay.moodi.moodindigo.Fragments.DevelopersFragment;
import com.iitbombay.moodi.moodindigo.Fragments.FAQFragment;
import com.iitbombay.moodi.moodindigo.Fragments.FnBFragment;
import com.iitbombay.moodi.moodindigo.Fragments.GoingTabsFragment;
import com.iitbombay.moodi.moodindigo.Fragments.HelplineFragment;
import com.iitbombay.moodi.moodindigo.Fragments.MainFragment;
import com.iitbombay.moodi.moodindigo.Fragments.MapFragment;
import com.iitbombay.moodi.moodindigo.Fragments.ResultsFragment;
import com.iitbombay.moodi.moodindigo.Notifications.AutoStart;
import com.iitbombay.moodi.moodindigo.data.NewsResponse;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<NewsResponse> responses = new ArrayList<>();
    RetrofitClass rcinitiate;
    SearchInterface client;

    // Navigation Bar
    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    MenuItem prevMenuItem;
    DrawerLayout mDrawerLayout;

    ImageView hamburger;
    String locationAddress;
    GPSTracker gps;

    SessionManager sessionManager;
    String image, name, mi_number;
    private boolean backPressedToExitOnce = false;
    private Toast toast = null;
    protected static final String TAG = "LocationOnOff";


    private GoogleApiClient googleApiClient;
    final static int REQUEST_LOCATION = 199;

//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this,AutoStart.class );
        sendBroadcast(intent);

        sessionManager = new SessionManager(getApplication());

        //FacebookSdk.sdkInitialize(getApplicationContext());
        hamburger = (ImageView) findViewById(R.id.hamburger);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        hamburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (sessionManager.getBoolean("isLoggedIn")) {
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
        } else {
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
        }


//        Log.e("check login",String.valueOf(sessionManager.getBoolean("isLoggedIn")));
//
//        Log.e("check login name",sessionManager.getString("names"));

        View hview = navigationView.getHeaderView(0);
        TextView userName = (TextView) hview.findViewById(R.id.textView1);
        TextView miNo = (TextView) hview.findViewById(R.id.textView2);
        if (sessionManager.getBoolean("isLoggedIn")) {
            name = sessionManager.getString("name");
            userName.setText(name);
            image = sessionManager.getString("image");
            mi_number = sessionManager.getString("mi number");
            miNo.setText(mi_number);
        } else {
            name = "Guest User";
            userName.setText(name);
            image = null;
            mi_number = "MI-ABC-XYZ";
            miNo.setText(mi_number);
        }

        CircleImageView profileImage = (CircleImageView) hview.findViewById(R.id.imageView);
        if (image != null && image != "empty") {


            ConnectivityManager connectivityManager
                    = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                new DownloadImage(profileImage, MainActivity.this).execute(image);
            } else {
                profileImage.setImageResource(R.drawable.icon);
            }

        }
        else{

        profileImage.setImageResource(R.drawable.icon);
        }
//
//        rcinitiate = new RetrofitClass(this);
//        client = rcinitiate.createBuilder().create(SearchInterface.class);
//        rcinitiate.startLogging();
//
//        Call<List<NewsResponse>> call = client.getNews();
//        call.enqueue(new Callback<List<NewsResponse>>() {
//            @Override
//            public void onResponse(Call<List<NewsResponse>> call, Response<List<NewsResponse>> response) {
//
//                pb.setVisibility(View.GONE);
//                responses = response.body();
//                Bundle bundle = new Bundle();
//                bundle.putInt("size", responses.size());
//                for (int i = 0; i < responses.size(); i++) {
//                    String json = new Gson().toJson(responses.get(i));
//                    bundle.putString("List" + i, json);
//                }
        MainFragment mainFragment = new MainFragment();
       openFragment(mainFragment,"main");
//
//            }
//
//            @Override
//            public void onFailure(Call<List<NewsResponse>> call, Throwable t) {
//
//            }
//        });
//        Log.i("Size112", String.valueOf(responses.size()));

        //////////
        checkPermission();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA);

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                Log.e("permission", "granted");
                callLocation();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            Log.e("MainActivity", "Lower Than MarshMallow");
            callLocation();
        }

        // Todo Turn On GPS Automatically

        if(!hasGPSDevice(MainActivity.this)){
            Toast.makeText(MainActivity.this,"Gps not Supported", Toast.LENGTH_SHORT).show();
        }
        final LocationManager manager = (LocationManager) MainActivity.this.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(MainActivity.this)) {
            Log.e("keshav","Gps already enabled");
//            Toast.makeText(getActivity(),"Gps not enabled",Toast.LENGTH_SHORT).show();
            enableLoc();
        }else{
            Log.e("keshav","Gps already enabled");
//            Toast.makeText(getActivity(),"Gps already enabled",Toast.LENGTH_SHORT).show();
        }

    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        googleApiClient.connect();
        super.onStart();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
//
//        if (id == R.id.nav_home) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//                        drawer.closeDrawer(GravityCompat.START);
//                }
//            }, 120);

//
        DrawerLayout mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (id == R.id.nav_about) {
                    AboutMIFragment aboutMIFragment=new AboutMIFragment();
                    openFragment(aboutMIFragment,"about");

                }

                else if (id == R.id.nav_helpline) {
                    HelplineFragment helplineFragment=new HelplineFragment();
                    openFragment(helplineFragment,"helpline");

                }
                else if (id == R.id.nav_home) {
                    MainFragment mainFragment=new MainFragment();
                    openFragment(mainFragment,"main");


                }
                else if (id == R.id.nav_contacts) {
                    ContactFragment contactFragment=new ContactFragment();
                    openFragment(contactFragment,"contact");


                } else if (id == R.id.nav_developers) {
                    DevelopersFragment developersFragment=new DevelopersFragment();
                    openFragment(developersFragment,"developer");

                } else if (id == R.id.nav_fnb) {
                    FnBFragment fnBFragment=new FnBFragment();
                    openFragment(fnBFragment,"fnb");

                }
                else if (id == R.id.nav_faq) {
                    FAQFragment faqFragment=new FAQFragment();
                    openFragment(faqFragment,"faq");

                }
                else if (id == R.id.nav_results) {
//
                    ResultsFragment resultsFragment=new ResultsFragment();
                    openFragment(resultsFragment,"results");

                }
                else if (id == R.id.nav_my_events) {
                    GoingTabsFragment goingTabsFragment=new GoingTabsFragment();
                    openFragment(goingTabsFragment,"going");

                }
               else if (id == R.id.nav_logout) {

                    // LoginManager.getInstance().logOut();
                    Auth.GoogleSignInApi.signOut(googleApiClient);
                    sessionManager.clear();
                    sessionManager.enterBoolean("isLoggedIn", false);
                    sessionManager.enterBoolean("isGuest",false);
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(1);
                }

                else if (id == R.id.nav_login) {
                    sessionManager.enterBoolean("isGuest",false);
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        }, 100);

//        else if (id == R.id.nav_maps) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    Intent intent = new Intent(getApplicationContext(),MapFragment.class);
////                    startActivity(intent);
//                    Class fragmentClass = MapFragment.class;
//                    MapFragment mapFragment = null;
//
//                    try {
//                        mapFragment = (MapFragment) fragmentClass.newInstance();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    fragmentManager.beginTransaction().replace(R.id.relative_layout_for_main_fragment, mapFragment).commit();
//                }
//            }, 120);
//
//        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CAMERA);

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                Log.e("permission", "granted");

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        } else {
            Log.e("MainActivity", "Lower Than MarshMallow");
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        FragmentManager fragmentManager
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.relative_layout_for_main_fragment);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (currentFragment instanceof MapFragment){
            Log.e("BackButtonIssues", "Current Fragment is map fragment");
            super.onBackPressed();
        }
        else if (currentFragment instanceof MainFragment){
            Log.e("BackButtonIssues", "Current Fragment is main fragment");
//            super.onBackPressed();
            this.finishAffinity();
        }
        else{
            super.onBackPressed();
        }
//        if (backPressedToExitOnce) {
//            super.onBackPressed();
//        } else {
//            this.backPressedToExitOnce = true;
//            toast = Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT);
//            toast.show();//showToast("Press again to exit");
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    backPressedToExitOnce = false;
//                }
//            }, 2000);
//        }



    }

    /**
     * Created to make sure that you toast doesn't show miltiple times, if user pressed back
     * button more than once.
     *
     * @param message Message to show on toast.
     */
    private void showToast(String message) {
        if (this.toast == null) {
            // Create toast if found null, it would he the case of first call only
            this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        } else if (this.toast.getView() == null) {
            // Toast not showing, so create new one
            this.toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);

        } else {
            // Updating toast message is showing
            this.toast.setText(message);
        }

        // Showing toast finally
        this.toast.show();
    }

    /**
     * Kill the toast if showing. Supposed to call from onPause() of activity.
     * So that toast also get removed as activity goes to background, to improve
     * better app experiance for user
     */
    private void killToast() {
        if (this.toast != null) {
            this.toast.cancel();
        }
    }

    @Override
    protected void onPause() {
        killToast();
        super.onPause();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                mDrawerLayout = findViewById(R.id.drawer_layout);
//                mDrawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

public void openFragment(Fragment fragment, String tag){
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    //ft.setCustomAnimations(R.anim.slide_out_from_left,R.anim.slide_in_from_right);
    ft.replace(R.id.relative_layout_for_main_fragment, fragment, fragment.getTag());
    ft.addToBackStack(tag);
    ft.commit();
}
    public void sendemail(View v)
    {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        TextView b= (TextView)v;
        String buttonText=b.getText().toString();
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {buttonText});
        intent.setType("text/plain");
        Intent chooser=Intent.createChooser(intent, "Send Email");
        startActivity(chooser);
    }

    public void call(View v)
    {
        TextView b = (TextView)v;
        String phoneNumber=b.getText().toString();
        String uri = "tel:" + phoneNumber;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this,AutoStart.class );
        sendBroadcast(intent);
    }

    ////
    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    private void enableLoc()
    {
        // TODO Add this Dependency
        // compile 'com.google.android.gms:play-services-location:7.+'

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            Log.d("Location error","Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(0);
            locationRequest.setFastestInterval(0);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();

                    Log.e("keshav","status Called  -->" + status.getStatusCode());

                    switch (status.getStatusCode())
                    {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            Log.e("keshav","LocationSettingsStatusCodes.RESOLUTION_REQUIRED Called ....");
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(MainActivity.this, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }


    private void callLocation() {

        Log.e("keshav", "callLocation Called ... ");

        gps = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Log.e("MainActivity", "latitude -> " + latitude);
            Log.e("MainActivity", "longitude -> " + longitude);

            LocationAddress locationAddress = new LocationAddress();
            locationAddress.getAddressFromLocation(latitude, longitude,
                    getApplicationContext(), new GeocoderHandler());

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings


//            gps.showSettingsAlert();
            enableLoc();
        }
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
//            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }

//            LoginPreferences.getActiveInstance(LoginActivity.this).setLocationAddress(locationAddress);

            Log.e("keshav", "addrees  in Main Activity -> " + locationAddress);
        }
    }

    public void checkPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                    android.Manifest.permission.CAMERA);

            if (permissionCheck == PackageManager.PERMISSION_GRANTED)
            {
                //showing dialog to select image
                Log.e("keshav", "permission granted");
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{android.Manifest.permission.CAMERA,
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }
    }







    @Override
    protected void onResume() {
        super.onResume();
        enableLoc();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("onActivityResult()", Integer.toString(resultCode));

        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode)
        {
            case REQUEST_LOCATION:
                switch (resultCode)
                {
                    case Activity.RESULT_OK:
                    {
                        // All required changes were successfully made
//                        Toast.makeText(MainActivity.this, "Location enabled by user!", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case Activity.RESULT_CANCELED:
                    {
                        // The user was asked to change settings, but chose not to
//                        Toast.makeText(MainActivity.this, "Location not enabled, user cancelled.", Toast.LENGTH_LONG).show();
//
//                        finish();

                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                break;
        }
    }



}





