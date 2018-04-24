package com.drugapp.Pojos;

/**
 * Created by Shiva on 14-07-2017.
 */

public class TrackMyMe {

    public String getMedId() {
        return medId;
    }

    public void setMedId(String medId) {
        this.medId = medId;
    }

    public TrackMyMe(String medId) {
        this.medId = medId;
    }

    public String medId;

}
