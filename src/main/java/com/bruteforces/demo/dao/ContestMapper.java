package com.bruteforces.demo.dao;

import com.bruteforces.demo.entity.Contest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContestMapper {

    List<Contest> getAll();

    int insertNewContest(Contest con);

    Contest findContestById(int conId);

    int updateContest(Contest con);


}
