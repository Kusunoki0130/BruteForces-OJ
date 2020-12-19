package com.bruteforces.demo.service;

import com.bruteforces.demo.dao.ConProMapper;
import com.bruteforces.demo.dao.ConStuMapper;
import com.bruteforces.demo.dao.ContestMapper;
import com.bruteforces.demo.entity.ConPro;
import com.bruteforces.demo.entity.ConStu;
import com.bruteforces.demo.entity.Contest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ContestService {

    @Autowired(required = false)
    private ContestMapper contestMapper;
    @Autowired(required = false)
    private ConStuMapper conStuMapper;
    @Autowired(required = false)
    private ConProMapper conProMapper;

    public List<Contest> getAll() {
        return contestMapper.getAll();
    }

    public int addNewContest(String conTitle, int adminId, Timestamp startTime, Timestamp endTime) {
        Contest con = new Contest(0, conTitle, adminId, startTime, endTime, 0);
        contestMapper.insertNewContest(con);
        return con.getConId();
    }

    public Contest findContestById(int conId) {
        return contestMapper.findContestById(conId);
    }

    public int addNewConPro(int conId, int proId) {
        ConPro cp = new ConPro(conId, proId);
        return conProMapper.insertNewConPro(cp);
    }

    public List<ConPro> findProbByConId(int conId) {
        return conProMapper.findProbbyconId(conId);
    }


    public ConStu findByConStu(int conId, int stuId) {
        ConStu cs = new ConStu(conId, stuId);
        return conStuMapper.findByConStu(cs);
    }

    public List<ConStu> findConByStuId(int stuId) {
        return conStuMapper.getConByStuId(stuId);
    }

    public List<ConStu> findStuByConId(int conId) {
        return conStuMapper.getStuByConId(conId);
    }

    public int addNewConStu(int conId, int stuId) {
        ConStu cs = new ConStu(conId, stuId);
        return conStuMapper.insertNewConStu(cs);
    }

    public int updateContest(Contest con) {
        return contestMapper.updateContest(con);
    }


}
