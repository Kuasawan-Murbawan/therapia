package com.husyairi.therapia;

import android.widget.Spinner;

public class DataClass {
    private String dataTreatmentType;
    private String dataDate;
    private String dataTime;
    private String dataLocation;
    private String dataImage;



    public DataClass(String dataTreatmentType, String dataDesc, String dataLocation, String dataImage, String dataDate, String dataTime) {
        this.dataTreatmentType = dataTreatmentType;
        this.dataDesc = dataDesc;
        this.dataLocation = dataLocation;
        this.dataImage = dataImage;
        this.dataDate = dataDate;
        this.dataTime = dataTime;
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

    public String getDataDate() {
        return dataDate;
    }

    public String getDataTime() {
        return dataTime;
    }


    public DataClass(){

    }

}
