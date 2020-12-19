package com.bruteforces.demo.controller;

import com.bruteforces.demo.entity.*;
import com.bruteforces.demo.service.ContestService;
import com.bruteforces.demo.service.ProblemService;
import com.bruteforces.demo.service.StudentService;
import com.bruteforces.demo.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class ContestController {

    @Autowired
    private ContestService contestService;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private StudentService studentService;


    // ClientEnd

    @GetMapping("/ContestList")
    public String contestListPage(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession(true);
        Student stu = (Student)session.getAttribute("stu");
        if (stu==null) {
            return "redirect:/login";
        }
        List<Contest> conList = contestService.getAll();
        request.setAttribute("conList", conList);
        return "ClientEnd/Contest/contestList";
    }

    @GetMapping("/ContestDetail")
    public String contestDetailpage(HttpServletRequest request, ModelAndView mv, @RequestParam int conId) {
        Contest con = contestService.findContestById(conId);
        List<ConPro> conproList = contestService.findProbByConId(conId);
        List<ConProStu> cpsList = submissionService.getAll(conId);
        List<Problem> probList = new LinkedList<>();
        Timestamp ts = new Timestamp(new Date().getTime());
        String checkTime = ts.toString();
        String startTime = con.getStartTime().toString();
        String endTime = con.getEndTime().toString();
        for (ConPro i : conproList) {
            probList.add(problemService.findProblemById(i.getProId()));
        }
        HttpSession session = request.getSession(true);
        Student stu = (Student) session.getAttribute("stu");
        if (stu!=null) {
            if (contestService.findByConStu(conId, stu.getStuId())!=null) {
                // 0 : stu代表的用户已报名
                request.setAttribute("ifDisable", 0);
            }
            else if (checkTime.compareTo(endTime)>0) {
                // 2 : 比赛已结束，不可报名
                request.setAttribute("ifDisable",2);
            }
            else if (checkTime.compareTo(startTime)>0) {
                // 1 : 比赛已开始，不可报名
                request.setAttribute("ifDisable", 1);
            }
            else {
                // 3 : 可报名
                request.setAttribute("ifDisable",3);
            }
        }
        else {
            // 4 : 无stu用户，无报名权限
            request.setAttribute("ifDisable",4);
        }
        System.out.println(checkTime);
        System.out.println(endTime);
        Admin admin = (Admin)session.getAttribute("admin");
        if (admin!=null) {
            request.setAttribute("ifAdmin", 1);
        }
        else {
            request.setAttribute("ifAdmin", 0);
        }
        request.setAttribute("con", con);
        request.setAttribute("probList", probList);
        request.setAttribute("cpsList", cpsList);
        System.out.println(cpsList.size());
        return "ClientEnd/Contest/contestDetail";
    }

    @PostMapping("/regiContest")
    @ResponseBody
    public void reigisterContest(HttpServletRequest request, ModelAndView mv, @RequestParam(value = "conId",required = true) int conId) {
        HttpSession session = request.getSession(true);
        Student stu = (Student)session.getAttribute("stu");
        if (stu!=null && contestService.findByConStu(conId, stu.getStuId())==null) {
            contestService.addNewConStu(conId, stu.getStuId());
            List<ConPro> cpList = contestService.findProbByConId(conId);
            Contest con = contestService.findContestById(conId);
            for (ConPro i : cpList) {
                String cpsId = conId + "_" + i.getProId() + "_" + stu.getStuId();
                submissionService.addnewCPS(cpsId, conId, i.getProId(), stu.getStuId());
            }
            con.addParticipant();
            contestService.updateContest(con);
        }
        return;
    }

    @GetMapping("/ContestProbDetail")
    public String ContestProbDetailPage(HttpServletRequest request, ModelAndView mv, @RequestParam int conId, @RequestParam int proId) {
        Problem pro = problemService.findProblemById(proId);
        Contest con = contestService.findContestById(conId);
        request.setAttribute("conId", conId);
        request.setAttribute("pro", pro);
        request.setAttribute("endTime", con.getEndTime());
        return "ClientEnd/Contest/contestProblemDetail";
    }

    @GetMapping("/ContestRank")
    public String contestRankPage(HttpServletRequest request, ModelAndView mv, int conId) {
        List<ConProStu> cpsList = submissionService.getAll(conId);
        List<Student> stuList = new LinkedList<Student>();
        List<ConStu> csList = contestService.findStuByConId(conId);
        for (ConStu cs : csList) {
            stuList.add(studentService.findStudentById(cs.getStuId()));
        }
        request.setAttribute("startTime",contestService.findContestById(conId).getStartTime().toString());
        request.setAttribute("cpsList", cpsList);
        request.setAttribute("stuList", stuList);
        request.setAttribute("conId", conId);
        return "ClientEnd/Contest/contestRank";
    }

    @GetMapping("/ContestStatus")
    public String statusPage(HttpServletRequest request, ModelAndView mv, int conId) {
        List<Submission> subList = submissionService.getSubByconId(conId);
        request.setAttribute("subList", subList);
        request.setAttribute("conId", conId);
        return "ClientEnd/Contest/contestStatus";
    }

    // AdminEnd

    @GetMapping("/admin/ContestList")
    public String admincontestListPage(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession(true);
        Admin admin = (Admin)session.getAttribute("admin");
        if (admin==null) {
            return "redirect:/admin/Login";
        }
        List<Contest> conList = contestService.getAll();
        request.setAttribute("conList", conList);
        return "AdminEnd/Contest/contestList";
    }

    @GetMapping("/admin/ContestDetail")
    public String admincontestDetailpage(HttpServletRequest request, ModelAndView mv, @RequestParam int conId) {
        Contest con = contestService.findContestById(conId);
        List<ConPro> conproList = contestService.findProbByConId(conId);
        List<ConProStu> cpsList = submissionService.getAll(conId);
        List<Problem> probList = new LinkedList<>();
        Timestamp ts = new Timestamp(new Date().getTime());
        for (ConPro i : conproList) {
            probList.add(problemService.findProblemById(i.getProId()));
        }
        HttpSession session = request.getSession(true);
        request.setAttribute("ifDisable", 10);
        Admin admin = (Admin)session.getAttribute("admin");
        if (admin!=null) {
            request.setAttribute("ifAdmin", 1);
        }
        else {
            request.setAttribute("ifAdmin", 0);
        }
        request.setAttribute("con", con);
        request.setAttribute("probList", probList);
        request.setAttribute("cpsList", cpsList);
        System.out.println(cpsList.size());
        return "AdminEnd/Contest/contestDetail";
    }

    @GetMapping("/admin/ContestProbDetail")
    public String adminContestProbDetailPage(HttpServletRequest request, ModelAndView mv, @RequestParam int conId, @RequestParam int proId) {
        Problem pro = problemService.findProblemById(proId);
        request.setAttribute("conId", conId);
        request.setAttribute("pro", pro);
        return "AdminEnd/Contest/contestProblemDetail";
    }

    @GetMapping("/admin/ContestRank")
    public String admincontestRankPage(HttpServletRequest request, ModelAndView mv, int conId) {
        List<ConProStu> cpsList = submissionService.getAll(conId);
        List<Student> stuList = new LinkedList<Student>();
        List<ConStu> csList = contestService.findStuByConId(conId);
        for (ConStu cs : csList) {
            stuList.add(studentService.findStudentById(cs.getStuId()));
        }
        request.setAttribute("startTime",contestService.findContestById(conId).getStartTime().toString());
        request.setAttribute("cpsList", cpsList);
        request.setAttribute("stuList", stuList);
        request.setAttribute("conId", conId);
        return "AdminEnd/Contest/contestRank";
    }

    @GetMapping("/admin/ContestStatus")
    public String adminstatusPage(HttpServletRequest request, ModelAndView mv, int conId) {
        List<Submission> subList = submissionService.getSubByconId(conId);
        request.setAttribute("subList", subList);
        request.setAttribute("conId", conId);
        return "AdminEnd/Contest/contestStatus";
    }



    @GetMapping("/admin/addContest")
    public String addContestPage(HttpServletRequest request, ModelAndView mv) {
        return "AdminEnd/Contest/addContest";
    }
    @PostMapping("/admin/addContest")
    @ResponseBody
    public int addContest(HttpServletRequest request, ModelAndView mv, RedirectAttributes ra, @RequestParam(value = "conTitle",required = true) String conTitle, @RequestParam(value = "startTime",required = true) Timestamp startTime, @RequestParam(value = "endTime",required = true)Timestamp endTime) {
        HttpSession session = request.getSession(true);
        Admin admin = (Admin)session.getAttribute("admin");
        System.out.println(conTitle);
        System.out.println(startTime);
        System.out.println(endTime);
        int res = contestService.addNewContest(conTitle, admin.getAdminId(), startTime, endTime);
        System.out.println(res);
        return res;
    }

    @GetMapping("/admin/addContest/probList")
    public String addContestProListPage(HttpServletRequest request, ModelAndView mv, int conId) {
        List<Problem> probList = problemService.getAll();
        request.setAttribute("probList", probList);
        request.setAttribute("conId", conId);
        return "AdminEnd/Contest/addContestProbList";
    }
    @PostMapping("/admin/addContest/probList")
    @ResponseBody
    public int addContestProList(HttpServletRequest request, ModelAndView mv, RedirectAttributes ra,
                                 @RequestParam(value = "conId", required = true)int conId,
                                 @RequestParam(value = "proIdList", required = true)List<Integer> proIdList) {
        HttpSession session = request.getSession(true);
        //Admin admin = (Admin)session.getAttribute("admin");
        for (int i : proIdList) {
            contestService.addNewConPro(conId, i);
            problemService.setHiddenTrue(i);
        }
        return conId;
    }
}
