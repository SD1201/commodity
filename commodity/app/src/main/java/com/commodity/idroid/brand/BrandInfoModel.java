package com.commodity.idroid.brand;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BrandInfoModel implements Serializable {

    private String index;

    private String deviceTypeId;

    private String brandId;

    private ArrayList<String> remoteId = new ArrayList<>();

    private String rank;

    private String en_name;

    private String name;

    public BrandInfoModel() {

    }

    public BrandInfoModel(String deviceTypeId, String brandId, ArrayList<String> remoteId, String rank, String en_name, String name) {
        this.deviceTypeId = deviceTypeId;
        this.brandId = brandId;
        this.remoteId = remoteId;
        this.rank = rank;
        this.en_name = en_name;
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public ArrayList<String> getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(ArrayList<String> remoteId) {
        this.remoteId = remoteId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BrandInfoModel{" +
                "deviceTypeId='" + deviceTypeId + '\'' +
                ", brandId='" + brandId + '\'' +
                ", remoteId=" + remoteId +
                ", rank='" + rank + '\'' +
                ", en_name='" + en_name + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
