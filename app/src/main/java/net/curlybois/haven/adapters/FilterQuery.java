package net.curlybois.haven.adapters;

import net.curlybois.haven.model.Shelter;

import java.io.Serializable;

/**
 * A container for a certain filter search
 */
public class FilterQuery implements Serializable {
    private String name;
    private Shelter.Gender gender;
    private Shelter.Age age;
    private boolean veterans;

    /**
     * Creates a new FilterQuery
     */
    public FilterQuery() {
        this.name = "";
        this.gender = null;
        this.age = null;
        this.veterans = false;
    }

//    public FilterQuery(String name, Shelter.Gender gender, Shelter.Age age, boolean veterans) {
//        this.name = name;
//        this.gender = gender;
//        this.age = age;
//        this.veterans = veterans;
//    }

    /**
     * Get the name
     * @return the name
     */
    public CharSequence getName() {
        return name;
    }

    /**
     * Set the name
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the gender
     * @return the gender
     */
    public Shelter.Gender getGender() {
        return gender;
    }

    /**
     * Set the gender
     * @param gender the gender
     */
    public void setGender(Shelter.Gender gender) {
        this.gender = gender;
    }

    /**
     * Get the age
     * @return the age
     */
    public Shelter.Age getAge() {
        return age;
    }

    /**
     * Set the age
     * @param age the age
     */
    public void setAge(Shelter.Age age) {
        this.age = age;
    }

    /**
     * Get veterans
     * @return veterans
     */
    public boolean isVeterans() {
        return veterans;
    }

    /**
     * Set veterans
     * @param veterans veterans
     */
    public void setVeterans(boolean veterans) {
        this.veterans = veterans;
    }

    @Override
    public String toString() {
        return "FilterQuery{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", veterans=" + veterans +
                '}';
    }
}
