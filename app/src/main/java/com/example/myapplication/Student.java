package com.example.myapplication;

public class Student {
    String ID;
    String Firstname;
    String Surname;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getFathersname() {
        return Fathersname;
    }

    public void setFathersname(String fathersname) {
        Fathersname = fathersname;
    }

    public String getNationalID() {
        return NationalID;
    }

    public void setNationalID(String nationalID) {
        NationalID = nationalID;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public Student() {
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    String Fathersname;
    String NationalID;
    String DOB;
    String Gender;

    public Student(String ID, String firstname, String surname, String fathersname, String nationalID, String DOB, String gender) {
        this.ID = ID;
        Firstname = firstname;
        Surname = surname;
        Fathersname = fathersname;
        NationalID = nationalID;
        this.DOB = DOB;
        Gender = gender;
    }
}
