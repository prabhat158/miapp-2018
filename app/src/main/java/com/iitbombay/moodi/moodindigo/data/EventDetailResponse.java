package com.iitbombay.moodi.moodindigo.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mrunz on 17/9/17.
 */

public class EventDetailResponse {

    @SerializedName("name")
    private String name;
    @SerializedName("subtitle")
    private String subtitle;
    @SerializedName("description")
    private String description;
    @SerializedName("id")
    private int id;
    @SerializedName("going")
    private int going;
    @SerializedName("venue")
    private String venue;
    @SerializedName("starttime")
    private String starttime;
    @SerializedName("endtime")
    private String endtime;
    @SerializedName("genre")
    private String type;
    @SerializedName("day")
    private int day;

    public EventDetailResponse() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public int getGoing() {
        return going;
    }

    public void setGoing(int going) {
        this.going = going;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}