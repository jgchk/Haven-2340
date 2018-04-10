package net.curlybois.haven.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.location.Location;

import net.curlybois.haven.model.database.SetTypeConverter;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jake on 3/31/18.
 */

@SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
@Entity
@TypeConverters({SetTypeConverter.class})
public class Shelter implements Serializable {

    public enum Gender {
        MEN("Men"), WOMEN("Women");

        private final String str;

        Gender(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    public enum Age {
        CHILDREN("Children"),
        YOUNG_ADULTS("Young adults"),
        FAMILIES_NEWBORNS("Families w/ newborns"),
        FAMILIES("Families"),
        ANYONE("Anyone");

        private final String str;

        Age(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    @PrimaryKey(autoGenerate = true) private int id;
    private final String name;
    private final int capacity;
    private final String restrictions;
    private final double longitude;
    private final double latitude;
    private final String address;
    private final String notes;
    private final String phone;
    private Set<Gender> genderSet;
    private Set<Age> ageSet;
    private boolean veterans;
    private int reservations;

    /**
     * Create a new shelter
     * @param name the shelter name
     * @param capacity the shelter capacity
     * @param restrictions restrictions on who can stay in the shelter
     * @param longitude the shelter longitude
     * @param latitude the shelter latitude
     * @param address the address of the shelter
     * @param notes notes about the shelter
     * @param phone the shelter phone number
     */
    public Shelter(String name, int capacity, String restrictions, double longitude,
                   double latitude, String address, String notes, String phone) {
        this.name = name;
        this.capacity = capacity;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.notes = notes;
        this.phone = phone;
        this.genderSet = new HashSet<>();
        for (Gender g : Gender.values()) {
            if (restrictions.contains(g.toString())) {
                genderSet.add(g);
            }
        }
        this.ageSet = new HashSet<>();
        for (Age a : Age.values()) {
            if (restrictions.contains(a.toString())) {
                ageSet.add(a);
            }
        }
        if (restrictions.contains("Veterans")) {
            this.veterans = true;
        }
        this.reservations = 0;
    }

    /**
     * Get the distance to the shelter from a location
     * @param location the location
     * @return the distance
     */
    public float getDistance(Location location) {
        if (location == null) {
            return 0;
        }
        Location location1 = new Location("");
        location1.setLatitude(latitude);
        location1.setLongitude(longitude);
        return location.distanceTo(location1);
    }

    /**
     * Get the shelter id
     * @return the shelter id
     */
    public int getId() {
        return id;
    }

    /**
     * Set the shelter id
     * @param id the shelter id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the shelter name
     * @return the shelter name
     */
    public String getName() {
        return name;
    }

// --Commented out by Inspection START (4/10/18 6:42 PM):
//    public void setName(String name) {
//        this.name = name;
//    }
// --Commented out by Inspection STOP (4/10/18 6:42 PM)

    /**
     * Get the shelter capacity
     * @return the shelter capacity
     */
    public int getCapacity() {
        return capacity;
    }

// --Commented out by Inspection START (4/10/18 6:42 PM):
//    public void setCapacity(int capacity) {
//        this.capacity = capacity;
//    }
// --Commented out by Inspection STOP (4/10/18 6:42 PM)

    /**
     * Get the shelter restrictions
     * @return the shelter restrictions
     */
    public String getRestrictions() {
        return restrictions;
    }

// --Commented out by Inspection START (4/10/18 6:42 PM):
//    public void setRestrictions(String restrictions) {
//        this.restrictions = restrictions;
//    }
// --Commented out by Inspection STOP (4/10/18 6:42 PM)

    /**
     * Get the shelter longitude
     * @return the shelter longitude
     */
    public double getLongitude() {
        return longitude;
    }

// --Commented out by Inspection START (4/10/18 6:42 PM):
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }
// --Commented out by Inspection STOP (4/10/18 6:42 PM)

    /**
     * Get the shelter latitude
     * @return the shelter latitude
     */
    public double getLatitude() {
        return latitude;
    }

// --Commented out by Inspection START (4/10/18 6:42 PM):
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
// --Commented out by Inspection STOP (4/10/18 6:42 PM)

    /**
     * Get the shelter address
     * @return the shelter address
     */
    public String getAddress() {
        return address;
    }

// --Commented out by Inspection START (4/10/18 6:42 PM):
//    public void setAddress(String address) {
//        this.address = address;
//    }
// --Commented out by Inspection STOP (4/10/18 6:42 PM)

    /**
     * Get the shelter notes
     * @return the shelter notes
     */
    public String getNotes() {
        return notes;
    }

// --Commented out by Inspection START (4/10/18 6:42 PM):
//    public void setNotes(String notes) {
//        this.notes = notes;
//    }
// --Commented out by Inspection STOP (4/10/18 6:42 PM)

    /**
     * Get the shelter phone number
     * @return the shelter phone number
     */
    public String getPhone() {
        return phone;
    }

// --Commented out by Inspection START (4/10/18 6:42 PM):
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
// --Commented out by Inspection STOP (4/10/18 6:42 PM)

    /**
     * Get the set of gender restrictions
     * @return the set of gender restrictions
     */
    public Set<Gender> getGenderSet() {
        return Collections.unmodifiableSet(genderSet);
    }

    /**
     * Set the gender restrictions
     * @param genderSet the set of gender restrictions
     */
    public void setGenderSet(Set<Gender> genderSet) {
        this.genderSet = genderSet;
    }

    /**
     * Get the set of age restrictions
     * @return the set of age restrictions
     */
    public Set<Age> getAgeSet() {
        return Collections.unmodifiableSet(ageSet);
    }

    /**
     * Set the age restrictions
     * @param ageSet the set of age restrictions
     */
    public void setAgeSet(Set<Age> ageSet) {
        this.ageSet = ageSet;
    }

    /**
     * Get the veteran restriction
     * @return the veteran restriction
     */
    public boolean isVeterans() {
        return veterans;
    }

    /**
     * Set the veteran restriction
     * @param veterans the veteran restriction
     */
    public void setVeterans(boolean veterans) {
        this.veterans = veterans;
    }

    /**
     * Get the number of reservations
     * @return the number of reservations
     */
    public int getReservations() {
        return reservations;
    }

    /**
     * Set the number of reservations
     * @param reservations the number of reservations
     */
    public void setReservations(int reservations) {
        this.reservations = reservations;
    }

    /**
     * Get the number of vacancies
     * @return the number of vacancies
     */
    public int getVacancies() {
        return capacity - reservations;
    }
}
