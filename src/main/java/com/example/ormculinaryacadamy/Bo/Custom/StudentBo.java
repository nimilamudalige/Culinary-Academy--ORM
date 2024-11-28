package com.example.ormculinaryacadamy.Bo.Custom;

import com.example.ormculinaryacadamy.Bo.SuperBo;
import com.example.ormculinaryacadamy.Dto.StudentDto;
import com.example.ormculinaryacadamy.Entity.Student;

import java.io.IOException;
import java.util.List;

public interface StudentBo extends SuperBo {
    public boolean save(StudentDto studentDto) throws IOException;
    public boolean update(StudentDto studentDto) throws IOException;
    public boolean delete(String id) throws IOException;
    public StudentDto getStudent(String id);
    public List<Student> getStudentList() throws IOException;
}
