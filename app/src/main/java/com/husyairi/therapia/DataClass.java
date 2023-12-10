package com.husyairi.therapia;

import android.widget.Spinner;

public class DataClass {
    private String dataTreatmentType;

    public DataClass(String dataTreatmentType, String dataDesc, String dataLocation, String dataImage) {
        this.dataTreatmentType = dataTreatmentType;
        this.dataDesc = dataDesc;
        this.dataLocation = dataLocation;
        this.dataImage = dataImage;
    }

    private String dataDesc;

    public String getDataTreatmentType() {
        return dataTreatmentType;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataLocation() {
        return dataLocation;
    }

    public String getDataImage() {
        return dataImage;
    }

    private String dataLocation;
    private String dataImage;

    public DataClass(){

    }

}
