package net.curlybois.haven.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.curlybois.haven.R;
import net.curlybois.haven.model.Shelter;
import net.curlybois.haven.TempDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jessieprice on 2/28/18.
 */

// This is the main screen showing the list of shelters after the user logs in. When a shelter is
// tapped, a new screen is shown that displays the details about that shelter.
public class ShelterListActivity extends AppCompatActivity {
    private ListView listView;
//    private TextView mTextMessage;
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
//                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);
//                    return true;
//                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_list);
        readDataIn();
        final ArrayList<Shelter> a = TempDatabase.getShelters();
        listView = (ListView) findViewById(R.id.shelter_list);
        ArrayAdapter<Shelter> arrayAdapter = new ArrayAdapter<Shelter>(
                this,
                android.R.layout.simple_list_item_1,
                TempDatabase.getShelters() );
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent appInfo = new Intent(ShelterListActivity.this, ShelterDetailActivity.class);
                System.out.println(a.get(position));
                appInfo.putExtra("Shelter", a.get(position));
                startActivity(appInfo);
            }
        });


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
    private void readDataIn(){
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
                //Shelter sample = new Shelter(null);
                Parcel args = Parcel.obtain();
                name = str[1];
                //sample.setName(str[1]);
                if (str[2].length() > 0) {
                    //sample.setCapacity((str[2]).toString());
                    cap = (str[2]).toString();
                } else {
                    //sample.setCapacity("Capacity not listed.");
                    cap = ("Capacity not listed.");
                }
                if (str[3].length() > 0) {
                    //sample.setRestrictions((str[3]));
                    res = (str[3]);
                } else {
                    //sample.setRestrictions("Restrictions not listed.");
                    res = ("Restrictions not listed.");
                }
                //sample.setLongitude(Float.parseFloat(str[4]));
                lon = (Float.parseFloat(str[4]));
                //sample.setLatitude(Float.parseFloat(str[5]));
                lat = (Float.parseFloat(str[5]));
                //sample.setAddress(str[6].replace("\"", ""));
                addr = (str[6].replace("\"", ""));
                if (str[7].length() > 0) {
                    //sample.setNotes(str[7].replace("\"", ""));
                    notes = (str[7].replace("\"", ""));
                } else {
                    //sample.setNotes("Notes not listed.");
                    notes = ("Notes not listed.");
                }
                //sample.setPhone(str[8].toString());
                phone = (str[8].toString());
                //System.out.println(args.readString());
                TempDatabase.addShelter(new Shelter(name, cap, res, lon, lat, addr, notes, phone));
                //Log.d("Activity", "Created " + sample);
                Log.d("Activity", "DB " + TempDatabase.getShelters());
            }

        } catch (IOException e){
            Log.wtf("Activity", "Error reading data file on line " + info, e);
            e.printStackTrace();
        }
    }


}
