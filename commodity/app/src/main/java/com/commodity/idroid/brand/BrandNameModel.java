package com.commodity.idroid.brand;

import java.util.ArrayList;

public class BrandNameModel {

    private ArrayList<BrandName> IrBrand;

    class BrandName {
        private String en_name;
        private String id;
        private String name;

        public String getEn_name() {
            return en_name;
        }

        public void setEn_name(String en_name) {
            this.en_name = en_name;
        }

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
            return "BrandName{" +
                    "en_name='" + en_name + '\'' +
                    ", id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public ArrayList<BrandName> getIrBrand() {
        return IrBrand;
    }

    public void setIrBrand(ArrayList<BrandName> irBrand) {
        IrBrand = irBrand;
    }
}
