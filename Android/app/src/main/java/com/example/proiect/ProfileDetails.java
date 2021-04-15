package com.example.proiect;

public class ProfileDetails {

    private String NAME;
    private String SURNAME;
    private String AGE ;
    private String HEIGHT;
    private String WEIGHT;
    private String GENDER_RB_ID;
    private String PHONE;
    private String ACTIVITY_LEVEL;

    public ProfileDetails(String NAME, String SURNAME, String AGE, String HEIGHT, String WEIGHT, String GENDER_RB_ID, String PHONE, String ACTIVITY_LEVEL) {
        this.NAME = NAME;
        this.SURNAME = SURNAME;
        this.AGE = AGE;
        this.HEIGHT = HEIGHT;
        this.WEIGHT = WEIGHT;
        this.GENDER_RB_ID = GENDER_RB_ID;
        this.PHONE=PHONE;
        this.ACTIVITY_LEVEL=ACTIVITY_LEVEL;

    }

    public String getACTIVITY_LEVEL() {
        return ACTIVITY_LEVEL;
    }

    public void setACTIVITY_LEVEL(String ACTIVITY_LEVEL) {
        this.ACTIVITY_LEVEL = ACTIVITY_LEVEL;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSURNAME() {
        return SURNAME;
    }

    public void setSURNAME(String SURNAME) {
        this.SURNAME = SURNAME;
    }

    public String getAGE() {
        return AGE;
    }

    public void setAGE(String AGE) {
        this.AGE = AGE;
    }

    public String getHEIGHT() {
        return HEIGHT;
    }

    public void setHEIGHT(String HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public String getWEIGHT() {
        return WEIGHT;
    }

    public void setWEIGHT(String WEIGHT) {
        this.WEIGHT = WEIGHT;
    }

    public String getGENDER_RB_ID() {
        return GENDER_RB_ID;
    }

    public void setGENDER_RB_ID(String GENDER_RB_ID) {
        this.GENDER_RB_ID = GENDER_RB_ID;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }
}
