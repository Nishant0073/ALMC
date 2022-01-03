package com.opra.alumniportalmanagement;

//Alumni class for alumni

public class Alumni {
    public String regId;
    public String alumniRegId;
    public String name;
    public String emailId;
    public String password;
    public String contactNo;
    public String company;
    public String designation;
    public String packageSalary;
    public String coPassword;
    public String year;
    public String department;
    public String profilePic;
    public String linkedInLink;


    //Parameterized constructor
    public Alumni(String regId,String alumniRegId,String name,String emailId,String password,String contactNo,String company,String designation,String packageSalary,String coPassword,String year,String department,String profilePic,String linkedInLink)
    {
        this.regId = regId;
        this.alumniRegId = alumniRegId;
        this.name = name;
        this.emailId = emailId;
        this.password = password;
        this.contactNo = contactNo;
        this.company = company;
        this.designation = designation;
        this.packageSalary = packageSalary;
        this.coPassword = coPassword;
        this.year = year;
        this.department = department;
        this.profilePic = profilePic;
        this.linkedInLink = linkedInLink;
    }

    //Default constructor
    public Alumni(){};

}
