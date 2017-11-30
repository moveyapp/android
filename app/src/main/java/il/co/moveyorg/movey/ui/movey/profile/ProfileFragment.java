package il.co.moveyorg.movey.ui.movey.profile;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.data.model.User;
import il.co.moveyorg.movey.ui.auth.editprofile.EditProfileActivity;
import il.co.moveyorg.movey.ui.base.BaseFragment;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements View.OnClickListener, PermissionListener, OnMapReadyCallback {

    private FirebaseAuth firebaseAuth;

    @BindView(R.id.fragment_profile_map_view)
    MapView mapView;
    GoogleMap map;

    @BindView(R.id.fragment_profile_image)
    ImageView userImage;

    @BindView(R.id.fragment_profile_email_text)
    TextView userEmail;

    @BindView(R.id.fragment_profile_username_text)
    TextView userName;

    @BindView(R.id.fragment_profile_first_name_text)
    TextView userFirstName;

    @BindView(R.id.fragment_profile_last_name_text)
    TextView userLastName;

    @BindView(R.id.fragment_profile_country_text)
    TextView userCountry;

    @BindView(R.id.fragment_profile_logout_button)
    Button logoutBtn;

    @BindView(R.id.fragment_profile_reset_password_button)
    Button resetPassBtn;

    @BindView(R.id.fragment_profile_edit_button)
    Button editBtn;



    private DatabaseReference userDbRef;
    private User currentUser;
    private boolean locationPermissionGranted = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MapsInitializer.initialize(getActivity());

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
        }
        initializeMap();
    }

    private void initializeMap() {
        if ( locationPermissionGranted && mapView != null) {
            mapView.getMapAsync(this);
            //setup markers etc...
        }
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);


        userEmail.setText(user.getEmail());
        logoutBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);


        userDbRef =
                FirebaseDatabase.getInstance().getReference().child("social").child("users")
                        .child(firebaseAuth.getCurrentUser().getUid());

        userDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                currentUser = dataSnapshot.getValue(User.class);

                if(currentUser != null) {
                    userName.setText(currentUser.getUserName());
                    userFirstName.setText(currentUser.getFirstName());
                    userLastName.setText(currentUser.getLastName());
                    userCountry.setText(currentUser.getCountry());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        mapView.onCreate(savedInstanceState);

        initializeMap();

        // Gets to GoogleMap from the MapView and does initialization stuff
        askForLocationPermission();
        return view;
    }

    public void askForLocationPermission() {

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(this)
                .check();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_profile_logout_button: {
                firebaseAuth.signOut();
                break;
            }
            case R.id.fragment_profile_edit_button: {
                startActivity(new Intent(getActivity(), EditProfileActivity.class));
                break;
            }

        }
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {
        locationPermissionGranted = true;
        initializeMap();
        Toast.makeText(getActivity(), "Location permission granted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {

    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(!locationPermissionGranted) return;
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);

        LocationRequest request = LocationRequest.create() //standard GMS LocationRequest
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(5)
                .setInterval(100);

        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(getActivity());

        Disposable subscription = locationProvider.getUpdatedLocation(request)
                .subscribe(new Consumer<Location>() {
                    @Override
                    public void accept(Location location) throws Exception {
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10);
                        map.animateCamera(cameraUpdate);
                    }
                });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        initializeMap();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
