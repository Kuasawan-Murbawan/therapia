package com.husyairi.therapia;

import android.widget.Spinner;

public class DataClass {
    private String dataTreatmentType;

    private String dataDesc;
    private String dataDate;
    private String dataTime;
    private String dataLocation;
    private String dataImage;

    private String jobAccepted;
    private String username;

    private String postingDate;

    private Boolean hasComplete;

    public DataClass(String username, String dataTreatmentType, String dataDesc, String dataLocation, String dataImage, String dataDate, String dataTime, String jobAccepted, String postingDate, Boolean hasComplete) {
        this.dataTreatmentType = dataTreatmentType;
        this.dataDesc = dataDesc;
        this.dataLocation = dataLocation;
        this.dataImage = dataImage;
        this.dataDate = dataDate;
        this.dataTime = dataTime;
        this.jobAccepted = jobAccepted;
        this.username = username;
        this.postingDate = postingDate;
        this.hasComplete = hasComplete;
    }

    public Boolean getHasComplete() {
        return hasComplete;
    }

    public void setHasComplete(Boolean hasComplete) {
        this.hasComplete = hasComplete;
    }

    public String getUsername() { return username;}

    public String getDataTreatmentType() {
        return dataTreatmentType;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getJobAccepted() { return jobAccepted;}

    public void setJobAccepted(String jobAccepted) {
        this.jobAccepted = jobAccepted;
    }

    public String getDataLocation() {
        return dataLocation;
    }

    public String getDataImage() {
        return dataImage;
    }

    public String getDataDate() {
        return dataDate;
    }

    public String getDataTime() {
        return dataTime;
    }

    public String getPostingDate() {return postingDate;}

    public DataClass(){

    }

}