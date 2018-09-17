package com.example.ekadarmaputra.jakmall_androidproject.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class JSONResponse {
    @SerializedName("value")
    @Expose
    private ArrayList<JokesList> jokesList = new ArrayList<>();

    /**
     * @return jokesList list from ApiInterface
     */
    public ArrayList<JokesList> getJokesList(){
        return jokesList;
    }

    /**
     * @param jokesList
     */
    public void setJokesList(ArrayList<JokesList> jokesList){
        this.jokesList = jokesList;
    }
}
