package com.iitbombay.moodi.moodindigo.Fragments;


import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iitbombay.moodi.moodindigo.Place;
import com.iitbombay.moodi.moodindigo.R;
import com.iitbombay.moodi.moodindigo.Routes;
import com.iitbombay.moodi.moodindigo.ServerConnection.GsonModels;
import com.iitbombay.moodi.moodindigo.UserLockBottomSheetBehavior;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.Context.LOCATION_SERVICE;
import static com.iitbombay.moodi.moodindigo.R.id.map;
import static com.facebook.FacebookSdk.getApplicationContext;

import com.iitbombay.moodi.moodindigo.Distance;
import com.iitbombay.moodi.moodindigo.Duration;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
//public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnCameraMoveListener, GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener, Callback<GsonModels.DistanceMatrix>, View.OnClickListener {


public class MapFragment extends Fragment implements OnMapReadyCallback, LocationListener, GoogleMap.OnCameraMoveListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener, View.OnClickListener {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final String DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?";
    private static final String GOOGLE_API_KEY = "AIzaSyC31o6h06Z36V8M0lXgkGj4Y1cmNRVV9s0";
    private static final String LakeSide1 = "}pvsB___|Lf@LT@T?f@@\\AV?r@C^K?{AZoD_@kFq@yAIyC_@aDUYwAu@eIy@yAS}BpAm@B_EMeDVgANUFIHAJBdDl@bABJALIHGDQBMAOKo@c@UMSDSJu@p@uB~CeAtAc@b@YJi@EyBW";
    private static final String LakeSide2 ="ykvsBs{_|LMuBEwAJuASm@c@k@eAk@{@gBz@dBgH|CGbCFcCoER?}C?zCa@NKp@Cx@Bx@";
    private static final String LakeSide3 = "{owsBgs_|LQTCH?HAH@F@LBJV`ABHDJLRXf@j@t@PTPLVNl@N";
    private static final String HillSide1 = "wgwsBy~`|LqJT_@@UFIHINGPATD`@Cd@G^eAL}BR]EGSI_AEOMKWCe@DeBNg@@[FKHSTUHcAFkHBa@D[H[PWTST]\\Y`@[`@]b@Yb@MRS`@Q^_@bBAXFJHLNR^R^V^P\\PTDTDPB";
    private static final String HillSide2 = "wpxsBux`|LHpEBzDBNBHFNf@rAHD`Ad@RNPNJNFR?NANGh@";
    //    private LatLng mapNoEntryLatLng1 = new LatLng(19.12595,72.915477);
//    private LatLng mapNoEntryLatLng2 = new LatLng(19.1274,72.914747);
    private LatLng mapNoEntryLatLng3 = new LatLng(19.128424,72.914672);
    private LatLng mapNoEntryLatLng4 = new LatLng(19.124785,72.911389);
    private LatLng mapNoEntryLatLng5 = new LatLng(19.134019,72.910691);
    private LatLng mapNoEntryLatLng6 = new LatLng(19.131019,72.912033);
    private LatLng mapNoEntryLatLng7 = new LatLng(19.129701,72.919006);
    private LatLng mapNoEntryLatLng8 = new LatLng(19.136289,72.917998);
    private LatLng mapNoEntryLatLng9 = new LatLng(19.135347,72.9145);
    private LatLng mapNoEntryLatLng10 = new LatLng(19.137597,72.91509);

    final float half = (float) 0.5;
    private static final int PATTERN_DASH_LENGTH_PX = 30;
    private static final int PATTERN_GAP_LENGTH_PX = 30;
    private static final Dash DASH = new Dash(PATTERN_DASH_LENGTH_PX);
    private static final Gap GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    private static final List<PatternItem> PATTERN_DASHED = Arrays.asList(DASH, GAP);
    private static double lat = 0.0;
    private static double lon = 0.0;
    int x = 0;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private GoogleMap mMap;
    private UserLockBottomSheetBehavior mBottomSheetBehavior;
    private FloatingActionButton directionsAndLocationButton;
    private Location currentLocation;
    private Place selectedPlace;
    private TextView titleTextView;
    private TextView distanceTextView;
    private TextView timeTextView;
    private FloatingActionMenu fabMenu;
    private com.github.clans.fab.FloatingActionButton fabEvents;
    private com.github.clans.fab.FloatingActionButton fabEateries;
    private com.github.clans.fab.FloatingActionButton fabAccomodation;
    private com.github.clans.fab.FloatingActionButton fabToilets;
    private com.github.clans.fab.FloatingActionButton fabEntries;
    private List<Place> eventList = new ArrayList<>();
    private List<Place> entryList = new ArrayList<>();
    private List<Place> eateriesList = new ArrayList<>();
    private List<Place> accomodationList = new ArrayList<>();
    private List<Place> toiletList = new ArrayList<>();
    private GsonModels.Event launchEvent;
    private boolean firstLaunch = true;

    //For the schedule location venue, setting string venu
    private String venueString = null;
    LatLng eventLocation = null;




//
//    public MapFragment(GsonModels.Event launchEvent) {
//        this.launchEvent = launchEvent;
//    }

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mapView = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager()
                .findFragmentById(map));
        mapFragment.getMapAsync(this);
        getActivity().setTitle("Map");
        Bundle bundle = getArguments();
        if(bundle != null){
            venueString = bundle.getString("venueString");
        }
        return mapView;
    }

    @Override
    public void onStart() {
        super.onStart();
        directionsAndLocationButton = (FloatingActionButton) getActivity().findViewById(R.id.directions);
        if (getView() != null) {
            View bottomSheetView = getView().findViewById(R.id.bottom_sheet);
            mBottomSheetBehavior = (UserLockBottomSheetBehavior) UserLockBottomSheetBehavior.from(bottomSheetView);
        }
        mBottomSheetBehavior.setHideable(true);
        mBottomSheetBehavior.setState(UserLockBottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.setBottomSheetCallback(new UserLockBottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == UserLockBottomSheetBehavior.STATE_HIDDEN) {
                    directionsAndLocationButton.setImageResource(R.drawable.ic_my_location_black_24dp);
                    directionsAndLocationButton.getDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
                    directionsAndLocationButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        fabMenu = (FloatingActionMenu) getActivity().findViewById(R.id.fab_menu);
        fabMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabMenu.isOpened()) {
                    displayAll();
                }
                fabMenu.toggle(true);
            }
        });
        createCustomAnimation();

        fabEvents = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.fab_event);
        fabEateries = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.fab_eateries);
        fabAccomodation = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.fab_accomodation);
        fabToilets = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.fab_toilets);
        fabEntries = (com.github.clans.fab.FloatingActionButton) getActivity().findViewById(R.id.fab_entries);

        fabEvents.setOnClickListener(this);
        fabEateries.setOnClickListener(this);
        fabAccomodation.setOnClickListener(this);
        fabToilets.setOnClickListener(this);
        fabEntries.setOnClickListener(this);

        directionsAndLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mBottomSheetBehavior.getState() == UserLockBottomSheetBehavior.STATE_HIDDEN) {
                        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            return;
                        }
                        currentLocation = getLastKnownLocation();
                        if (currentLocation != null) {
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 17);
                            mMap.animateCamera(cameraUpdate);
                            directionsAndLocationButton.getDrawable().setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
                        }
                    } else {
                        if (selectedPlace != null) {
                            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + selectedPlace.getLatLng().latitude + "," + selectedPlace.getLatLng().longitude + "&mode=w");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            startActivity(mapIntent);
                        }
                    }
                } catch (Exception e){
                    checkLocationPermission();
                    Toast.makeText(getContext(), "Please turn on Location from the Settings", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

// Done with the getLastKnownLocation() function


    private Location getLastKnownLocation() {
        //Create a location manager object instance
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        //Get all the different providers which can give current location info
        List<String> providers = mLocationManager.getProviders(true);
        //Initialising the location to be null
        Location bestLocation = null;
        //Creating a for loop to go through all the location providers and get the location
        for (String provider : providers) {

            //if permission is not granted, then get the permission

            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            //Get the last known location from the data provider
            Location l = mLocationManager.getLastKnownLocation(provider);
            //If the last know location provided by the data provider is null then ignore the provider and move to the next one.
            if (l == null) {
                continue;
            }

            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//        LocationManager service = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
//        boolean enabled = service
//                .isProviderEnabled(LocationManager.GPS_PROVIDER);
//        if (!enabled) {
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent);
//        }

        mMap = googleMap;
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.map_style);
        mMap.setMapStyle(style);


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                if (fabMenu.isOpened()) {
//                    fabMenu.toggle(true);
//                }

            }
        });
        try {
            final ViewGroup parent = (ViewGroup) getView().findViewWithTag("GoogleMapMyLocationButton").getParent();
            parent.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        Resources r = getResources();
                        //convert our dp margin into pixels
                        int marginPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
                        // Get the map compass view
                        View mapCompass = parent.getChildAt(4);

                        // create layoutParams, giving it our wanted width and height(important, by default the width is "match parent")
                        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(mapCompass.getHeight(), mapCompass.getHeight());
                        // position on top right
                        rlp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                        rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                        //give compass margin
                        rlp.setMargins(marginPixels, marginPixels, marginPixels, marginPixels);
                        mapCompass.setLayoutParams(rlp);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkLocationPermission();
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        if (directionsAndLocationButton != null) {
            directionsAndLocationButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
        }

        eventList.add(new Place(new LatLng(19.131973, 72.914285), "Convocation Hall"));
        eventList.add(new Place(new LatLng(19.130735, 72.916900), "Lecture Hall Complex (LCH)"));
        eventList.add(new Place(new LatLng(19.132348, 72.915785), "LT PCSA"));
        eventList.add(new Place(new LatLng(19.134446, 72.912217), "Gymkhana Grounds"));
        eventList.add(new Place(new LatLng(19.133572, 72.913399), "NCC Grounds"));
        eventList.add(new Place(new LatLng(19.130480, 72.915724), "FC Kohli Auditorium (FCK)"));
        eventList.add(new Place(new LatLng(19.135574, 72.912930), "New Swimming Pool"));
        eventList.add(new Place(new LatLng(19.129113, 72.918408), "Kendriya Vidyalaya (KV)"));
        eventList.add(new Place(new LatLng(19.135422, 72.913674), "Student Activity Center (SAC)"));
        eventList.add(new Place(new LatLng(19.131651, 72.915758), "SJM SOM"));
        eventList.add(new Place(new LatLng(19.135045, 72.913401), "Open Air Theatre (OAT)"));
        eventList.add(new Place(new LatLng(19.132635, 72.915716), "MB Lawns"));
        eventList.add(new Place(new LatLng(19.130005, 72.916704), "Physics Parking Lot"));
        eventList.add(new Place(new LatLng(19.129574, 72.915394), "H10 T-Point"));
        eventList.add(new Place(new LatLng(19.132030, 72.915920), "PCSA Backlawns"));
        eventList.add(new Place(new LatLng(19.135771, 72.914428), "SAC Parking Lot"));
        eventList.add(new Place(new LatLng(19.134779, 72.912952), "SAC Backyard"));
        eventList.add(new Place(new LatLng(19.135572, 72.914017), "Old Swimming Pool"));

        entryList.add(new Place(new LatLng(19.132166, 72.915688), "LT PCSA Left Entry"));
        entryList.add(new Place(new LatLng(19.132187, 72.915962), "LT PCSA Right Entry"));
        entryList.add(new Place(new LatLng(19.132025, 72.91579), "SOM Well Entry"));
        entryList.add(new Place(new LatLng(19.131622, 72.915946), "SOM Well Back Entry"));
        entryList.add(new Place(new LatLng(19.131568, 72.915522), "SOM Well Front Entry"));
        entryList.add(new Place(new LatLng(19.129612, 72.918255), "Kendriya Vidyalaya (KV) Entry"));
        entryList.add(new Place(new LatLng(19.130681, 72.916198), "Lecture Hall Complex Entry 1"));
        entryList.add(new Place(new LatLng(19.130755, 72.917158), "Lecture Hall Complex Entry 2"));
        entryList.add(new Place(new LatLng(19.132199, 72.914562), "Convocation Hall Entry"));
        entryList.add(new Place(new LatLng(19.133134, 72.913513), "NCC Ground Entry"));
        entryList.add(new Place(new LatLng(19.135648, 72.913843), "SAC Entry"));
        entryList.add(new Place(new LatLng(19.13426, 72.910313), "Gymkhana Boys Entry"));
        entryList.add(new Place(new LatLng(19.13394, 72.911276), "Gymkhana Girls Entry"));
//        entryList.add(new Place(new LatLng(),));


        accomodationList.add(new Place(new LatLng(19.136110, 72.913904), "Hostel 1"));
        accomodationList.add(new Place(new LatLng(19.136357, 72.912555), "Hostel 2"));
        accomodationList.add(new Place(new LatLng(19.136231, 72.911482), "Hostel 3"));
        accomodationList.add(new Place(new LatLng(19.136142, 72.910456), "Hostel 4"));
        accomodationList.add(new Place(new LatLng(19.135235, 72.910237), "Hostel 5"));
        accomodationList.add(new Place(new LatLng(19.135361, 72.907071), "Hostel 6"));
        accomodationList.add(new Place(new LatLng(19.134910, 72.908062), "Hostel 7"));
        accomodationList.add(new Place(new LatLng(19.133758, 72.911147), "Hostel 8"));
        accomodationList.add(new Place(new LatLng(19.134966, 72.908400), "Hostel 9"));
        accomodationList.add(new Place(new LatLng(19.128322, 72.915731), "Hostel 10"));
        accomodationList.add(new Place(new LatLng(19.133495, 72.912053), "Hostel 11"));
        accomodationList.add(new Place(new LatLng(19.134811, 72.905217), "Hostel 12, 13, 14"));
        accomodationList.add(new Place(new LatLng(19.137539, 72.914096), "Hostel 15"));
        accomodationList.add(new Place(new LatLng(19.137731, 72.912796), "Hostel 16"));
        accomodationList.add(new Place(new LatLng(19.135883, 72.910248), "Tansa House"));
        accomodationList.add(new Place(new LatLng(19.135305, 72.913685), "Hospitality Desk"));

        eateriesList.add(new Place(new LatLng(19.134599, 72.910057), "The Campus Hub"));
        eateriesList.add(new Place(new LatLng(19.133698, 72.911471), "Brews & Bites"));
        eateriesList.add(new Place(new LatLng(19.129769, 72.915152), "Gulmohar"));
        eateriesList.add(new Place(new LatLng(19.134865, 72.913824), "SAC Food Court"));
        eateriesList.add(new Place(new LatLng(19.129861, 72.915798), "Day Food Court"));
        eateriesList.add(new Place(new LatLng(19.134377, 72.910707), "Concert Lounge"));
        eateriesList.add(new Place(new LatLng(19.135148, 72.905608), "Amul Parlour"));
        eateriesList.add(new Place(new LatLng(19.134948, 72.905138), "H12 Night Canteen"));
        eateriesList.add(new Place(new LatLng(19.131994, 72.915479), "Coffee Shack"));

        toiletList.add(new Place(new LatLng(19.12541, 72.909201), "Near Padmavati Devi Temple"));
        toiletList.add(new Place(new LatLng(19.125455, 72.916304), "Main Gate"));
        toiletList.add(new Place(new LatLng(19.130289, 72.915922), "Kresit Building T1"));
        toiletList.add(new Place(new LatLng(19.130688, 72.915843), "Kresit Building T2"));
        toiletList.add(new Place(new LatLng(19.130476, 72.916369), "LCH T1"));
        toiletList.add(new Place(new LatLng(19.130973, 72.91634), "LCH T2"));
        toiletList.add(new Place(new LatLng(19.130968, 72.916986), "LCH T3"));
        toiletList.add(new Place(new LatLng(19.130571, 72.917032), "LCH T4"));
        toiletList.add(new Place(new LatLng(19.130214, 72.916565), "Physics Department"));
        toiletList.add(new Place(new LatLng(19.130281, 72.917738), "Chemistry Department"));
        toiletList.add(new Place(new LatLng(19.130565, 72.917287), "LCH T5"));
        toiletList.add(new Place(new LatLng(19.131034, 72.917275), "LCH T6"));
        toiletList.add(new Place(new LatLng(19.131168, 72.916496), "MEMS Department"));
        toiletList.add(new Place(new LatLng(19.13122, 72.91781), "HSS Department"));
        toiletList.add(new Place(new LatLng(19.131673, 72.916578), "GG Building"));
        toiletList.add(new Place(new LatLng(19.13188, 72.916441), "Electrical Department"));
        toiletList.add(new Place(new LatLng(19.131903, 72.917321), "CTARA Department"));
        toiletList.add(new Place(new LatLng(19.132589, 72.91649), "Civil Department"));
        toiletList.add(new Place(new LatLng(19.13235, 72.917319), "VMCC"));
        toiletList.add(new Place(new LatLng(19.131741, 72.91582), "SOM"));
        toiletList.add(new Place(new LatLng(19.133325, 72.91644), "Mechanical Department"));
        toiletList.add(new Place(new LatLng(19.135648, 72.913701), "SAC"));
        toiletList.add(new Place(new LatLng(19.135568, 72.912728), "Swimming Pool"));
        toiletList.add(new Place(new LatLng(19.136331, 72.913784), "Hostel 1 A"));
        toiletList.add(new Place(new LatLng(19.136575, 72.912584), "Hostel 2 A"));
        toiletList.add(new Place(new LatLng(19.136365, 72.911449), "Hostel 3 A"));
        toiletList.add(new Place(new LatLng(19.135798, 72.911416), "Gymkhana Indoor"));
        toiletList.add(new Place(new LatLng(19.135754, 72.912397), "Gymkhana Badminton"));
        toiletList.add(new Place(new LatLng(19.136492, 72.910099), "Hostel 4 A"));
        toiletList.add(new Place(new LatLng(19.135555, 72.910177), "Tansa A"));
        toiletList.add(new Place(new LatLng(19.135771, 72.909998), "Tansa B"));
        toiletList.add(new Place(new LatLng(19.135187, 72.909738), "Hostel 5 A"));
        toiletList.add(new Place(new LatLng(19.135584, 72.908264), "Hostel 9 A"));
        toiletList.add(new Place(new LatLng(19.134359, 72.907746), "Hostel 7 A"));
        toiletList.add(new Place(new LatLng(19.135829, 72.907155), "Hostel 6 A"));
        toiletList.add(new Place(new LatLng(19.135463, 72.904687), "Hostel 12 B1"));
        toiletList.add(new Place(new LatLng(19.134532, 72.904711), "Hostel 13 A1"));
        toiletList.add(new Place(new LatLng(19.134768, 72.905867), "Hostel 14 B1"));


        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(19.131973, 72.914285), 17));
        List<LatLng> decodedPath = PolyUtil.decode(LakeSide1);


        CameraPosition cameraPosition;
        if (venueString != null) {
            for (Place place : eventList) {
                if (venueString.equals(place.getTitle())) {
                    eventLocation = place.getLatLng();
                    break;
                }
                Log.e("EventLocationNotPresent", "This Location is not present: " + venueString);
            }
            mMap.addMarker(new MarkerOptions().position(eventLocation).title(venueString).icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder)).anchor(half, half).zIndex(10));
            cameraPosition = new CameraPosition.Builder().target(eventLocation).zoom(18).tilt(70).build();
        } else {
            cameraPosition = new CameraPosition.Builder().target(new LatLng(19.131973, 72.914285)).zoom(18).tilt(70).build();
        }
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnCameraMoveListener(this);

        displayAll();




        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }



    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        if (launchEvent == null) {
            if (!firstLaunch) {
                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                //move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
            } else {
                firstLaunch = false;
            }
        }

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);

                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                return;
            }
        }
    }

    @Override
    public void onPause() {

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
//            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
        super.onPause();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        try {
            checkLocationPermission();
            getLastKnownLocation();
            selectedPlace = (Place) marker.getTag();
            distanceTextView = (TextView) getActivity().findViewById(R.id.bottom_sheet_distance);
            timeTextView = (TextView) getActivity().findViewById(R.id.bottom_sheet_time);
            if (distanceTextView != null) {
                distanceTextView.setText(null);
            }
            if (timeTextView != null) {
                timeTextView.setText(null);
            }
            directionsAndLocationButton.setImageResource(R.drawable.ic_directions_walk_black_24dp);
            directionsAndLocationButton.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.white), PorterDuff.Mode.SRC_IN);
            directionsAndLocationButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0D47A1")));

            titleTextView = (TextView) getActivity().findViewById(R.id.bottom_sheet_title);
            titleTextView.setText(selectedPlace.getTitle());
            currentLocation = getLastKnownLocation();
            LatLng markerLatLng = marker.getPosition();
            String markerLat = String.valueOf(markerLatLng.latitude);
            String markerLng = String.valueOf(markerLatLng.longitude);
            String selectedLat = String.valueOf(currentLocation.getLatitude());
            String selectedLng = String.valueOf(currentLocation.getLongitude());

            String distanceUrl = DIRECTION_URL_API + "origin=" + selectedLat + "," + selectedLng + "&destination=" + markerLat + "," + markerLng + "&key=" + "AIzaSyBW-BQ4JoYbq3LkiGwzUdPReem8Nhv-Q_g";
            new DistAsyncTask().execute(distanceUrl);



            mBottomSheetBehavior.setState(UserLockBottomSheetBehavior.STATE_COLLAPSED);
            return true;
        } catch (Exception e){
            checkLocationPermission();
            Toast.makeText(getContext(), "Please turn on Location from the Settings", Toast.LENGTH_SHORT).show();
            return false;

        }
    }


    @Override
    public void onCameraMove() {
        if (mBottomSheetBehavior.getState() == UserLockBottomSheetBehavior.STATE_HIDDEN) {
            directionsAndLocationButton.getDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorGray), PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void onClick(View v) {
        mMap.clear();
        List<Place> placeList = new ArrayList<>();
        int iconDrawable = 0;
        switch (v.getId()) {
            case R.id.fab_event:
                placeList = eventList;
                iconDrawable = R.drawable.blue_event_24;
                break;
            case R.id.fab_eateries:
                placeList = eateriesList;
                iconDrawable = R.drawable.blue_food_24;
                break;
            case R.id.fab_accomodation:
                placeList = accomodationList;
                iconDrawable = R.drawable.blue_bed_24;
                break;
            case R.id.fab_toilets:
                placeList = toiletList;
                iconDrawable = R.drawable.blue_toilet_24;
                break;
            case R.id.fab_entries:
                placeList = entryList;
                iconDrawable = R.drawable.blue_entry_24;
        }

        if(venueString != null){
            for(Place place : eventList){
                if(venueString.equals(place.getTitle())){
                    eventLocation = place.getLatLng();
                    break;
                }
                Log.e("EventLocationNotPresent", "This Location is not present: "+venueString);
            }
            mMap.addMarker(new MarkerOptions().position(eventLocation).title(venueString).icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder)).anchor(half,half).zIndex(10));
        }
        //Polyline
        List<LatLng> decodedPath1 = PolyUtil.decode(LakeSide1);
        mMap.addPolyline(new PolylineOptions()
                .addAll(decodedPath1)
                .color(getContext().getResources().getColor(R.color.roadRed)).width(15).pattern(PATTERN_DASHED));
        List<LatLng> decodedPath2 = PolyUtil.decode(LakeSide2);
        mMap.addPolyline(new PolylineOptions()
                .addAll(decodedPath2)
                .color(getContext().getResources().getColor(R.color.roadRed)).width(15).pattern(PATTERN_DASHED));
        List<LatLng> decodedPath3 = PolyUtil.decode(LakeSide3);
        mMap.addPolyline(new PolylineOptions()
                .addAll(decodedPath3)
                .color(getContext().getResources().getColor(R.color.roadRed)).width(15).pattern(PATTERN_DASHED));
        List<LatLng> decodedPath4 = PolyUtil.decode(HillSide1);
        mMap.addPolyline(new PolylineOptions()
                .addAll(decodedPath4)
                .color(getContext().getResources().getColor(R.color.roadRed)).width(15).pattern(PATTERN_DASHED));
        List<LatLng> decodedPath5 = PolyUtil.decode(HillSide2);
        mMap.addPolyline(new PolylineOptions()
                .addAll(decodedPath5)
                .color(getContext().getResources().getColor(R.color.roadRed)).width(15).pattern(PATTERN_DASHED));
//        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng1).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
//        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng2).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng3).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng4).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng5).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng6).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng7).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng8).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng9).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng10).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));



        for (Place place : placeList) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(BitmapDescriptorFactory.fromResource(iconDrawable)).anchor(0.5f, 0.5f));
            marker.setTag(place);
        }
        fabMenu.close(true);
    }

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(fabMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                fabMenu.getMenuIconView().setImageResource(fabMenu.isOpened()
                        ? R.drawable.ic_search_white_24dp : R.drawable.ic_close_white_24dp);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        fabMenu.setIconToggleAnimatorSet(set);
    }

    public void displayAll() {
        mMap.clear();
        if(venueString != null){
            for(Place place : eventList){
                if(venueString.equals(place.getTitle())){
                    eventLocation = place.getLatLng();
                    break;
                }
                Log.e("EventLocationNotPresent", "This Location is not present: "+venueString);
            }
            mMap.addMarker(new MarkerOptions().position(eventLocation).title(venueString).icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder)).anchor(half,half).zIndex(10));
        }
        //Polyline

        List<LatLng> decodedPath1 = PolyUtil.decode(LakeSide1);
        mMap.addPolyline(new PolylineOptions()
                .addAll(decodedPath1)
                .color(getContext().getResources().getColor(R.color.roadRed)).width(15).pattern(PATTERN_DASHED));
        List<LatLng> decodedPath2 = PolyUtil.decode(LakeSide2);
        mMap.addPolyline(new PolylineOptions()
                .addAll(decodedPath2)
                .color(getContext().getResources().getColor(R.color.roadRed)).width(15).pattern(PATTERN_DASHED));
        List<LatLng> decodedPath3 = PolyUtil.decode(LakeSide3);
        mMap.addPolyline(new PolylineOptions()
                .addAll(decodedPath3)
                .color(getContext().getResources().getColor(R.color.roadRed)).width(15).pattern(PATTERN_DASHED));
        List<LatLng> decodedPath4 = PolyUtil.decode(HillSide1);
        mMap.addPolyline(new PolylineOptions()
                .addAll(decodedPath4)
                .color(getContext().getResources().getColor(R.color.roadRed)).width(15).pattern(PATTERN_DASHED));
        List<LatLng> decodedPath5 = PolyUtil.decode(HillSide2);
        mMap.addPolyline(new PolylineOptions()
                .addAll(decodedPath5)
                .color(getContext().getResources().getColor(R.color.roadRed)).width(15).pattern(PATTERN_DASHED));
//        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng1).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
//        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng2).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng3).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng4).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng5).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng6).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng7).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng8).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng9).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        mMap.addMarker(new MarkerOptions().position(mapNoEntryLatLng10).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_no_entry)).anchor(half, half));
        final float half = (float) 0.5;
        for (Place place : eventList) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_event_24)).anchor(half, half));
            marker.setTag(place);
        }
        for (Place place : eateriesList) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_food_24)).anchor(half, half));
            marker.setTag(place);
        }
        for (Place place : accomodationList) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_bed_24)).anchor(half, half));
            marker.setTag(place);
        }
        for (Place place : toiletList) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_toilet_24)).anchor(half, half));
            marker.setTag(place);
        }
        for (Place place : entryList) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(place.getLatLng()).icon(BitmapDescriptorFactory.fromResource(R.drawable.blue_entry_24)).anchor(half, half));
            marker.setTag(place);
        }
    }


    private void parseJSon(String data) throws JSONException {
        if (data == null)
            return;

        List<Routes> routes = new ArrayList<Routes>();
        JSONObject jsonData = new JSONObject(data);
        JSONArray jsonRoutes = jsonData.getJSONArray("routes");
        for (int i = 0; i < jsonRoutes.length(); i++) {
            JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
            Routes route = new Routes();

            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
            JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
            JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
//            JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
//            JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");

            route.distance = new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"));
            route.duration = new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"));
            route.endAddress = jsonLeg.getString("end_address");
            route.startAddress = jsonLeg.getString("start_address");
//            route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
//            route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));


            routes.add(route);
        }
        for (Routes routes1 : routes) {
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) getActivity().findViewById(R.id.bottom_sheet_time)).setText("Time: " + routes1.duration.text);
            ((TextView) getActivity().findViewById(R.id.bottom_sheet_distance)).setText("Distance: " + routes1.distance.text);

        }

    }
    private class DistAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(link);
                InputStream is = url.openConnection().getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                parseJSon(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }






}