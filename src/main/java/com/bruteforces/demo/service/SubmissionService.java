package com.bruteforces.demo.service;

import com.bruteforces.demo.dao.ConProStuMapper;
import com.bruteforces.demo.dao.SubmissionMapper;
import com.bruteforces.demo.entity.ConPro;
import com.bruteforces.demo.entity.ConProStu;
import com.bruteforces.demo.entity.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class SubmissionService {

    @Autowired(required = false)
    private SubmissionMapper submissionMapper;
    @Autowired(required = false)
    private ConProStuMapper conProStuMapper;

    public List<Submission> getAll() {
        return submissionMapper.getAll();
    }

    public List<Submission> getSubByconId(int conId) {
        return submissionMapper.getSubByconId(conId);
    }

    public List<Submission> getSubBystuId(int stuId) {
        return submissionMapper.getSubBystuId(stuId);
    }

    public int addNewSubmission(int conId, int proId, int stuId, String subSrc, Timestamp subTime, String result, String language, int subTimeLimit, int subMemoryLimit, int subLength) {
        Submission sub = new Submission(0, conId, proId, stuId, subSrc, subTime, result, language, subTimeLimit, subMemoryLimit, subLength);
        return submissionMapper.insertNewSubmission(sub);
    }

    public Submission getSubmissionById(int subId) {
        return submissionMapper.selectSubmissionById(subId);
    }

    public List<ConProStu> getAll(int conId) {
        return conProStuMapper.findByConId(conId);
    }

    public int addnewCPS(String cpsId, int conId, int proId, int stuId) {
        ConProStu cps = new ConProStu(cpsId, conId, proId, stuId, 0, 0, null);
        return conProStuMapper.insertNewConProStu(cps);
    }

    public ConProStu findcpsById(String cpsId) {
        return conProStuMapper.findcpsById(cpsId);
    }

    public int updateCPS(ConProStu cps) {
        return conProStuMapper.updateCPS(cps);
    }
}
