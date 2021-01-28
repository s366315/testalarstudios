package com.testalarstudios.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("data")
    @Expose
    private List<DataModel> data;

    @SerializedName("code")
    @Expose
    private String code;

    public String getStatus() {
        return status;
    }

    public int getPage() {
        return page;
    }

    public List<DataModel> getData() {
        return data;
    }

    public String getCode() {
        return code;
    }
}
