package net.curlybois.haven.adapters;

import net.curlybois.haven.model.Shelter;

import java.io.Serializable;

public class FilterQuery implements Serializable {
    private String name;
    private Shelter.Gender gender;
    private Shelter.Age age;
    private boolean veterans;

    public FilterQuery() {
        this.name = "";
        this.gender = null;
        this.age = null;
        this.veterans = false;
    }

    public FilterQuery(String name, Shelter.Gender gender, Shelter.Age age, boolean veterans) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.veterans = veterans;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Shelter.Gender getGender() {
        return gender;
    }

    public void setGender(Shelter.Gender gender) {
        this.gender = gender;
    }

    public Shelter.Age getAge() {
        return age;
    }

    public void setAge(Shelter.Age age) {
        this.age = age;
    }

    public boolean isVeterans() {
        return veterans;
    }

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
