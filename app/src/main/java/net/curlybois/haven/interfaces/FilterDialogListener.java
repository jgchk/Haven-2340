package net.curlybois.haven.interfaces;

import net.curlybois.haven.model.Shelter;

public interface FilterDialogListener {
    void setGender(Shelter.Gender gender);

    void setAge(Shelter.Age age);

    void setVeterans(boolean veterans);

    void applyFilters();
}
