package com.commodity.idroid.category;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

public class CategoryModel implements Serializable {

    private Document DOCUMENT;

    public class Document {
        private List<DeviceType> device_type;

        public List<DeviceType> getDevice_type() {
            return device_type;
        }

        public void setDevice_type(List<DeviceType> device_type) {
            this.device_type = device_type;
        }

        @Override
        public String toString() {
            return "Document{" +
                    "device_type=" + device_type +
                    '}';
        }
    }

    public class DeviceType implements Serializable{
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "DeviceType{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public Document getDOCUMENT() {
        return DOCUMENT;
    }

    public void setDOCUMENT(Document DOCUMENT) {
        this.DOCUMENT = DOCUMENT;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "DOCUMENT=" + DOCUMENT +
                '}';
    }

}
