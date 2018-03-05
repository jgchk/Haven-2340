package net.curlybois.haven.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import net.curlybois.haven.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jessieprice on 2/27/18.
 */

// Reads in the shelter data and instantiates each line as a shelter object with required
// attributes. All objects are stored in an array list called 'shelters.'

public class Shelter implements Serializable{
    private String name;
    private String capacity;
    private String restrictions;
    private float longitude;
    private float latitude;
    private String address;
    private String notes;
    private String phone;
    private ArrayList<Gender> genderList;
    private ArrayList<Age> ageList;
    private boolean veterans;
    public enum Gender {
        WOMEN("Women"),
        MEN("Men"),
        NONE("None");

        private String name;

        Gender(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    public enum Age {
        CHILDREN("Children"),
        YOUNG_ADULTS("Young adults"),
        FAMILIES_NEWBORNS("Families w/ newborns"),
        FAMILIES("Families"),
        ANYONE("Anyone"),
        NONE("None");

        private String name;

        Age(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
    }
//    public enum Restriction {
//        Women("Women"),
//        Men("Men"),
//        Children("Children"),
//        YoungAdults("Young adults"),
//        FamiliesWNewborns("Families w/ newborns"),
//        Families ("Families"),
//        Veterans("Veterans"),
//        Anyone("Anyone");
//        private String name;
//        private Restriction(String name) {
//            this.name = name;
//        }
//        public String getName() {
//            return this.name;
//        }
//
//        @Override
//        public String toString() {
//            return this.name;
//        }
//    }
    /** holds the list of all courses */
    private static ArrayList<Shelter> shelters = new ArrayList<>();

    public int describeContents() {
        return 0;
    }

    public Shelter(String name, String cap, String res, float lon, float lat, String addr, String notes, String phone) {
        this.name = name;
        this.capacity = cap;
        this.restrictions = res;
        this.longitude = lon;
        this.latitude = lat;
        this.address = addr;
        this.notes = notes;
        this.phone = phone;
        for (Gender g: Gender.values()) {
            if (res.contains(g.getName())) {
                genderList.add(g);
            }
        }
        for (Age a : Age.values()) {
            if (res.contains(a.getName())) {
                ageList.add(a);
            }
        }
        if (res.contains("Veterans")) {
            this.veterans = true;
        }

    }

    /** Returns context of this activity **/
    //public static Context getContext(){
    //    return _instance.getContext();
    //}


    public String getRestrictionListAsString(){
        String answer = "";
        for (Gender g : genderList) {
            if (answer != "") {
                answer = answer + ", " + g;
            } else {
                answer = answer + g;
            }
        }
        for (Age a : ageList) {
            if (answer != "") {
                answer = answer + ", " + a;
            } else {
                answer = answer + a;
            }
        }
        if (answer != "") {
            answer = answer + ", " + "Veterans";
        } else {
            answer = answer + "Veterans";
        }
        return answer;
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
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

    public String toString() {
        return name;
    }

    /** the currently selected course, defaults to first course */
    private Shelter _currentShelter;

    /**
     *
     * @return  the currently selected course
     */
    public Shelter getCurrentShelter() { return _currentShelter;}

    public void setCurrentShelter(Shelter shelter) { _currentShelter = shelter; }

    /**
     * get the courses
     * @return a list of the courses in the app
     */
    public static List<Shelter> getShelters() { return shelters; }
    public void setShelters(ArrayList<Shelter> a){shelters = a;}

    /** Singleton instance */
//    private static final Shelter _instance = new Shelter(null);
//    public static Shelter getInstance() { return _instance; }


}
