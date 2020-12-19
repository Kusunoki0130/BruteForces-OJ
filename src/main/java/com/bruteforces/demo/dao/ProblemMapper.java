package com.bruteforces.demo.dao;

import com.bruteforces.demo.entity.Problem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProblemMapper {

    List<Problem> getAll();

    int insertNewProblem(Problem pro);

    int deleteProblemByproId(int proId);

    int setHiddenTrue(int proId);

    int setHiddenFalse(int proId);

    int updatePro(Problem pro);

    Problem findProblemById(int proId);

    Problem findProblemByTitle(String proTitle);
}
