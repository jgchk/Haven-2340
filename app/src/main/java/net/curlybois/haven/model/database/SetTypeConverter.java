package net.curlybois.haven.model.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.curlybois.haven.model.Shelter;

import java.lang.reflect.Type;
import java.util.Set;

public class SetTypeConverter {

    private static Gson GSON = new Gson();

    @TypeConverter
    public static Set<Shelter.Gender> genderFromString(String value) {
        Type listType = new TypeToken<Set<Shelter.Gender>>() {
        }.getType();
        return GSON.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromGenderSet(Set<Shelter.Gender> set) {
        return GSON.toJson(set);
    }

    @TypeConverter
    public static Set<Shelter.Age> ageFromString(String value) {
        Type listType = new TypeToken<Set<Shelter.Age>>() {
        }.getType();
        return GSON.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromAgeSet(Set<Shelter.Age> set) {
        return GSON.toJson(set);
    }
}
