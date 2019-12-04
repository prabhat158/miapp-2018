package com.iitbombay.moodi.moodindigo.EventsDatabase;

/**
 * Created by mrunz on 7/12/17.
 */

public class Event {
    private int id;
    private int retrofit_id;
    private String name;
    private String subtitle;
    private String description;
    private String venue;
    private String time_start;
    private String time_end;
    private int going;
    private int day;
    private String type;

    public Event() {
    }

    public Event(int retrofit_id,String name,String subtitle, String description, String venue, String time_start,String time_end,int day, String type) {
        this.name = name;
        this.retrofit_id=retrofit_id;
        this.description = description;
        this.venue = venue;
        this.time_start = time_start;
        this.going = 0;
        this.time_end=time_end;
        this.subtitle=subtitle;
        this.day=day;
        this.type=type;
    }

    public Event(int id, int retrofit_id,String name, String subtitle,String description, String venue, String time_start,String time_end,int day,String type) {
        this.id = id;
        this.retrofit_id=retrofit_id;
        this.name = name;
        this.description = description;
        this.venue = venue;
        this.time_start = time_start;
        this.going = 0;
        this.time_end=time_end;
        this.subtitle=subtitle;
        this.day=day;
        this.type=type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public int getGoing() {
        return going;
    }

    public void setGoing(int going) {
        this.going = going;
    }


    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }



    public int getRetrofit_id() {
        return retrofit_id;
    }

    public void setRetrofit_id(int retrofit_id) {
        this.retrofit_id = retrofit_id;
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
}
