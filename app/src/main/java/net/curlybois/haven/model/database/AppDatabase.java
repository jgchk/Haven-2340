package net.curlybois.haven.model.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import net.curlybois.haven.R;
import net.curlybois.haven.model.Shelter;
import net.curlybois.haven.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by jake on 3/31/18.
 */

@Database(entities = {User.class, Shelter.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();

    private static AppDatabase INSTANCE;

    public abstract AppDao appDao();

    public synchronized static AppDatabase getAppDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "haven-database")
                // allow queries on the main thread.
                // Don't do this on a real app! See PersistenceBasicSample for an example.
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        List<Shelter> shelters = readShelters(context);
                        for (int i = 0; i < shelters.size(); i++) {
                            Shelter shelter = shelters.get(i);
                            ContentValues cv = new ContentValues();
                            cv.put("id", i);
                            cv.put("name", shelter.getName());
                            cv.put("capacity", shelter.getCapacity());
                            cv.put("restrictions", shelter.getRestrictions());
                            cv.put("longitude", shelter.getLongitude());
                            cv.put("latitude", shelter.getLatitude());
                            cv.put("address", shelter.getAddress());
                            cv.put("notes", shelter.getNotes());
                            cv.put("phone", shelter.getPhone());
                            cv.put("genderSet", SetTypeConverter.fromGenderSet(shelter.getGenderSet()));
                            cv.put("ageSet", SetTypeConverter.fromAgeSet(shelter.getAgeSet()));
                            cv.put("veterans", shelter.isVeterans());
                            cv.put("reservations", shelter.getReservations());
                            db.insert("Shelter", OnConflictStrategy.IGNORE, cv);
                        }
                    }
                })
                .build();
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    /**
     * Read in shelter data from the CSV to a list of Shelters
     *
     * @param context the context
     * @return the list of shelters
     */
    private static List<Shelter> readShelters(Context context) {
        List<Shelter> shelters = new ArrayList<>();
        InputStream is = context.getResources().openRawResource(R.raw.shelter_database);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        String info = "";
        try {
            reader.readLine();
            while ((info = reader.readLine()) != null) {
                String regex = "(?!\\B\"[^\"]*),(?![^\"]*\"\\B)";
                String[] str = info.split(regex);
                String name = str[1];
                int capacity = 0;
                if (str[2].length() > 0) {
                    Scanner in = new Scanner(str[2]).useDelimiter("[^0-9]+");
                    try {
                        while (true) {
                            capacity += in.nextInt();
                        }
                    } catch (NoSuchElementException ignored) {
                    }
                }
                String restrictions;
                if (str[3].length() > 0) {
                    restrictions = str[3];
                } else {
                    restrictions = "Restrictions not listed.";
                }
                double longitude = Double.parseDouble(str[4]);
                double latitude = Double.parseDouble(str[5]);
                String address = str[6].replace("\"", "");
                String notes;
                if (str[7].length() > 0) {
                    notes = str[7].replace("\"", "");
                } else {
                    notes = "Notes not listed.";
                }
                String phone = str[8];
                shelters.add(new Shelter(name, capacity, restrictions, longitude, latitude, address, notes, phone));
            }
        } catch (IOException e) {
            Log.wtf(TAG, "Error reading data file on line " + info, e);
            e.printStackTrace();
        }
        return shelters;
    }
}