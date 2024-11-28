package com.example.ormculinaryacadamy.Dao.Custom;

import com.example.ormculinaryacadamy.Dao.CrudDao;
import com.example.ormculinaryacadamy.Entity.Student;
import com.example.ormculinaryacadamy.Entity.Student_Course;

import java.io.IOException;

public interface StudentDao extends CrudDao<Student> {
    String getCurrentId() throws IOException;

    Student getStudentById(String text);

    void saveStudentCourseDetails(Student_Course studentCourse) throws IOException;
    boolean isStudentRegisteredForCourse(String stuId, String courseId) throws IOException;
    int getStudentCount();
}
