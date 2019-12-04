package com.iitbombay.moodi.moodindigo.data;

import android.graphics.drawable.Drawable;

/**
 * Created by mrunz on 27/9/17.
 */

public class ContactItem {

    private String name;
    private String phone;
    private String email;
    private Drawable image;

    public ContactItem(String name, String email, String phone, Drawable image) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Drawable getImage() {
        return image;
    }
}
