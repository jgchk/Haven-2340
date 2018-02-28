package net.curlybois.haven.model;

import android.content.Context;
import android.util.Log;

import net.curlybois.haven.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jessieprice on 2/27/18.
 */

// Reads in the shelter data and instantiates each line as a shelter object with required
// attributes. All objects are stored in an array list called 'shelters.'

public class Shelter {
    private static String name;
    private static String capacity;
    private static String restrictions;
    private static float longitude;
    private static float latitude;
    private static String address;
    private static String notes;
    private static String phone;

    /** holds the list of all courses */
    private ArrayList<Shelter> shelters = new ArrayList<>();



    private Shelter() {
        readData(shelters);
    }

    /** Returns context of this activity **/
    public static Context getContext(){
        return _instance.getContext();
    }

    /**
     * get data from csv
     */
    private void readData(ArrayList<Shelter> a) {
        InputStream is = getContext().getResources().openRawResource(R.raw.shelterdatabase);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String info = "";
        try {
            reader.readLine();
            while ((info = reader.readLine()) != null) {
                String regex = "(?!\\B\"[^\"]*),(?![^\"]*\"\\B)";
                String[] str = info.split(regex);

                Shelter sample = new Shelter();
                sample.setName(str[1]);
                if (str[2].length() > 0) {
                    sample.setCapacity((str[2]).toString());
                } else {
                    sample.setCapacity("Capacity not listed.");
                }
                if (str[3].length() > 0) {
                    sample.setRestrictions((str[3]));
                } else {
                    sample.setRestrictions("Restrictions not listed.");
                }
                sample.setLongitude(Float.parseFloat(str[4]));
                sample.setLatitude(Float.parseFloat(str[5]));
                sample.setAddress(str[6].replace("\"", ""));
                if (str[7].length() > 0) {
                    sample.setNotes(str[7].replace("\"", ""));
                } else {
                    sample.setNotes("Notes not listed.");
                }
                sample.setPhone(str[8].toString());
                a.add(sample);
                Log.d("Activity", "Created " + sample);
            }

        } catch (IOException e){
            Log.wtf("Activity", "Error reading data file on line " + info, e);
            e.printStackTrace();
        }
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public static String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public static float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public static float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public static String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public static String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return name;
    }

    /** the currently selected course, defaults to first course */
    private Shelter _currentShelter = shelters.get(0);

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
    public List<Shelter> getShelters() { return shelters; }

    /** Singleton instance */
    private static final Shelter _instance = new Shelter();
    public static Shelter getInstance() { return _instance; }


}
