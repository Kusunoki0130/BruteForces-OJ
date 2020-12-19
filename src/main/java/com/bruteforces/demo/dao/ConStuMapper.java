package com.bruteforces.demo.dao;

import com.bruteforces.demo.entity.ConStu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConStuMapper {

    List<ConStu> getAll();

    List<ConStu> getConByStuId(int stuId);

    List<ConStu> getStuByConId(int conId);

    ConStu findByConStu(ConStu cs);

    int insertNewConStu(ConStu cs);

}
