package net.curlybois.haven.model;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jessieprice on 2/27/18.
 */

// Reads in the shelter data and instantiates each line as a shelter object with required
// attributes. All objects are stored in an array list called 'shelters.'

public class Shelter implements Serializable {

    public static final String GENDER = "Gender", AGE = "Age", VETERAN = "Veteran";

    private String name;
    private int capacity;
    private String restrictions;
    private Location location;
    private String address;
    private ArrayList<String> notes;
    private String phone;
    private ArrayList<Gender> genderList;
    private ArrayList<Age> ageList;
    private boolean veterans;

    public enum Gender {
        WOMEN("Women"),
        MEN("Men"),
        NONE("None");

        private String string;

        Gender(String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    public enum Age {
        CHILDREN("Children"),
        YOUNG_ADULTS("Young adults"),
        FAMILIES_NEWBORNS("Families w/ newborns"),
        FAMILIES("Families"),
        ANYONE("Anyone"),
        NONE("None");

        private String string;

        Age(String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    public Shelter(String name, int cap, String res, double lon, double lat, String addr, ArrayList<String> notes, String phone) {
        this.name = name;
        this.capacity = cap;
        this.restrictions = res;
        this.location = new Location("");
        this.location.setLatitude(lat);
        this.location.setLongitude(lon);
        this.address = addr;
        this.notes = notes;
        this.phone = phone;
        genderList = new ArrayList<>();
        ageList = new ArrayList<>();
        for (Gender g : Gender.values()) {
            if (res.contains(g.toString())) {
                genderList.add(g);
            }
        }
        for (Age a : Age.values()) {
            if (res.contains(a.toString())) {
                ageList.add(a);
            }
        }
        if (res.contains("Veterans")) {
            this.veterans = true;
        }

    }

    public String getRestrictionListAsString() {
        StringBuilder answer = new StringBuilder();
        for (Gender g : genderList) {
            if (!answer.toString().equals("")) {
                answer.append(", ").append(g);
            } else {
                answer.append(g);
            }
        }
        for (Age a : ageList) {
            if (!answer.toString().equals("")) {
                answer.append(", ").append(a);
            } else {
                answer.append(a);
            }
        }
        if (!answer.toString().equals("")) {
            answer.append(", " + "Veterans");
        } else {
            answer.append("Veterans");
        }
        return answer.toString();
    }

    public ArrayList<Age> getAgeList() {
        return ageList;
    }

    public ArrayList<Gender> getGenderList() {
        return genderList;
    }

    public boolean isVeterans() {
        return veterans;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return name;
    }
}