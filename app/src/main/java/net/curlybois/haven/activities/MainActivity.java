package net.curlybois.haven.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import net.curlybois.haven.R;
import net.curlybois.haven.adapters.ShelterAdapter;
import net.curlybois.haven.controllers.SheltersController;
import net.curlybois.haven.dialogs.FilterDialog;
import net.curlybois.haven.interfaces.FilterDialogListener;
import net.curlybois.haven.interfaces.ShelterListClickListener;
import net.curlybois.haven.model.Shelter;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Allows the user to search for shelters
 */
public class MainActivity extends AppCompatActivity implements ShelterListClickListener,
        FilterDialogListener, OnMapReadyCallback {

    private static final int MY_LOCATION_REQUEST_CODE = 0;
    private static final int ZOOM_LEVEL = 11;

    @BindView(R.id.results_rv) private RecyclerView results_rv;
    @BindView(R.id.search_txe) private EditText search_txe;
    @BindView(R.id.filter_btn) private ImageButton filter_btn;

    private ShelterAdapter resultsAdapter;
    private SheltersController sheltersController;
    private FilterDialog filterDialog;
    private GoogleMap googleMap;
    private FusedLocationProviderClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize controller
        sheltersController = SheltersController.getInstance(this);

        // Views setup
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        filterDialog = FilterDialog.newInstance(sheltersController.getFilterQuery());

        // Map setup
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationClient = LocationServices.getFusedLocationProviderClient(this);

        // Results list setup
        resultsAdapter = new ShelterAdapter(sheltersController.getShelters(), this);
        results_rv.setAdapter(resultsAdapter);
        results_rv.setLayoutManager(new LinearLayoutManager(this));
        results_rv.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        // Listeners
        search_txe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sheltersController.setNameFilter(search_txe.getText().toString());
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.setArguments(FilterDialog.bundleArgs(
                        sheltersController.getFilterQuery()));
                filterDialog.show(getSupportFragmentManager(), "filters");
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Set up our map
        this.googleMap = googleMap;

        // Enable location if we have permission, otherwise ask
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            enableLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_LOCATION_REQUEST_CODE);
        }

        // Initialize filters now that our map is up
        applyFilters();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_LOCATION_REQUEST_CODE) {
            if ((permissions.length == 1)
                    && permissions[0].equals(Manifest.permission.ACCESS_COARSE_LOCATION)
                    && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                enableLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void enableLocation() {
        this.googleMap.setMyLocationEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void shelterListClicked(Shelter shelter) {
        Intent intent = new Intent(MainActivity.this, ShelterDetailActivity.class);
        intent.putExtra(ShelterDetailActivity.EXTRAS_SHELTER, shelter);
        startActivity(intent);
    }

    @Override
    public void setGender(Shelter.Gender gender) {
        sheltersController.setGenderFilter(gender);
    }

    @Override
    public void setAge(Shelter.Age age) {
        sheltersController.setAgeFilter(age);
    }

    @Override
    public void setVeterans(boolean veterans) {
        sheltersController.setVeteransFilter(veterans);
    }

    @Override
    public void applyFilters() {
        resultsAdapter.filter(sheltersController.getFilterQuery());
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                resultsAdapter.sortByNearest(location);
                            }
                        }
                    });
        }

        googleMap.clear();
        Collection<Marker> markers = new ArrayList<>();
        for (Shelter shelter : resultsAdapter.getSheltersFiltered()) {
            markers.add(googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(shelter.getLatitude(), shelter.getLongitude()))
                    .title(shelter.getName())
                    .snippet(shelter.getPhone())));
        }

        if (!markers.isEmpty()) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (Marker marker : markers) {
                builder.include(marker.getPosition());
            }
            LatLngBounds bounds = builder.build();
            CameraUpdate center = CameraUpdateFactory.newLatLng(bounds.getCenter());
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(ZOOM_LEVEL);
            googleMap.moveCamera(center);
            googleMap.animateCamera(zoom);
        }
    }
}
