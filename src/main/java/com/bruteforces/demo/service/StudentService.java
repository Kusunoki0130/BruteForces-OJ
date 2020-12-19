package com.bruteforces.demo.service;

import com.bruteforces.demo.dao.StudentMapper;
import com.bruteforces.demo.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentService {

    @Autowired(required = false)
    private StudentMapper studentMapper;

    public int addNewStudent(String stuUsername, String stuPassword, String stuEmail, String stuPhone) {
        Student stu = new Student(0, stuUsername, stuPassword, 0, 0, stuEmail, stuPhone);
        return studentMapper.insertNewStudent(stu);
    }

    public Student findStudentById(int stuId) {
        return studentMapper.selectStudentByStuId(stuId);
    }

    public Student findStudentByUsername(String stuUsername) {
        return studentMapper.selectStudentByStuUsername(stuUsername);
    }

    public int rePassword(String stuUsername, String stuPassword, String newPassword) {
        Student stu = studentMapper.selectStudentByStuUsername(stuUsername);
        if (!stu.getStuPassword().equals(stuPassword)) {
            return 0;
        }
        stu.setStuPassword(newPassword);
        return studentMapper.updateStudent(stu);
    }

    public int updateStu(Student stu) {
        return studentMapper.updateStudent(stu);
    }
}
