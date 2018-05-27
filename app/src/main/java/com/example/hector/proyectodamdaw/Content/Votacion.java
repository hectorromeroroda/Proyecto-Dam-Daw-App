package com.example.hector.proyectodamdaw.Content;

/**
 * Created by Hector on 25/05/2018.
 */

public class Votacion {
    private String Title;
    private String Description;

    public Votacion() {}

    public Votacion(String title, String description) {
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
