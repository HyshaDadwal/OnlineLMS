package com.user.model;

public class Admin {
    private int adminId;
    private String fullName;
    private String phoneNumber;

    // Constructor
    public Admin(int adminId, String fullName, String phoneNumber) {
        this.adminId = adminId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
