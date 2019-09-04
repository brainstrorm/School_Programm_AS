package com.example.school_programm_as;

public class Group {
    public String name;
    public String teacherFullName;
    public String teacherId;

    public Group() {
    }

    public Group(String name, String teacherName, String teacherId) {
        this.name = name;
        this.teacherFullName = teacherName;
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherFullName;
    }
}
