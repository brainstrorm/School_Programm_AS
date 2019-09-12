package com.example.school_programm_as;

public class Lesson {
    public String date;
    public String group;
    public String name;
    public int number;

    public Lesson() {
    }

    public Lesson(String teacherId, String date, String group, String name, int number) {
        this.date = date;
        this.group = group;
        this.name = name;
        this.number = number;
    }


    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getGroup() {
        return group;
    }

    public int getNumber() {
        return number;
    }
}
