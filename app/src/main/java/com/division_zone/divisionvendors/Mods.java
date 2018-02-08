package com.division_zone.divisionvendors;

/**
 * Created by Brad on 2/8/2018.
 */

public class Mods {
    private String vendor,name,stat,price,attribute;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Mods(String vendor, String name, String stat, String price, String attribute) {
        this.vendor = vendor;
        this.name = name;
        this.stat = stat;
        this.price = price;
        this.attribute = attribute;
    }
}
