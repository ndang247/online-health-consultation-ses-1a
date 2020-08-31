package com.example.myapplication;

public class Doctor {
    private String staffNumber;
    private String email;
    private String firstLegalName;
    private String lastLegalName;
    private String password;
    private String gender;
    private String age;
    private String specialty;
    private String clinicName;
    private String phoneNumber;

    public Doctor(String staffNumber, String email, String firstLegalName, String lastLegalName, String password, String gender, String age, String specialty, String clinicName, String phoneNumber) {
        this.staffNumber = staffNumber;
        this.email = email;
        this.firstLegalName = firstLegalName;
        this.lastLegalName = lastLegalName;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.specialty = specialty;
        this.clinicName = clinicName;
        this.phoneNumber = phoneNumber;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getEmail() {return email;}

    public void setEmail(String email){this.email = email;}

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

    @Override
    public String toString() {
        return "Doctor{" +
                "staffNumber='" + staffNumber + '\'' +
                ", firstLegalName='" + firstLegalName + '\'' +
                ", lastLegalName='" + lastLegalName + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", specialty='" + specialty + '\'' +
                ", clinicName='" + clinicName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
