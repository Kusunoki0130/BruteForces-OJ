package com.bruteforces.demo.dao;

import com.bruteforces.demo.entity.Submission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubmissionMapper {

    List<Submission> getAll();

    List<Submission> getSubByconId(int conId);

    List<Submission> getSubBystuId(int stuId);

    int insertNewSubmission(Submission sub);

    Submission selectSubmissionById(int subId);


}
