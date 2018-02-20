package com.example.sakchai.listview;

/**
 * Created by Sakchai on 10/27/2017 AD.
 */

public class Person {
    private String name ;
    private String LastName;
    private String nickname ;
    private int ID ;
    public Person(int ID ,String name, String lastName, String nickname) {
        this.ID = ID;
        this.name = name;
        LastName = lastName;
        this.nickname = nickname;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
