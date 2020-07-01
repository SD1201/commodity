package com.commodity.idroid.brand;

import androidx.annotation.NonNull;

import java.util.List;

public class BrandModel {

    private List<Brand> IrBrandRemoteRel;

    class Brand {
        private String device_type_id;
        private String brand_id;
        private String remote_id;
        private String rank;

        public String getDevice_type_id() {
            return device_type_id;
        }

        public void setDevice_type_id(String device_type_id) {
            this.device_type_id = device_type_id;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public String getRemote_id() {
            return remote_id;
        }

        public void setRemote_id(String remote_id) {
            this.remote_id = remote_id;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        @Override
        public String toString() {
            return "Brand{" +
                    "device_type_id='" + device_type_id + '\'' +
                    ", brand_id='" + brand_id + '\'' +
                    ", remote_id='" + remote_id + '\'' +
                    ", rank='" + rank + '\'' +
                    '}';
        }
    }

    public List<Brand> getIrBrandRemoteRel() {
        return IrBrandRemoteRel;
    }

    public void setIrBrandRemoteRel(List<Brand> irBrandRemoteRel) {
        IrBrandRemoteRel = irBrandRemoteRel;
    }
}
