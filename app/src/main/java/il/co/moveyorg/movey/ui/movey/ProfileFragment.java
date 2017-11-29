package il.co.moveyorg.movey.ui.movey;


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
import il.co.moveyorg.movey.ui.auth.EditUserDetailsActivity;
import il.co.moveyorg.movey.ui.base.BaseFragment;
import io.reactivex.functions.Consumer;
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider;
import timber.log.Timber;

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
        Timber.i("Profile Fragment -> User:" + firebaseAuth.getCurrentUser().getUid());
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, view);


        userEmail.setText(user.getEmail());
        logoutBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);

        mapView.onCreate(savedInstanceState);

        userDbRef =
                FirebaseDatabase.getInstance().getReference().child("social").child("users")
                        .child(firebaseAuth.getCurrentUser().getUid());

        userDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                currentUser = dataSnapshot.getValue(User.class);
                userName.setText(currentUser.getUserName());
                userFirstName.setText(currentUser.getFirstName());
                userLastName.setText(currentUser.getLastName());
                userCountry.setText(currentUser.getCountry());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        // Gets to GoogleMap from the MapView and does initialization stuff
        askForLocationPermission();
        return view;
    }

    public void askForLocationPermission() {

        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
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
                startActivity(new Intent(getActivity(), EditUserDetailsActivity.class));
                break;
            }

        }
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {
        Toast.makeText(getActivity(), "Location permission granted!", Toast.LENGTH_SHORT).show();
        mapView.getMapAsync(this);
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
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.setMyLocationEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(getActivity());

        locationProvider.getLastKnownLocation()
                .subscribe(new Consumer<Location>() {
                    @Override
                    public void accept(Location location) throws Exception {

                        Toast.makeText(getActivity(), "Got current location", Toast.LENGTH_SHORT).show();
                        // Updates the location and zoom of the MapView
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 10);
                        map.animateCamera(cameraUpdate);
                    }

                });


    }
}
