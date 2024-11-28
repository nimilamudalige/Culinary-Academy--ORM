package com.example.ormculinaryacadamy.Bo.Custom;

import com.example.ormculinaryacadamy.Bo.SuperBo;
import com.example.ormculinaryacadamy.Entity.Student_Course;

import java.io.IOException;
import java.util.List;

public interface StudentCourseBo extends SuperBo {
    public List<Student_Course> getStudentCourseList() throws IOException;

}
