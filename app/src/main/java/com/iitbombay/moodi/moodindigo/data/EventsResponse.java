package com.iitbombay.moodi.moodindigo.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mrunz on 17/9/17.
 */

public class EventsResponse {

    @SerializedName("Competitions")
    private List<com.iitbombay.moodi.moodindigo.data.GenresResponse> competitions;
    @SerializedName("Arts and Ideas")
    private List<com.iitbombay.moodi.moodindigo.data.GenresResponse> artsAndIdeas;
    @SerializedName("Proshows")
    private List<com.iitbombay.moodi.moodindigo.data.GenresResponse> proshows;
    @SerializedName("Workshops")
    private List<com.iitbombay.moodi.moodindigo.data.GenresResponse> workshops;
    @SerializedName("Informals")
    private List<com.iitbombay.moodi.moodindigo.data.GenresResponse> informals;
    @SerializedName("Concerts")
    private List<com.iitbombay.moodi.moodindigo.data.GenresResponse> concerts;

    public List<com.iitbombay.moodi.moodindigo.data.GenresResponse> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(List<com.iitbombay.moodi.moodindigo.data.GenresResponse> competitions) {
        this.competitions = competitions;
    }

    public List<com.iitbombay.moodi.moodindigo.data.GenresResponse> getArtsAndIdeas() {
        return artsAndIdeas;
    }

    public void setArtsAndIdeas(List<com.iitbombay.moodi.moodindigo.data.GenresResponse> artsAndIdeas) {
        this.artsAndIdeas = artsAndIdeas;
    }

    public List<com.iitbombay.moodi.moodindigo.data.GenresResponse> getProshows() {
        return proshows;
    }

    public void setProshows(List<com.iitbombay.moodi.moodindigo.data.GenresResponse> proshows) {
        this.proshows = proshows;
    }

    public List<com.iitbombay.moodi.moodindigo.data.GenresResponse> getWorkshops() {
        return workshops;
    }

    public void setWorkshops(List<com.iitbombay.moodi.moodindigo.data.GenresResponse> workshops) {
        this.workshops = workshops;
    }

    public List<com.iitbombay.moodi.moodindigo.data.GenresResponse> getInformals() {
        return informals;
    }

    public void setInformals(List<com.iitbombay.moodi.moodindigo.data.GenresResponse> informals) {
        this.informals = informals;
    }

    public List<com.iitbombay.moodi.moodindigo.data.GenresResponse> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<com.iitbombay.moodi.moodindigo.data.GenresResponse> concerts) {
        this.concerts = concerts;
    }
}
