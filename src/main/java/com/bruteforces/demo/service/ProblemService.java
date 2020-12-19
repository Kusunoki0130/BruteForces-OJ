package com.bruteforces.demo.service;

import com.bruteforces.demo.dao.ProblemMapper;
import com.bruteforces.demo.entity.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {

    @Autowired(required = false)
    private ProblemMapper problemMapper;

    public List<Problem> getAll() {
        return problemMapper.getAll();
    }

    public int addNewProblem(String proTitle, String proInfo, String proInput, String proOutput, String proInputSample, String proOutputSample, int proTimeLimit, int proMemoryLimit, int proDataNum, String proData, int isHidden) {
        Problem pro = new Problem(0, proTitle, proInfo, proInput, proOutput, proInputSample, proOutputSample, proTimeLimit, proMemoryLimit, 0, 0, proDataNum, proData, isHidden);
        problemMapper.insertNewProblem(pro);
        return pro.getProId();
    }

    public int setHiddenTrue(int proId) {
        return problemMapper.setHiddenTrue(proId);
    }

    public int setHiddenFalse(int proId) {
        return problemMapper.setHiddenFalse(proId);
    }

    public int updateSubAndAcc(Problem pro) {
        return problemMapper.updatePro(pro);
    }

    public Problem findProblemById(int proId) {
        return problemMapper.findProblemById(proId);
    }

    public Problem findProblemByTitle(String proTitle) {
        return problemMapper.findProblemByTitle(proTitle);
    }
}
