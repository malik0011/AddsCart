package com.as2developers.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.as2developers.myapplication.Modals.UserModal;
//import com.as2developers.myapplication.databinding.MenuHeaderBinding;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SelectLocationFromMap extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener,GoogleMap.OnMarkerDragListener,OnMapReadyCallback,GoogleMap.OnMapClickListener {
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 1000;
    private static final int REQUEST_CHECK_SETTINGS = 100;

    //clients accessable point
    // 15.772946, 74.450248 down || 15.959346, 74.565029 this for up

    Double Dlat = 15.772946;
    Double Dlon = 74.450248;
    Double Tlat = 15.959346;
    Double Tlon = 74.565029;
    //Initializing the variable
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    String latS,lonS;
    ImageView searchBtn;
    SearchView searchView;
    LatLng home;
    private List<Place.Field> fields;
    final int place_piker_req_code = 1;
    String LocationName;
    LatLng latLngGlobal;
    GoogleMap googleMapGlobal;
    private Marker markerGlobal;
    private Double homeLat,homeLon;
    String  myLocationName;
    BottomSheetDialog sheetDialog;
    Double latGlobal,lonGlobal;
    MarkerOptions optionsGlobal;
    RadioGroup radioGroup;
    String radioValue;
    Button next,continueBtn;
    String radioS,finalLocation,userLocality,UserAddressLine;
    TextInputEditText uLocality,uAddressLine;
    ImageButton ImgBtn;
    //for slide navigation bar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView WelcomeUser;
    private static final int REQUEST_CALL =1;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference ref;
    private static final int REQUEST_ENABLE_GPS = 516;

    //for turing on location
    private LocationRequest locationRequest;
    public static  final int REQUEST_CHECK_SETTING = 1001;

    // changes by Bala
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location_from_map);
        //hooks for navigation bar
        Toast.makeText(this, "Wait a while till it fetches your location..", Toast.LENGTH_SHORT).show();
        //init();
        drawerLayout =findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.dummy_content,R.string.dummy_content);
        drawerLayout.addDrawerListener(toggle);
        //toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        continueBtn = (Button) findViewById(R.id.continueBtn);
        //end
        //uploading req. value to database
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            ref = database.getReference("Users").child(user.getPhoneNumber());
        }

        //to be done...

        searchBtn = (ImageView)findViewById(R.id.searchBtn);
        searchView = (SearchView)findViewById(R.id.searchView);
        ImgBtn = findViewById(R.id.Img);

        //if location is turn off the turn it on
        searchView.clearFocus();
        searchView.setFocusable(false);
        //noe assigning the variable
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync((OnMapReadyCallback) this);

        //initialize the fused location
        client = LocationServices.getFusedLocationProviderClient(this);
        ImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                WelcomeUser = navigationView.findViewById(R.id.Welcome_Note);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModal user01 = snapshot.getValue(UserModal.class);
                        String userName = user01.getName();
                        WelcomeUser.setText(String.format("Welcome %s", userName));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

    }

    private void TurnOnLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        /*if (!isGpsEnabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivityForResult(intent, REQUEST_ENABLE_GPS);
        }*/
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext()).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                   // Toast.makeText(SelectLocationFromMap.this, "Gps is On in your device!", Toast.LENGTH_SHORT).show();
                    //getCurrentLocation();
                } catch (ApiException e) {
                    switch (e.getStatusCode()){
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(SelectLocationFromMap.this,REQUEST_CHECK_SETTING);
                            } catch (IntentSender.SendIntentException sendIntentException) {
                                sendIntentException.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            }
        });
    }
    //for drawable
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CHECK_SETTING){
            switch (resultCode){
                case Activity.RESULT_OK:
                   // Toast.makeText(this, "GPS is turing on..", Toast.LENGTH_SHORT).show();
                    //getCurrentLocation();
                    /*startActivity(new Intent(this,SelectLocationFromMap.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();*/
                    break;
                case Activity.RESULT_CANCELED:
                   // Toast.makeText(this, "GPS have to be turn on..", Toast.LENGTH_SHORT).show();
                    break;

            }
        }

        switch (requestCode)
        {
            case place_piker_req_code:
                Place place = Autocomplete.getPlaceFromIntent(data);
                LocationName = place.getName();
                latLngGlobal = place.getLatLng();
                MarkerOptions options = new MarkerOptions().position(latLngGlobal).title(LocationName);
                googleMapGlobal.moveCamera(CameraUpdateFactory.newLatLng(latLngGlobal));
                //now zoom into the map
                googleMapGlobal.animateCamera(CameraUpdateFactory.newLatLngZoom(latLngGlobal,17)); //   <<---here we can change the ZOOM ratio..

                //adding marker on map
                googleMapGlobal.addMarker(options).setIcon(BitmapFromVector(getApplicationContext(), R.drawable.ic_location));
                googleMapGlobal.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
        }
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CHECK_SETTINGS) {
            startLocationUpdates();
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==44){
            if(grantResults.length > 0  && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //when permission granted call method
                //getCurrentLocation();
            }else{
                Toast.makeText(this, "Location Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==REQUEST_CALL){
            if(grantResults.length > 0  && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //when permission granted call method
                makePhoneCall();
            }else{
                Toast.makeText(this, "Call Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void HomeLocation(View view) {
        //if someone click on the search button
        if(home != null){
            latLngGlobal = home;
            googleMapGlobal.animateCamera(CameraUpdateFactory.newLatLngZoom(home,17));
            if(markerGlobal!=null) markerGlobal.remove();
            googleMapGlobal.clear();
            latLngGlobal = home;


            //creating marker options
            int height = 100;
            int width = 100;
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable. pin);
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
            BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);
            MarkerOptions options = new MarkerOptions().position(home).title("Hold & Drag To Move Pin!").draggable(true).icon(smallMarkerIcon);
            optionsGlobal = options;
            //now zoom into the map
            googleMapGlobal.animateCamera(CameraUpdateFactory.newLatLngZoom(home,17)); //   <<---here we can change the ZOOM ratio..
            //adding marker on map
            googleMapGlobal.addMarker(options).showInfoWindow();
            latGlobal = homeLat;
            lonGlobal = homeLon;

            latS = Double.toString(homeLat);
            lonS = Double.toString(homeLon);
            Toast.makeText(this, "Your current location!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Fetching current location please wait!", Toast.LENGTH_SHORT).show();
        }
    }

    public void Continue(View view) {
        //when someone clicked on the Continue button
        //calling bottomSheetLayout
        if(Dlat< latGlobal && latGlobal < Tlat && Dlon < lonGlobal && lonGlobal <Tlon){// if the selected location in our service area then
            sheetDialog = new BottomSheetDialog(SelectLocationFromMap.this,R.style.BottomSheetStyle);

            View v = LayoutInflater.from(SelectLocationFromMap.this).inflate(R.layout.location_confirm,(LinearLayout)findViewById(R.id.sheet));
            sheetDialog.setContentView(v);

            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latGlobal,lonGlobal,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String country = addresses.get(0).getCountryName();
            String locality = addresses.get(0).getLocality();
            String name = addresses.get(0).getAdminArea();
            String pin = addresses.get(0).getPostalCode();
            TextInputEditText editText = sheetDialog.findViewById(R.id.yourLocation);

            //to share data to an another activity
            finalLocation = locality+","+name+","+country+","+pin;
            editText.setText(finalLocation);

            //sheetDialog.show();
            addAddressToFirebase(finalLocation);
           // Toast.makeText(this,  "lat: "+latS+", lan: "+lonS+" LocationName: "+latLngGlobal, Toast.LENGTH_SHORT).show();

            uLocality = (TextInputEditText) sheetDialog.findViewById(R.id.UserLocality);
            uAddressLine = (TextInputEditText) sheetDialog.findViewById(R.id.edtxt_addressLine);

            next = (Button) sheetDialog.findViewById(R.id.nextBtn);
            radioGroup = (RadioGroup) sheetDialog.findViewById(R.id.radio_Group);
            sheetDialog.show();
            radioS ="Home";
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i){
                        case R.id.radio_home:
                            radioS = "Home";
                            break;
                        case R.id.ratio_office:
                            radioS = "Office";
                            break;
                        case R.id.radio_shop:
                            radioS = "Shop";
                            break;
                        case R.id.radio_outlet:
                            radioS = "Outlet/Mall";
                            break;
                    }
                }
            });
            userLocality = uLocality.getText().toString();
            UserAddressLine = uAddressLine.getText().toString();
            //next button
            next = (Button) sheetDialog.findViewById(R.id.nextBtn);
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(SelectLocationFromMap.this,FormFillupActivity.class);
                        //passing the value
                        //getting some value
                        userLocality = uLocality.getText().toString();
                        UserAddressLine = uAddressLine.getText().toString();
                        finalLocation = editText.getText().toString();
                        i.putExtra("Latitude",Double.toString(latGlobal));
                        i.putExtra("Longitude",Double.toString(lonGlobal));
                        i.putExtra("locationType",radioS);
                        i.putExtra("LocationDetails",finalLocation);
                        i.putExtra("pin",pin);
                        i.putExtra("locality",userLocality);
                        i.putExtra("longAddress",UserAddressLine);

                        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
                        myEdit.putString("Latitude", Double.toString(latGlobal));
                        myEdit.putString("Longitude", Double.toString(lonGlobal));
                        myEdit.putString("locationType",radioS);
                        myEdit.putString("LocationDetails",finalLocation);
                        myEdit.putString("pin", pin);
                        myEdit.putString("locality",userLocality);
                        myEdit.putString("longAddress", UserAddressLine);


// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
                        myEdit.commit();

                        //now if location type selected then only go to next activity
                        //also we have to select the house number
//
                            if(userLocality.length()>0){
                                startActivity(i);
//                                Toast.makeText(SelectLocationFromMap.this, radioS, Toast.LENGTH_SHORT).show();
                                //System.out.println(userLocality+"  "+UserAddressLine);
                            }else{
                                Toast.makeText(SelectLocationFromMap.this, "Please enter H/No,Plot No!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    //}
                });


        }else{
            Toast.makeText(this, "Sorry!Currently our services are not available in this area!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addAddressToFirebase(String finalLocation) {
        ref.child("address").setValue(finalLocation);
        ref.child("latitude").setValue(Double.toString(latGlobal));
        ref.child("longitude").setValue(Double.toString(lonGlobal));
    }

    private void hideKeyBoard(EditText editText) {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(editText.getApplicationWindowToken(),0);
    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent i = new Intent(getApplicationContext(),ProfilePage.class);
                startActivity(i);
                break;
            case R.id.howItWorks:
                startActivity(new Intent(this,HowItWorks.class));
                break;
            case R.id.aboutUs:
                startActivity(new Intent(this,AboutUs.class));
                break;
            case R.id.call_us:
                makePhoneCall();
                break;
            case R.id.home:
                Toast.makeText(this, "You are at Home!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logOut:
                Toast.makeText(this, "Logging out..", Toast.LENGTH_SHORT).show();
                //Toast.makeText(SelectLocationFromMap.this, "Back to Home Page", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(SelectLocationFromMap.this,Login_Phone.class));
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void makePhoneCall(){

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else{
            String phoneNo = "tel:"+"8867825522";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(phoneNo));
            startActivity(intent);
        }
    }

    private void init(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();
    }

    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /**
     * Creates a callback for receiving location events.
     */
    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                removeLocationUpdate();
                getCurrentLocation(locationResult.getLastLocation());
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
            }
        };
    }

    /**
     * Uses a {@link com.google.android.gms.location.LocationSettingsRequest.Builder} to build
     * a {@link com.google.android.gms.location.LocationSettingsRequest} that is used for checking
     * if a device has the needed location settings.
     */
    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }

    @SuppressLint("MissingPermission")
    protected void startLocationUpdates() {
        //Log.i("service", "start location updates");
        // Begin by checking if the device has the necessary location settings.

        Toast.makeText(this, "Please wait we fetching your data..", Toast.LENGTH_SHORT).show();
        
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this,
                        locationSettingsResponse -> mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper()))
                .addOnFailureListener(this, e -> {
                    int statusCode = ((ApiException) e).getStatusCode();
                    switch (statusCode) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the
                                // result in onActivityResult().

                                ResolvableApiException rae = (ResolvableApiException) e;
                                rae.startResolutionForResult(SelectLocationFromMap.this, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sie) {
                                //Log.i(TAG, "PendingIntent unable to execute request.");
                            } catch (Exception exception){
                                e.printStackTrace();
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            String errorMessage = "Location settings are inadequate, and cannot be " +
                                    "fixed here. Fix in Settings.";
                            Toast.makeText(SelectLocationFromMap.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                });
    }

    private void removeLocationUpdate() {
        if (mFusedLocationClient != null && mLocationRequest != null && mLocationCallback != null)
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    private void getCurrentLocation(Location location){
        //sync map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            //added after
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                //All things are ready here to show the maps
                googleMapGlobal = googleMap; // puting this in global

                //Initialize lat lng
                LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

                //making home location global
                home = latLng;
                latLngGlobal = home;
                latGlobal = location.getLatitude();
                lonGlobal = location.getLongitude();

                homeLat = latGlobal;
                homeLon = lonGlobal;


                //code for resizing the marker in map
                int height = 100;
                int width = 100;
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.pin);
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);


                //creating marker options and adding custom marker
                MarkerOptions options = new MarkerOptions().position(latLng).title("Hold & Drag To Move Pin!").draggable(true).icon(smallMarkerIcon);
                optionsGlobal = options;
                //now zoom into the map
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17)); //   <<---here we can change the ZOOM ratio..
                //adding marker on map
                googleMap.addMarker(options).showInfoWindow();
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                continueBtn.setVisibility(View.VISIBLE);
                //making the data global to share in there activity
                latS = Double.toString(location.getLatitude());
                lonS = Double.toString(location.getLongitude());


                //when some one click on the search place options
                //places.initialize places

                fields = Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.LAT_LNG);

                Places.initialize(getApplicationContext(),"AIzaSyDR65hxh0Qrv3Xsb775Q_BSUgG_AvhkNEA"); //we have to put place api key here
                //create a new place cline instance
                PlacesClient placesClient = Places.createClient(getApplicationContext());
                System.out.println(homeLat+" "+homeLon);
                continueBtn.setEnabled(true);
//                continueBtn.setEnabled(true);
            }

        });
        googleMapGlobal.setOnMarkerDragListener(this);
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {
        latGlobal = marker.getPosition().latitude;
        lonGlobal = marker.getPosition().longitude;
        Toast.makeText(this, "Location changed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMapGlobal = googleMap;
        //scearchview code
        init();
        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        //asking the user to turn on the location
                        startLocationUpdates();

                    }


                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if(location!=null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(SelectLocationFromMap.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(addressList.isEmpty()){
                        Toast.makeText(SelectLocationFromMap.this, "Can't find this location,search nearby locations!", Toast.LENGTH_SHORT).show();
                    }else {
                        if(markerGlobal!=null){ //to remove previous location marker
                            markerGlobal.remove();
                        }
                        googleMapGlobal.clear();
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        latLngGlobal = latLng;
                        latGlobal = latLng.latitude;
                        lonGlobal = latLng.longitude;

                        //creating marker options
                        int height = 100;
                        int width = 100;
                        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable. pin);
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                        BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);
                        //adding it
                        MarkerOptions options = new MarkerOptions().position(latLng).title("Hold & Drag To Move Pin!").draggable(true).icon(smallMarkerIcon);
                        optionsGlobal = options;
                        //now zoom into the map
                        googleMapGlobal.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17)); //   <<---here we can change the ZOOM ratio..
                        //adding marker on map
                        googleMapGlobal.addMarker(options).showInfoWindow();
                        // googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


                        //making the data global to share in there activity
                        latS = Double.toString(latGlobal);
                        lonS = Double.toString(lonGlobal);


                    }

                }
                return false;
            }


            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        googleMapGlobal.setOnMarkerDragListener(this);
        googleMapGlobal.setOnMapClickListener(this);
        continueBtn.setEnabled(true);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        if(markerGlobal!=null && optionsGlobal!=null){ //to remove previous location marker
            markerGlobal.remove();
        }
        googleMapGlobal.clear();
//        //creating marker options
//        markerGlobal = googleMapGlobal.addMarker(new MarkerOptions().position(latLng).draggable(true));
//        MarkerOptions options = new MarkerOptions().position(latLng).title("Your selected location!").draggable(true);
//         optionsGlobal = options;
//        //now zoom into the map
//        googleMapGlobal.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17)); //   <<---here we can change the ZOOM ratio..
//        //adding marker on map
//        googleMapGlobal.addMarker(options).setIcon(BitmapFromVector(getApplicationContext(), R.drawable.ic_location));
//        latGlobal = latLng.latitude;
//        lonGlobal = latLng.longitude;
//
//        //making the data global to share in there activity
//        latS = Double.toString(latLng.latitude);
//        lonS = Double.toString(latLng.longitude);

        latLngGlobal = latLng;
        latGlobal = latLng.latitude;
        lonGlobal = latLng.longitude;

        //creating marker options and resize it
        int height = 100;
        int width = 100;
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable. pin);
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);
        //adding it
        MarkerOptions options = new MarkerOptions().position(latLng).title("Hold & Drag To Move Pin!").draggable(true).icon(smallMarkerIcon);
        optionsGlobal = options;
        //now zoom into the map
        googleMapGlobal.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17)); //   <<---here we can change the ZOOM ratio..
        //adding marker on map
        googleMapGlobal.addMarker(options).showInfoWindow();
       // googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


        //making the data global to share in there activity
        latS = Double.toString(latGlobal);
        lonS = Double.toString(lonGlobal);

    }
}
