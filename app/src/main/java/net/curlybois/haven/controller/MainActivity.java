package net.curlybois.haven.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import net.curlybois.haven.R;
import net.curlybois.haven.ShelterAdapter;
import net.curlybois.haven.ShelterListClickListener;
import net.curlybois.haven.TempDatabase;
import net.curlybois.haven.model.Shelter;

public class MainActivity extends AppCompatActivity implements ShelterListClickListener, FilterDialog.FilterDialogListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private Shelter.Gender genderFilter = Shelter.Gender.NONE;
    private Shelter.Age ageFilter = Shelter.Age.NONE;
    private boolean veteranFilter = false;

    private EditText searchBox;
    private RecyclerView resultsView;
    private ShelterAdapter resultsAdapter;
    private ImageButton filterButton;

    private FilterDialog filterDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        searchBox = findViewById(R.id.search_box);
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    applyFilters();
                    return true;
                }
                return false;
            }
        });

        TempDatabase.readDataIn(this);

        resultsView = findViewById(R.id.results_list);
        resultsAdapter = new ShelterAdapter(TempDatabase.getShelters(), this);
        resultsView.setAdapter(resultsAdapter);
        resultsView.setLayoutManager(new LinearLayoutManager(this));
        resultsView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        filterDialog = FilterDialog.newInstance(genderFilter, ageFilter, veteranFilter);

        filterButton = findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.setArguments(FilterDialog.bundleFilters(genderFilter, ageFilter, veteranFilter));
                filterDialog.show(getSupportFragmentManager(), "filters");
            }
        });
    }

    @Override
    public void applyFilters() {
        resultsAdapter.getFilter().filter(buildQuery());
    }

    private String buildQuery() {
        return searchBox.getText().toString() + ":"
                + genderFilter + ":"
                + ageFilter + ":"
                + Boolean.toString(veteranFilter);
    }

    @Override
    public void shelterListClicked(View v, int position) {
        Intent intent = new Intent(MainActivity.this, ShelterDetailActivity.class);
        intent.putExtra("Shelter", TempDatabase.getShelters().get(position));
        startActivity(intent);
    }

    @Override
    public void setGender(Shelter.Gender gender) {
        genderFilter = gender;
    }

    @Override
    public void setAge(Shelter.Age age) {
        ageFilter = age;
    }

    @Override
    public void setVeteran(boolean veteran) {
        veteranFilter = veteran;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
