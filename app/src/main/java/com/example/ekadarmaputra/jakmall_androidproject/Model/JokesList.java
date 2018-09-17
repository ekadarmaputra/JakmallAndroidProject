package com.example.ekadarmaputra.jakmall_androidproject.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JokesList {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("joke")
    @Expose
    private String joke;

    @SerializedName("categories")
    @Expose
    private String[] categories;

    public JokesList(Integer id, String joke, String[] categories){
        this.id = id;
        this.joke = joke;
        this.categories = categories;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getJoke(){
        return joke;
    }

    public void setJoke(String joke){
        this.joke = joke;
    }

    public String[] getCategories(){
        return categories;
    }

    public void setCategories(String[] categories){
        this.categories = categories;
    }
}
