package net.curlybois.haven.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import net.curlybois.haven.R;
import net.curlybois.haven.model.Shelter;

/**
 * Created by jessieprice on 2/28/18.
 */

// Should display attributes of shelter that was clicked.

public class ShelterDetailActivity extends AppCompatActivity {
    TextView name;
    TextView capacity;
    TextView restrictions;
    TextView lon;
    TextView addr;
    TextView notes;
    TextView phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);
        Shelter shelter = (Shelter) getIntent().getSerializableExtra("Shelter");
        System.out.println(shelter);
        capacity = findViewById(R.id.shelter_capacity);
        restrictions = findViewById(R.id.shelter_restrictions);
        lon = findViewById(R.id.shelter_longitude);
        addr = findViewById(R.id.shelter_address);
        notes = findViewById(R.id.shelter_notes);
        phone = findViewById(R.id.shelter_phone);
        capacity.setText("Capacity: " + shelter.getCapacity());
        restrictions.setText("Restrictions: " + shelter.getRestrictionListAsString());
        lon.setText("Coordinates: " + Float.toString(shelter.getLongitude()) + ", " + Float.toString(shelter.getLatitude()));
        addr.setText("Address: " + shelter.getAddress());
        notes.setText("Notes: " + shelter.getNotes());
        phone.setText("Phone: " +shelter.getPhone());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(shelter.getName());
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

