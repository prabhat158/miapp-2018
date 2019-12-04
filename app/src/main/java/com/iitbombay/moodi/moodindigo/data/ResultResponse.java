package com.iitbombay.moodi.moodindigo.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mrunz on 20/12/17.
 */

public class ResultResponse {
    @SerializedName("name")
    private String name;
    @SerializedName("typeofround")
    private String round;
    @SerializedName("roundwinners")
    private String results;
    @SerializedName("day")
    private int day;
    @SerializedName("id")
    private int id;



    public ResultResponse(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }
}
