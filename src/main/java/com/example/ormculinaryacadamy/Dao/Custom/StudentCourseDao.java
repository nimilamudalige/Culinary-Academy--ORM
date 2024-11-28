package com.example.ormculinaryacadamy.Dao.Custom;

import com.example.ormculinaryacadamy.Dao.CrudDao;
import com.example.ormculinaryacadamy.Entity.Student_Course;

public interface StudentCourseDao extends CrudDao<Student_Course> {
    Student_Course getStudentCourseById(Long value);
}
