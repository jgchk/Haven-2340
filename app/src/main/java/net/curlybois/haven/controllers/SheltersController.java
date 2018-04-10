package net.curlybois.haven.controllers;

import android.content.Context;

import net.curlybois.haven.adapters.FilterQuery;
import net.curlybois.haven.model.Shelter;
import net.curlybois.haven.model.database.AppDatabase;

import java.util.List;

/**
 * Controls shelter-related operations
 */
public class SheltersController {

    private static SheltersController INSTANCE = null;

    private final AppDatabase db;
    private final FilterQuery filterQuery;

    /**
     * Get the singleton instance of SheltersController
     * @param context the current context
     * @return the singleton instance
     */
    public static SheltersController getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SheltersController(context);
        }
        return INSTANCE;
    }

    /**
     * Create a new SheltersController
     * @param context the current context
     */
    public SheltersController(Context context) {
        this.db = AppDatabase.getAppDatabase(context);
        this.filterQuery = new FilterQuery();
    }

    /**
     * Get the list of shelters
     * @return the list of shelters
     */
    public List<Shelter> getShelters() {
        return db.appDao().getShelters();
    }

    /**
     * Set the name filter
     * @param name the name to filter for
     */
    public void setNameFilter(String name) {
        filterQuery.setName(name);
    }

    /**
     * Set the gender filter
     * @param gender the gender to filter for
     */
    public void setGenderFilter(Shelter.Gender gender) {
        filterQuery.setGender(gender);
    }

    /**
     * Set the age filter
     * @param age the age to filter for
     */
    public void setAgeFilter(Shelter.Age age) {
        filterQuery.setAge(age);
    }

    /**
     * Set the veteran filter
     * @param veterans the veteran status to filter for
     */
    public void setVeteransFilter(boolean veterans) {
        filterQuery.setVeterans(veterans);
    }

    /**
     * Get the current filter
     * @return the current filter
     */
    public FilterQuery getFilterQuery() {
        return filterQuery;
    }

    /**
     * Reserve a number of beds at a shelter
     * @param shelter the shelter to reserve beds at
     * @param numReserved the number of beds to reserve
     */
    public void reserve(Shelter shelter, int numReserved) {
        shelter.setReservations(numReserved);
        db.appDao().updateShelter(shelter);
    }
}
