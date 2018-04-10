package net.curlybois.haven.model.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.curlybois.haven.model.Shelter;

import java.lang.reflect.Type;
import java.util.Set;

/**
 * Converts age and gender sets to and from strings
 */
public class SetTypeConverter {

    private static final Gson GSON = new Gson();

    /**
     * Convert a string to a set of gender restrictions
     * @param value a string
     * @return a set of gender restrictions
     */
    @TypeConverter
    public static Set<Shelter.Gender> genderFromString(String value) {
        Type listType = new TypeToken<Set<Shelter.Gender>>() {
        }.getType();
        return GSON.fromJson(value, listType);
    }

    /**
     * Convert a set of gender restrictions to a string
     * @param set a set of gender restrictions
     * @return a string
     */
    @TypeConverter
    public static String fromGenderSet(Set<Shelter.Gender> set) {
        return GSON.toJson(set);
    }

    /**
     * Convert a string to a set of age restrictions
     * @param value a string
     * @return a set of gender restrictions
     */
    @TypeConverter
    public static Set<Shelter.Age> ageFromString(String value) {
        Type listType = new TypeToken<Set<Shelter.Age>>() {
        }.getType();
        return GSON.fromJson(value, listType);
    }

    /**
     * Convert a set of age restrictions to a string
     * @param set a set of age restrictions
     * @return a string
     */
    @TypeConverter
    public static String fromAgeSet(Set<Shelter.Age> set) {
        return GSON.toJson(set);
    }
}
