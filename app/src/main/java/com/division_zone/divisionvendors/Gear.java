package com.division_zone.divisionvendors;

/**
 * Created by Brad on 2/5/2018.
 */

public class Gear {
    private String rarity,vendor,score,name,armor,price,firearms,stamina,electronics,major,minor;

    public Gear(String rarity, String vendor, String score, String name, String armor, String price, String firearms, String stamina, String electronics, String major, String minor) {
        this.rarity = rarity;
        this.vendor = vendor;
        this.score = score;
        this.name = name;
        this.armor = armor;
        this.price = price;
        this.firearms = firearms;
        this.stamina = stamina;
        this.electronics = electronics;
        this.major = major;
        this.minor = minor;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArmor() {
        return armor;
    }

    public void setArmor(String armor) {
        this.armor = armor;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFirearms() {
        return firearms;
    }

    public void setFirearms(String firearms) {
        this.firearms = firearms;
    }

    public String getStamina() {
        return stamina;
    }

    public void setStamina(String stamina) {
        this.stamina = stamina;
    }

    public String getElectronics() {
        return electronics;
    }

    public void setElectronics(String electronics) {
        this.electronics = electronics;
    }

    public String getMajor() {
        major = major.replace("<br/>", "\n");
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        minor = minor.replace("<br/>","\n");
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

}
