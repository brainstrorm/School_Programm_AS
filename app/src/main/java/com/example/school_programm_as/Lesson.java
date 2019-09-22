package com.example.school_programm_as;

public class Lesson implements Comparable<Lesson>{
    public String date;
    public String group;
    public String name;
    public int number;
    public int state;

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

    public int getState() { return state; }

    public String getDate() {
        return date;
    }

    public String getGroup() {
        return group;
    }

    public int getNumber() {
        return number;
    }

    public int compareTo(Lesson other) {
        if(this.getNumber() >= other.getNumber())
            return 1;
        else
        return -1 ;
    }
}
