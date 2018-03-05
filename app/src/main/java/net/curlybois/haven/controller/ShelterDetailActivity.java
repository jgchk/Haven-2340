package net.curlybois.haven.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import net.curlybois.haven.R;
import net.curlybois.haven.model.Shelter;

/**
 * Created by jessieprice on 2/28/18.
 */

// Should display attributes of shelter that was clicked.

public class ShelterDetailActivity extends AppCompatActivity {
    private TextView name, capacity, restrictions, lon, addr, notes, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);
        Shelter shelter = (Shelter) getIntent().getSerializableExtra("Shelter");
        System.out.println(shelter);
        name = findViewById(R.id.shelter_name);
        capacity = findViewById(R.id.shelter_capacity);
        restrictions = findViewById(R.id.shelter_restrictions);
        lon = findViewById(R.id.shelter_longitude);
        addr = findViewById(R.id.shelter_address);
        notes = findViewById(R.id.shelter_notes);
        phone = findViewById(R.id.shelter_phone);
        name.setText("Name: " + shelter.getName());
        capacity.setText("Capacity: " + shelter.getCapacity());
        restrictions.setText("Restrictions: " + shelter.getRestrictions());
        lon.setText("Coordinates: " + Float.toString(shelter.getLongitude()) + ", " + Float.toString(shelter.getLatitude()));
        addr.setText("Address: " + shelter.getAddress());
        notes.setText("Notes: " + shelter.getNotes());
        phone.setText("Phone: " + shelter.getPhone());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        actionBar.setTitle(shelter.getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, ShelterListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

