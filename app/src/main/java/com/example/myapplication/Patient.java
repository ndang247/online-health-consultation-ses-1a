package com.example.myapplication;

public class Patient {
    private String firstLegalName;
    private String middleName;
    private String lastLegalName;
    private String title;
    private String email;
    private String password;
    private String gender;
    private String age;
    private String height;
    private String weight;
    private String bloodType;
    private String medicareNumber;
    private String phoneNumber;

    public Patient(String firstLegalName, String middleName, String lastLegalName, String title, String email, String password, String gender, String age, String height, String weight, String bloodType, String medicareNumber, String phoneNumber) {
        this.firstLegalName = firstLegalName;
        this.middleName = middleName;
        this.lastLegalName = lastLegalName;
        this.title = title;
        this.email = email;
        this.password = password;
        this.gender = gender;
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

    public void setFirstLegalName(String firstLegalName) {
        this.firstLegalName = firstLegalName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastLegalName() {
        return lastLegalName;
    }

    public void setLastLegalName(String lastLegalName) {
        this.lastLegalName = lastLegalName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getMedicareNumber() {
        return medicareNumber;
    }

    public void setMedicareNumber(String medicareNumber) {
        this.medicareNumber = medicareNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
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
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", medicareNumber='" + medicareNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
