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
    private ArrayList<Restriction> restrictionList;
    public enum Restriction {
        Women("Women"),
        Men("Men"),
        Children("Children"),
        YoungAdults("Young adults"),
        FamiliesWNewborns("Families w/ newborns"),
        Families ("Families"),
        Veterans("Veterans"),
        Anyone("Anyone");
        private String name;
        private Restriction(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
    /** holds the list of all courses */
    private static ArrayList<Shelter> shelters = new ArrayList<>();

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(capacity);
        out.writeString(restrictions);
        out.writeFloat(longitude);
        out.writeFloat(latitude);
        out.writeString(address);
        out.writeString(notes);
        out.writeString(phone);
    }
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator<Shelter> CREATOR = new Parcelable.Creator<Shelter>() {
        public Shelter createFromParcel(Parcel in) {
            return new Shelter(in);
        }

        public Shelter[] newArray(int size) {
            return new Shelter[size];
        }
    };


    public Shelter(String name, String cap, String res, float lon, float lat, String addr, String notes, String phone) {
        this.name = name;
        this.capacity = cap;
        this.restrictions = res;
        this.longitude = lon;
        this.latitude = lat;
        this.address = addr;
        this.notes = notes;
        this.phone = phone;
        this.restrictionList = parseRestrictions(res);

    }
    private ArrayList<Restriction> parseRestrictions(String restrictions) {
        ArrayList<Restriction> list= new ArrayList<>();
        for (Restriction r : Restriction.values()) {
            if (restrictions.contains(r.getName())) {
                list.add(r);
            }
        }
        return list;
    }
    public Shelter(Parcel in) {
//        this.name = in.readString();
//        this.capacity = in.readString();
//        this.restrictions = in.readString();
//        this.longitude = in.readFloat();
//        this.latitude = in.readFloat();
//        this.address = in.readString();
//        this.notes = in.readString();
//        this.phone = in.readString();
    }

    /** Returns context of this activity **/
    public static Context getContext(){
        return _instance.getContext();
    }

    public ArrayList<Restriction> getRestrictionList() {
        return restrictionList;
    }

    public String getRestrictionListAsString(){
        String answer = "";
        for (Restriction r : restrictionList) {
            if (answer != "") {
                answer = answer + ", " + r;
            } else {
                answer = answer + r;
            }
        }
        return answer;
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
    private static final Shelter _instance = new Shelter(null);
    public static Shelter getInstance() { return _instance; }


}
