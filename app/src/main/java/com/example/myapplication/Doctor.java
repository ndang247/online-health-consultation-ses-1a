package com.example.myapplication;

public class Doctor {
    private String firstLegalName;
    private String lastLegalName;
    private String gender;
    private String age;
    private String specialty;
    private String clinicName;
    private String clinicNumber;
    private String clinicEmail;
    private String phoneNumber;

    public Doctor(String firstLegalName, String lastLegalName, String gender, String age, String specialty, String clinicName, String clinicNumber, String clinicEmail, String phoneNumber) {
        this.firstLegalName = firstLegalName;
        this.lastLegalName = lastLegalName;
        this.gender = gender;
        this.age = age;
        this.specialty = specialty;
        this.clinicName = clinicName;
        this.clinicNumber = clinicNumber;
        this.clinicEmail = clinicEmail;
        this.phoneNumber = phoneNumber;
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

    public String getClinicNumber() {
        return clinicNumber;
    }

    public void setClinicNumber(String clinicNumber) {
        this.clinicNumber = clinicNumber;
    }

    public String getClinicEmail() {
        return clinicEmail;
    }

    public void setClinicEmail(String clinicEmail) {
        this.clinicEmail = clinicEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "firstLegalName='" + firstLegalName + '\'' +
                ", lastLegalName='" + lastLegalName + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", specialty='" + specialty + '\'' +
                ", clinicName='" + clinicName + '\'' +
                ", clinicNumber='" + clinicNumber + '\'' +
                ", clinicEmail='" + clinicEmail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
