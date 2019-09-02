package com.example.school_programm_as;

public class Lesson {
    public String teacherId;
    public String date;
    public String group;
    public String name;
    public String number;

    public Lesson() {
    }

    public Lesson(String teacherId, String date, String group, String name, String number) {
        this.teacherId = teacherId;
        this.date = date;
        this.group = group;
        this.name = name;
        this.number = number;
    }

    public String getTeacherId() {
        return teacherId;
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

    public String getNumber() {
        return number;
    }
}
