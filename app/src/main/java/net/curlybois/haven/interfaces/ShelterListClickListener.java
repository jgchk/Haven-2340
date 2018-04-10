package net.curlybois.haven.interfaces;

import net.curlybois.haven.model.Shelter;

/**
 * Listen for clicks on any of the shelters in the results list
 */
public interface ShelterListClickListener {
    /**
     * Run when a shelter is clicked
     * @param shelter the shelter that was clicked
     */
    void shelterListClicked(Shelter shelter);
}
