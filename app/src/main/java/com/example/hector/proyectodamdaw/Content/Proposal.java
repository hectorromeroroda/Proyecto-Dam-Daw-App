package com.example.hector.proyectodamdaw.Content;

/**
 * Created by Hector on 16/04/2018.
 */

public class Proposal {

    private String Title;
    private String Description;

    public Proposal() {}

    public Proposal(String title, String description) {
        this.Title = title;
        this.Description = Description;
    }

    public void setTitle(String title) {
        this.Title = title;
    }
    public void setDescription(String description) {
        this.Description = description;
    }

    public String getTitle() {
        return Title;
    }
    public String getDescription() {
        return Description;
    }
}
