package net.curlybois.haven.interfaces;

import net.curlybois.haven.model.Shelter;

/**
 * Listens for changes to the FilterQuery from the FilterDialog
 */
public interface FilterDialogListener {
    /**
     * Set the gender filter
     * @param gender the gender to filter for
     */
    void setGender(Shelter.Gender gender);

    /**
     * Set the age filter
     * @param age the age to filter for
     */
    void setAge(Shelter.Age age);

    /**
     * Set the veterans filter
     * @param veterans the veteran status to filter for
     */
    void setVeterans(boolean veterans);

    /**
     * Apply the new filters
     */
    void applyFilters();
}
