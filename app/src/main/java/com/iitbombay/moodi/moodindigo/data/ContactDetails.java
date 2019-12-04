package com.iitbombay.moodi.moodindigo.data;

import java.util.List;

/**
 * Created by mrunz on 27/9/17.
 */

public class ContactDetails {

    private String title;
    private List<com.iitbombay.moodi.moodindigo.data.ContactItem> contactItems;

    public ContactDetails(String title, List<com.iitbombay.moodi.moodindigo.data.ContactItem> contactItems) {
        this.contactItems = contactItems;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<com.iitbombay.moodi.moodindigo.data.ContactItem> getContactItems() {
        return contactItems;
    }
}
