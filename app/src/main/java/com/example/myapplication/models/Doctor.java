package com.example.myapplication.models;

public class Doctor {

    private String id;

    private String staffNumber;
    private String firstLegalName;
    private String lastLegalName;
    private String password;
    private String gender;
    private String age; // Not Required
    private String specialty;
    private String clinicName;
    private String phoneNumber; // Not Required

    private String imageURL;

    public Doctor() {

    }

    public Doctor(String id, String staffNumber, String firstLegalName, String lastLegalName, String password, String gender, String age, String specialty, String clinicName, String phoneNumber, String imageURL) {
        this.id = id;
        this.staffNumber = staffNumber;
        this.firstLegalName = firstLegalName;
        this.lastLegalName = lastLegalName;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.specialty = specialty;
        this.clinicName = clinicName;
        this.phoneNumber = phoneNumber;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getFirstLegalName() {
        return firstLegalName;
    }

    public void setFirstLegalName(String firstLegalName) {
        this.firstLegalName = firstLegalName;
    }

    public String getLastLegalName() {
        return lastLegalName;
    }

    public void setLastLegalName(String lastLegalName) {
        this.lastLegalName = lastLegalName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
