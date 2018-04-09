package net.curlybois.haven.controllers;

import android.content.Context;

import net.curlybois.haven.adapters.FilterQuery;
import net.curlybois.haven.model.Shelter;
import net.curlybois.haven.model.database.AppDatabase;

import java.util.List;

public class SheltersController {

    private static SheltersController INSTANCE = null;

    private AppDatabase db;
    private FilterQuery filterQuery;

    public static SheltersController getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SheltersController(context);
        }
        return INSTANCE;
    }

    public SheltersController(Context context) {
        this.db = AppDatabase.getAppDatabase(context);
        this.filterQuery = new FilterQuery();
    }

    public List<Shelter> getShelters() {
        return db.appDao().getShelters();
    }

    public void setNameFilter(String name) {
        filterQuery.setName(name);
    }

    public void setGenderFilter(Shelter.Gender gender) {
        filterQuery.setGender(gender);
    }

    public void setAgeFilter(Shelter.Age age) {
        filterQuery.setAge(age);
    }

    public void setVeteransFilter(boolean veterans) {
        filterQuery.setVeterans(veterans);
    }

    public FilterQuery getFilterQuery() {
        return filterQuery;
    }

    public void reserve(Shelter shelter, int numReserved) {
        shelter.setReservations(numReserved);
        db.appDao().updateShelter(shelter);
    }
}
