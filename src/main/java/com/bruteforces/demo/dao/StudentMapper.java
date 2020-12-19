package com.bruteforces.demo.dao;

import com.bruteforces.demo.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {

    int insertNewStudent(Student stu);

    Student selectStudentByStuId(int StuId);

    Student selectStudentByStuUsername(String StuUsername);

    int updateStudent(Student stu);
}
