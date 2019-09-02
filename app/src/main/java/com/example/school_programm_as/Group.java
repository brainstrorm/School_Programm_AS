package com.example.school_programm_as;

public class Group {
    public String group;
    public String name;
    public String teacherName;
    public String teacherId;

    public Group() {
    }

    public Group(String group, String name, String teacherName, String teacherId) {
        this.group = group;
        this.name = name;
        this.teacherName = teacherName;
        this.teacherId = teacherId;
    }

    public String getGroupId() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }
}
