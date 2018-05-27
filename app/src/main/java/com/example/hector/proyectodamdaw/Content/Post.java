package com.example.hector.proyectodamdaw.Content;

/**
 * Created by Hector on 16/04/2018.
 */

public class Post {

    private String Title;
    private String Description;
    private String Content;

    public Post() {}

    public Post(String title, String description, String content) {
        this.Title = title;
        this.Description = Description;
        this.Content = content;
    }


    public void setTitle(String title) {
        this.Title = title;
    }
    public void setDescription(String description) {
        this.Description = description;
    }
    public void setContent(String content) {
        this.Content = content;
    }

    public String getTitle() {
        return Title;
    }
    public String getDescription() {
        return Description;
    }
    public String getContent() {
        return Content;
    }

}
