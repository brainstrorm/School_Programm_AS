package com.example.school_programm_as;

public class Pupil extends User {
    public int bill;
    public String group;
    public String parentId;
    public Pupil(){}
    public Pupil(String Name, String Surname, String Pathronimic, String Role, String Email,
                 String Password, String UserID, int Bill, String Group, String ParentId){
        super(Name, Surname, Pathronimic, Role, Email, Password, UserID);
        this.bill = Bill;
        this.group = Group;
        this.parentId = ParentId;
    }
}
