package com.bruteforces.demo.dao;

import com.bruteforces.demo.entity.ConProStu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ConProStuMapper {

    int insertNewConProStu(ConProStu cps);

    List<ConProStu> findByConId(int conId);

    ConProStu findcpsById(String cpsId);

    int updateCPS(ConProStu cps);
}
