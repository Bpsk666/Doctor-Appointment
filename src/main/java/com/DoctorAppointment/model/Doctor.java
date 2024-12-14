package com.DoctorAppointment.model;

import jakarta.persistence.*;

@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long docId;

    private String docFName;
    private String docLName;
    private int docAge;
    private String specialty;
    private String qualification;
    private int experience;
    private String docPhone;
    private String docAddress;
    private String docEmail;
    private String docPassword;

    private double aptFee;
    @Lob
    @Column(name="profile_image", columnDefinition = "mediumBlob")
    private byte[] profileImage;

    public Doctor() {
        super();
    }

    public Doctor(String docFName, String docLName, int docAge, String specialty, String qualification, int experience, String docPhone, String docAddress, String docEmail, String docPassword,double aptFee, byte[] profileImage) {
        this.docFName = docFName;
        this.docLName = docLName;
        this.docAge = docAge;
        this.specialty = specialty;
        this.qualification = qualification;
        this.experience = experience;
        this.docPhone = docPhone;
        this.docAddress = docAddress;
        this.docEmail = docEmail;
        this.docPassword = docPassword;
        this.aptFee = aptFee;
        this.profileImage = profileImage;
    }

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    public String getDocFName() {
        return docFName;
    }

    public void setDocFName(String docFName) {
        this.docFName = docFName;
    }

    public String getDocLName() {
        return docLName;
    }

    public void setDocLName(String docLName) {
        this.docLName = docLName;
    }

    public int getDocAge() {
        return docAge;
    }

    public void setDocAge(int docAge) {
        this.docAge = docAge;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getDocPhone() {
        return docPhone;
    }

    public void setDocPhone(String docPhone) {
        this.docPhone = docPhone;
    }

    public String getDocAddress() {
        return docAddress;
    }

    public void setDocAddress(String docAddress) {
        this.docAddress = docAddress;
    }

    public String getDocEmail() {
        return docEmail;
    }

    public void setDocEmail(String docEmail) {
        this.docEmail = docEmail;
    }

    public String getDocPassword() {
        return docPassword;
    }

    public void setDocPassword(String docPassword) {
        this.docPassword = docPassword;
    }

    public double getAptFee() {
        return aptFee;
    }

    public void setAptFee(double aptFee) {
        this.aptFee = aptFee;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }
}
