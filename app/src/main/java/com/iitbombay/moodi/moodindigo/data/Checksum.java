package com.iitbombay.moodi.moodindigo.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mrunz on 8/12/17.
 */

public class Checksum {
    @SerializedName("value")
    private int checksum;
    @SerializedName("id")
    private int id;

    public Checksum() {

    }


    public int getChecksum() {
        return checksum;
    }

    public void setChecksum(int checksum) {
        this.checksum = checksum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
