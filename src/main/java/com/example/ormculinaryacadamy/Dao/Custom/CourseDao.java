package com.example.ormculinaryacadamy.Dao.Custom;

import com.example.ormculinaryacadamy.Dao.CrudDao;
import com.example.ormculinaryacadamy.Entity.Course;

import java.io.IOException;
import java.util.List;

public interface CourseDao extends CrudDao<Course> {
    String getCurrentId() throws IOException;

    List<String> getCourseId();

    List<String> getCourseIds();

    Course getCourseById(String courseId);
    int getProgramCount();
}
