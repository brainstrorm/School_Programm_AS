package com.example.school_programm_as;

public class User {
    public String name;
    public String surname;
    public String pathronimic;
    public String role;
    public String email;
    public String password;
    public String userId;
    public User(){

    }
    public User(String Name, String Surname, String Pathronimic, String Role, String Email,
                String Password, String UserID){
        this.name = Name;
        this.surname = Surname;
        this.pathronimic = Pathronimic;
        this.role = Role;
        this.email = Email;
        this.password = Password;
        this.userId = UserID;
    }
    /*public String getName(){
        return name;
    }
    public String getSurname(){
        return surname;
    }
    public String getPathronimic(){
        return pathronimic;
    }
    public String getRole(){
        return role;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getUserId(){
        return userId;
    }*/
}
