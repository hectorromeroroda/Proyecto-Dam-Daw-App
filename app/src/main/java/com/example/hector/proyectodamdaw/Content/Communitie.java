package com.example.hector.proyectodamdaw.Content;

/**
 * Created by Hector on 16/04/2018.
 */

public class Communitie {
    private String name;
    private String description;
    private int numUsers;

    public Communitie() {}
    public Communitie(String Name, String Description, int numUsers) {
        this.name = Name;
        this.description = Description;
        this.numUsers = numUsers;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {this.description = description;}

    public void setNumUsers(int numUsers) {
        this.numUsers = numUsers;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getnumUsers() {
        return numUsers;
    }


}
