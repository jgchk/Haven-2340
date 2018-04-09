package net.curlybois.haven.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.location.Location;

import net.curlybois.haven.model.database.SetTypeConverter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jake on 3/31/18.
 */

@Entity
@TypeConverters({SetTypeConverter.class})
public class Shelter implements Serializable {

    public enum Gender {
        MEN("Men"), WOMEN("Women");

        private String str;

        Gender(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    public enum Age {
        CHILDREN("Children"), YOUNG_ADULTS("Young adults"), FAMILIES_NEWBORNS("Families w/ newborns"), FAMILIES("Families"), ANYONE("Anyone");

        private String str;

        Age(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    @PrimaryKey(autoGenerate = true) private int id;
    private String name;
    private int capacity;
    private String restrictions;
    private double longitude;
    private double latitude;
    private String address;
    private String notes;
    private String phone;
    private Set<Gender> genderSet;
    private Set<Age> ageSet;
    private boolean veterans;
    private int reservations;

    public Shelter(String name, int capacity, String restrictions, double longitude, double latitude, String address, String notes, String phone) {
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

    public float getDistance(Location location) {
        if (location == null) {
            return 0;
        }
        Location location1 = new Location("");
        location1.setLatitude(latitude);
        location1.setLongitude(longitude);
        return location.distanceTo(location1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Gender> getGenderSet() {
        return genderSet;
    }

    public void setGenderSet(Set<Gender> genderSet) {
        this.genderSet = genderSet;
    }

    public Set<Age> getAgeSet() {
        return ageSet;
    }

    public void setAgeSet(Set<Age> ageSet) {
        this.ageSet = ageSet;
    }

    public boolean isVeterans() {
        return veterans;
    }

    public void setVeterans(boolean veterans) {
        this.veterans = veterans;
    }

    public int getReservations() {
        return reservations;
    }

    public void setReservations(int reservations) {
        this.reservations = reservations;
    }

    public int getVacancies() {
        return capacity - reservations;
    }
}
