package com.iitbombay.moodi.moodindigo.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mrunz on 24/8/17.
 */

public class RegistrationRequest {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fb_id")
    @Expose
    private String fb_id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile_number")
    @Expose
    private String mobile_number;
    @SerializedName("present_city")
    @Expose
    private String present_city;
    @SerializedName("present_college")
    @Expose
    private String present_college;
    @SerializedName("postal_address")
    @Expose
    private String postal_address;
    @SerializedName("year_of_study")
    @Expose
    private String year_of_study;
    @SerializedName("zip_code")
    @Expose
    private int zip_code;
    @SerializedName("dob")
    @Expose
    private String dob;

    public RegistrationRequest(String name, String fb_id, String email, int zip_code, String year_of_study, String mobile_number, String present_city, String present_college, String postal_address, String dob) {
        this.name = name;
        this.email = email;
        this.fb_id = fb_id;
        this.mobile_number = mobile_number;
        this.present_city = present_city;
        this.present_college = present_college;
        this.postal_address = postal_address;
        this.year_of_study = year_of_study;
        this.zip_code = zip_code;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getPresent_city() {
        return present_city;
    }

    public void setPresent_city(String present_city) {
        this.present_city = present_city;
    }

    public String getPresent_college() {
        return present_college;
    }

    public void setPresent_college(String present_college) {
        this.present_college = present_college;
    }

    public String getPostal_address() {
        return postal_address;
    }

    public void setPostal_address(String postal_address) {
        this.postal_address = postal_address;
    }

    public String getYear_of_study() {
        return year_of_study;
    }

    public void setYear_of_study(String year_of_study) {
        this.year_of_study = year_of_study;
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
