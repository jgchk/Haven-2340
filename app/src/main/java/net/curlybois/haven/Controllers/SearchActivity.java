package net.curlybois.haven.Controllers;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import net.curlybois.haven.R;
import net.curlybois.haven.TempDatabase;
import net.curlybois.haven.model.Shelter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    public static final String GENDER = "Gender";
    public static final String AGE = "Age";
    public static final String VETERAN = "Veteran";
    RecyclerView resultsList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        Bundle appData = intent.getBundleExtra(SearchManager.APP_DATA);
        Shelter.Gender gender = (Shelter.Gender) appData.getSerializable(GENDER);
        Shelter.Age age = (Shelter.Age) appData.getSerializable(AGE);
        resultsList = (RecyclerView) findViewById(R.id.resultsList);
        boolean vet = appData.getBoolean(VETERAN);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query, gender, age, vet);
        }

    }
    private ArrayList<Shelter> doMySearch(String query, Shelter.Gender gender, Shelter.Age age, boolean vet) {
        ArrayList<Shelter> results = new ArrayList<>();
        ArrayList<Shelter> shelters = TempDatabase.getShelters();
        for (Shelter s : shelters) {
            if (!s.getName().contains(query)) {
                continue;
            }
            if (!s.getAgeList().contains(age) && age != Shelter.Age.NONE) {
                continue;
            }
            if (!s.getGenderList().contains(gender) && gender != Shelter.Gender.NONE) {
                continue;
            }
            if (vet != s.isVeterans()) {
                continue;
            }
            results.add(s);
        }
        return results;
    }
}
