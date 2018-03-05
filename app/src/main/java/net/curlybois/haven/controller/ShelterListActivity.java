package net.curlybois.haven.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import net.curlybois.haven.R;
import net.curlybois.haven.ShelterAdapter;
import net.curlybois.haven.ShelterListClickListener;
import net.curlybois.haven.TempDatabase;
import net.curlybois.haven.model.Shelter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by jessieprice on 2/28/18.
 */

// This is the main screen showing the list of shelters after the user logs in. When a shelter is
// tapped, a new screen is shown that displays the details about that shelter.
public class ShelterListActivity extends AppCompatActivity implements ShelterListClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);
        readDataIn();

        RecyclerView rv = findViewById(R.id.shelter_list);
        ShelterAdapter adapter = new ShelterAdapter(TempDatabase.getShelters(), this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(ShelterListActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    private void readDataIn() {
        InputStream is = this.getResources().openRawResource(R.raw.shelterdatabase);
        ArrayList<Shelter> arr = new ArrayList<>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String info = "";
        try {
            reader.readLine();
            while ((info = reader.readLine()) != null) {
                String name;
                String cap;
                String res;
                float lon;
                float lat;
                String addr;
                String notes;
                String phone;
                String regex = "(?!\\B\"[^\"]*),(?![^\"]*\"\\B)";
                String[] str = info.split(regex);
                Parcel args = Parcel.obtain();
                name = str[1];
                if (str[2].length() > 0) {
                    cap = str[2];
                } else {
                    cap = "Capacity not listed.";
                }
                if (str[3].length() > 0) {
                    res = str[3];
                } else {
                    res = "Restrictions not listed.";
                }
                lon = Float.parseFloat(str[4]);
                lat = Float.parseFloat(str[5]);
                addr = str[6].replace("\"", "");
                if (str[7].length() > 0) {
                    notes = str[7].replace("\"", "");
                } else {
                    notes = "Notes not listed.";
                }
                phone = str[8];
                TempDatabase.addShelter(new Shelter(name, cap, res, lon, lat, addr, notes, phone));
                args.recycle();
            }

        } catch (IOException e) {
            Log.wtf("Activity", "Error reading data file on line " + info, e);
            e.printStackTrace();
        }
    }


    @Override
    public void shelterListClicked(View v, int position) {
        Intent intent = new Intent(ShelterListActivity.this, ShelterDetailActivity.class);
        intent.putExtra("Shelter", TempDatabase.getShelters().get(position));
        startActivity(intent);
    }
}
