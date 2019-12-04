package com.iitbombay.moodi.moodindigo.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mrunz on 24/8/17.
 */

public class RegistrationResponse {
    @SerializedName("name")
    private String name;
    @SerializedName("fb_id")
    private String fb_id;
    @SerializedName("mi_number")
    private String mi_number;
    @SerializedName("email")
    private String email;
    @SerializedName("mobile_number")
    private String mobile_number;
    @SerializedName("present_city")
    private String present_city;
    @SerializedName("present_college")
    private String present_college;
    @SerializedName("postal_address")
    private String postal_address;
    @SerializedName("year_of_study")
    private String year_of_study;
    @SerializedName("zip_code")
    private int zip_code;
    @SerializedName("dob")
    private String dob;
    public RegistrationResponse() {
    }

    public int getZip_code() {
        return zip_code;
    }

    public void setZip_code(int zip_code) {
        this.zip_code = zip_code;
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

    public String getPresent_college() {
        return present_college;
    }

    public void setPresent_college(String present_college) {
        this.present_college = present_college;
    }

    public String getPresent_city() {
        return present_city;
    }

    public void setPresent_city(String present_city) {
        this.present_city = present_city;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMi_number() {
        return mi_number;
    }

    public void setMi_number(String mi_number) {
        this.mi_number = mi_number;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
