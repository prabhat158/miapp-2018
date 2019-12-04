package com.iitbombay.moodi.moodindigo;

/**
 * Created by owais on 30/07/17.
 */
/*
This class is the list of all the properties that the NewsCard will have.
 */

public class NewsCard {
    private static final int NO_IMAGE_PROVIDED = -1;
    private String mTitleOfCard;
    private String mDescriptionOfCard;
    private int mImageResourceID = NO_IMAGE_PROVIDED;

    public NewsCard(String titleOfCard, String descriptionOfCard, int ImageResourceID) {
        mTitleOfCard = titleOfCard;
        mDescriptionOfCard = descriptionOfCard;
        mImageResourceID = ImageResourceID;
    }

    public String getTitleOfCard() {
        return mTitleOfCard;
    }

    public void setTitleOfCard(String titleOfCard) {
        this.mTitleOfCard = titleOfCard;
    }

    public String getDescriptionOfCard() {
        return mDescriptionOfCard;
    }

    public void setDescriptionOfCard(String descriptionOfCard) {
        this.mDescriptionOfCard = descriptionOfCard;
    }

    public int getImageResourceID() {
        return mImageResourceID;
    }

    public void setImageResourceID(int imageResourceID) {
        this.mImageResourceID = imageResourceID;
    }

    public boolean hasImage() {
        return mImageResourceID != NO_IMAGE_PROVIDED;
    }
}
