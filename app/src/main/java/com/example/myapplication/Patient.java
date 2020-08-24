package com.example.myapplication;

public class Patient {
    private String firstLegalName;
    private String middleName;
    private String lastLegalName;
    private String age;
    private String height;
    private String weight;
    private String bloodType;
    private String medicareNumber;
    private String phoneNumber;

    public Patient(String firstLegalName, String middleName, String lastLegalName, String gender, String age, String height, String weight, String bloodType, String medicareNumber, String phoneNumber) {
        this.firstLegalName = firstLegalName;
        this.middleName = middleName;
        this.lastLegalName = lastLegalName;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bloodType = bloodType;
        this.medicareNumber = medicareNumber;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstLegalName() {
        return firstLegalName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastLegalName() {
        return lastLegalName;
    }

    public String getAge() {
        return age;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getMedicareNumber() {
        return medicareNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFirstLegalName(String firstLegalName) {
        this.firstLegalName = firstLegalName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastLegalName(String lastLegalName) {
        this.lastLegalName = lastLegalName;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setMedicareNumber(String medicareNumber) {
        this.medicareNumber = medicareNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "firstLegalName='" + firstLegalName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastLegalName='" + lastLegalName + '\'' +
                ", age='" + age + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", medicareNumber='" + medicareNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
