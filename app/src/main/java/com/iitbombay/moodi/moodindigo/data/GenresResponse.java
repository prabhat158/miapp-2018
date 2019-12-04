package com.iitbombay.moodi.moodindigo.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrunz on 17/9/17.
 */

public class GenresResponse {
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("events")
    private List<EventDetailResponse> events;
    @SerializedName("image")
    private String image;

    public GenresResponse(String name, String description, String Image, List<EventDetailResponse> events) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.events = events;
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

    public List<EventDetailResponse> getEvents() {
        return events;
    }

    public void setEvents(List<EventDetailResponse> events) {
        this.events = events;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
