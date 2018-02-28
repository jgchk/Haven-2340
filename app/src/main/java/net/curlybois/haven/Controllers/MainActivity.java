package net.curlybois.haven.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import net.curlybois.haven.R;
import net.curlybois.haven.model.Shelter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

// Should list the shelters and bring to new screen when shelter is clicked (ShelterListActivity).
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(ShelterInfoFragment.ARG_SHELTER_NAME,
                    getIntent().getIntExtra(ShelterInfoFragment.ARG_SHELTER_NAME, 0));

            ShelterInfoFragment fragment = new ShelterInfoFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.shelter_detail_container, fragment)
                    .commit();
        }

        shelterList = (ListView) findViewById(R.id.shelterList);

        ArrayAdapter<Shelter> adapter = new ArrayAdapter<Shelter>(this,  android.R.layout.simple_list_item_1,shelters);

        shelterList.setAdapter(adapter);

        shelterList.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShelterActivity.class);
                startActivity(intent);
            }
        });

        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

        @Override
        public boolean onOptionsItemSelected (MenuItem it){
            int id = it.getItemId();
            if (id == android.R.id.home) {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                navigateUpTo(new Intent(this, ShelterDetailActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(it);

        }
    }
