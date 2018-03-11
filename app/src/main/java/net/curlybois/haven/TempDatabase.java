package net.curlybois.haven;

import android.content.Context;
import android.os.Parcel;
import android.util.Log;

import net.curlybois.haven.model.Shelter;
import net.curlybois.haven.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jake on 2/20/2018.
 */

public class TempDatabase {
    private static Map<String, User> USERS = new HashMap<>();

    private static ArrayList<Shelter> SHELTERS= new ArrayList<>();

    public static boolean addShelter(Shelter shelter) {
        return SHELTERS.add(shelter);
    }
    public static boolean removeShelter(Shelter shelter) {
        return SHELTERS.remove(shelter);
    }
    public static ArrayList<Shelter> getShelters() {
        return SHELTERS;
    }

    /**
     * Adds a new user
     *
     * @param user the user to add
     * @return false if a user with the same email already exists, true otherwise
     */
    public static boolean addUser(User user) {
        if (USERS.containsKey(user.getEmail())) {
            return false;
        }

        USERS.put(user.getEmail(), user);
        return true;
    }

    /**
     * Checks whether a given login is valid
     *
     * @param email email for login
     * @param password password for login
     * @return whether the login is valid (found in the user store)
     */
    public static boolean isValidLogin(String email, String password) {
        User user = USERS.get(email);
        return user != null && password.equals(user.getPassword());
    }

    public static void readDataIn(Context context) {
        SHELTERS = new ArrayList<>();
        InputStream is = context.getResources().openRawResource(R.raw.shelterdatabase);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String info = "";
        try {
            reader.readLine();
            while ((info = reader.readLine()) != null) {
                String name;
                String cap;
                String res;
                float lon;
                float lat;
                String addr;
                String notes;
                String phone;
                String regex = "(?!\\B\"[^\"]*),(?![^\"]*\"\\B)";
                String[] str = info.split(regex);
                Parcel args = Parcel.obtain();
                name = str[1];
                if (str[2].length() > 0) {
                    cap = str[2];
                } else {
                    cap = "Capacity not listed.";
                }
                if (str[3].length() > 0) {
                    res = str[3];
                } else {
                    res = "Restrictions not listed.";
                }
                lon = Float.parseFloat(str[4]);
                lat = Float.parseFloat(str[5]);
                addr = str[6].replace("\"", "");
                if (str[7].length() > 0) {
                    notes = str[7].replace("\"", "");
                } else {
                    notes = "Notes not listed.";
                }
                phone = str[8];
                TempDatabase.addShelter(new Shelter(name, cap, res, lon, lat, addr, notes, phone));
                args.recycle();
            }
        } catch (IOException e) {
            Log.wtf("Activity", "Error reading data file on line " + info, e);
            e.printStackTrace();
        }
    }
}
