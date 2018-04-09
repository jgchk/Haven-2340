package net.curlybois.haven.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import net.curlybois.haven.R;
import net.curlybois.haven.controllers.SheltersController;
import net.curlybois.haven.controllers.UsersController;
import net.curlybois.haven.model.Shelter;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShelterDetailActivity extends AppCompatActivity {

    public static final String EXTRAS_SHELTER = "shelter";
    private static final String TAG = ShelterDetailActivity.class.getSimpleName();

    @BindView(R.id.address_txv) TextView address_txv;
    @BindView(R.id.phone_txv) TextView phone_txv;
    @BindView(R.id.restrictions_txv) TextView restrictions_txv;
    @BindView(R.id.notes_txv) TextView notes_txv;
    @BindView(R.id.vacancies_txv) TextView vacancies_txv;
    @BindView(R.id.vacancy_claim_spn) Spinner vacancyClaim_spn;
    @BindView(R.id.vacancy_claim_btn) Button vacancyClaim_btn;
    @BindView(R.id.vacancy_claim_txv) TextView vacancyClaim_txv;
    @BindView(R.id.vacancy_spots_txv) TextView vacancySpots_txv;
    @BindView(R.id.vacancy_release_btn) Button vacancyRelease_btn;

    private Shelter shelter;
    private SheltersController sheltersController;
    private UsersController usersController;
    private boolean reserve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        // Initialize controller
        sheltersController = new SheltersController(this);
        usersController = UsersController.getInstance(this);

        // Get the shelter passed in
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        shelter = (Shelter) extras.getSerializable(EXTRAS_SHELTER);

        // Set up info
        setTitle(shelter.getName());
        address_txv.setText(shelter.getAddress());
        phone_txv.setText(shelter.getPhone());
        restrictions_txv.setText(shelter.getRestrictions());
        notes_txv.setText(shelter.getNotes());
        setVacanciesText();

        // Set up vacancy claim spinner
        Integer[] items = new Integer[shelter.getCapacity()];
        for (int i = 0; i < shelter.getCapacity(); i++) {
            items[i] = i + 1;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vacancyClaim_spn.setAdapter(adapter);

        // OnClick listeners
        vacancyClaim_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserve();
            }
        });
        vacancyRelease_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                release();
            }
        });

        int numReserved = usersController.getNumReserved(shelter);
        shelter.setReservations(numReserved);
        setVacanciesText();
        setReservationMode(numReserved == 0);
    }

    private void reserve() {
        if (usersController.reserve(shelter, getNumBedsReserved())) {
            sheltersController.reserve(shelter, getNumBedsReserved());
            setVacanciesText();
            setReservationMode(false);
        }
    }

    private void release() {
        if (usersController.release(shelter)) {
            sheltersController.reserve(shelter, 0);
            setVacanciesText();
            setReservationMode(true);
        }
    }

    private int getNumBedsReserved() {
        return vacancyClaim_spn.getSelectedItemPosition() + 1;
    }

    private void setReservationMode(boolean reserve) {
        if (reserve) {
            vacancyClaim_spn.setVisibility(View.VISIBLE);
            vacancyClaim_btn.setVisibility(View.VISIBLE);
            vacancySpots_txv.setVisibility(View.VISIBLE);
            vacancyClaim_txv.setVisibility(View.GONE);
            vacancyRelease_btn.setVisibility(View.GONE);
        } else {
            vacancyClaim_spn.setVisibility(View.GONE);
            vacancyClaim_btn.setVisibility(View.GONE);
            vacancySpots_txv.setVisibility(View.GONE);
            vacancyClaim_txv.setVisibility(View.VISIBLE);
            vacancyClaim_txv.setText(String.format(Locale.getDefault(), "%d reserved", usersController.getNumReserved(shelter)));
            vacancyRelease_btn.setVisibility(View.VISIBLE);
        }
    }

    private void setVacanciesText() {
        vacancies_txv.setText(String.format(Locale.getDefault(), "%d vacancies", shelter.getVacancies()));
    }

}
