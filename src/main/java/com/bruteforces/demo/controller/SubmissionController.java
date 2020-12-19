package com.bruteforces.demo.controller;

import com.bruteforces.demo.entity.*;
import com.bruteforces.demo.utils.Client;
import com.bruteforces.demo.utils.SubmitResult;
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

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ProblemService problemService;

    // ClientEnd


    @PostMapping("/submit")
    public String submitPage(HttpServletRequest request, ModelAndView mv, String language ,String subSrc, int proId) throws IOException, InterruptedException {
        System.out.println(language);
        System.out.println(proId);
        System.out.println(subSrc);
        HttpSession session = request.getSession(true);
        Student stu = (Student)session.getAttribute("stu");
        int stuId = stu.getStuId();
        Timestamp subTime = new Timestamp(new Date().getTime());
        String subTimeStr = subTime.toString();
        String subtime2 = "";
        for (int i=0;i<subTimeStr.length();++i) {
            if (subTimeStr.substring(i,i+1).equals(":") || subTimeStr.substring(i,i+1).equals(" ")) {
                continue;
            }
            subtime2 += subTimeStr.substring(i,i+1);
        }
        String subInfo = ""+stuId+"_"+proId+"_"+subtime2;

        SubmitResult subResult = new SubmitResult();
        Problem pro = problemService.findProblemById(proId);
        subResult = Client.preprocess(String.valueOf(proId), pro.getProDataNum(), pro.getProTimeLimit(), pro.getProMemoryLimit(), language, subInfo, subSrc);
        List<Submission> subList = submissionService.getSubBystuId(stuId);
        boolean flag = false;
        for (Submission sub : subList) {
            if (sub.getProId()==proId && sub.getConId()==0 && sub.getResult().equals("Accepted")) {
                flag = true;
                break;
            }
        }
        int res = submissionService.addNewSubmission(0, proId, stuId, subSrc, subTime, subResult.result, language, (int)(subResult.time), (int)(subResult.mem), subSrc.length());
        if (!flag) {
            stu.updateSubmissionNum();
            pro.updateSubmissionNum();
            if (subResult.result.equals("Accepted")) {
                stu.updateSolvedNum();
                pro.updateAccpetedNum();
            }
            studentService.updateStu(stu);
            problemService.updateSubAndAcc(pro);
        }
        if (res>0) {
            return "redirect:/status";
        }
        return "redirect:/ProblemList";
    }

    @PostMapping("/ContestSubmit")
    public String contestSubmit(HttpServletRequest request, ModelAndView mv, RedirectAttributes ra, String language, String subSrc, int conId, int proId) throws IOException, InterruptedException {
        System.out.println("ContestSubmit");
        HttpSession session = request.getSession(true);
        Student stu = (Student)session.getAttribute("stu");
        int stuId = stu.getStuId();
        Timestamp subTime = new Timestamp(new Date().getTime());
        String subTimeStr = subTime.toString();
        String subtime2 = "";
        for (int i=0;i<subTimeStr.length();++i) {
            if (subTimeStr.substring(i,i+1).equals(":") || subTimeStr.substring(i,i+1).equals(" ")) {
                continue;
            }
            subtime2 += subTimeStr.substring(i,i+1);
        }
        String subInfo = ""+stuId+"_"+proId+"_"+subtime2;

        Problem pro = problemService.findProblemById(proId);
        SubmitResult subResult = new SubmitResult();
        subResult = Client.preprocess(String.valueOf(proId), pro.getProDataNum(), pro.getProTimeLimit(), pro.getProMemoryLimit()*1024, language, subInfo, subSrc);

        int res = submissionService.addNewSubmission(conId, proId, stuId, subSrc, subTime, subResult.result, language, (int)(subResult.time), (int)(subResult.mem/1024), subSrc.length());

        String cpsId = conId + "_" + proId + "_" + stuId;
        ConProStu cps = submissionService.findcpsById(cpsId);
        if (cps.getIfAccepted()!=1) {
            if (subResult.getResult().equals("Accepted")) {
                cps.setIfAccepted(1);
                cps.setSolveTime(subTime);
            }
            else {
                cps.addAttempTimes();
                System.out.println(cps.getAttempTimes());
            }
            submissionService.updateCPS(cps);
        }

        ra.addAttribute("conId", conId);
        return "redirect:/ContestStatus";

    }

    @GetMapping("/status")
    public String statusPage(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession(true);
        Student stu = (Student)session.getAttribute("stu");
        if (stu==null) {
            return "redirect:/login";
        }
        List<Submission> subList = submissionService.getSubByconId(0);
        request.setAttribute("subList", subList);
        return "ClientEnd/Status/status";
    }

    @GetMapping("/statusDetail")
    public String statusDetailPage(HttpServletRequest request, ModelAndView mv, int subId) {
        Submission sub = submissionService.getSubmissionById(subId);
        request.setAttribute("sub", sub);
        return "ClientEnd/Status/statusDetail";
    }




    //AdminEnd


    @GetMapping("/admin/status")
    public String adminstatusPage(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession(true);
        Admin admin = (Admin)session.getAttribute("admin");
        if (admin == null) {
            return "redirect:/admin/Login";
        }
        List<Submission> subList = submissionService.getSubByconId(0);
        request.setAttribute("subList", subList);
        return "AdminEnd/Status/status";
    }
    @GetMapping("/admin/statusDetail")
    public String adminstatusDetailPage(HttpServletRequest request, ModelAndView mv, int subId) {
        Submission sub = submissionService.getSubmissionById(subId);
        request.setAttribute("sub", sub);
        return "AdminEnd/Status/statusDetail";
    }


    // Online IDE
    @GetMapping("/OnlineIDESub")
    public String onlineIDEPage(HttpServletRequest request, ModelAndView mv) {
        return "ClientEnd/OnlineIDE/onlineIDE";
    }
    @PostMapping("/OnlineIDESub")
    @ResponseBody
    public Map<String, Object> onlineIdepost(HttpServletRequest request, WebParam.Mode mv,
                                             @RequestParam String code,
                                             @RequestParam String input,
                                             @RequestParam String output,
                                             @RequestParam String language,
                                             @RequestParam int timeLimit,
                                             @RequestParam int memoryLimit) throws IOException, InterruptedException {
        HttpSession session = request.getSession(true);
        Student stu = (Student)session.getAttribute("stu");
        Timestamp subTime = new Timestamp(new Date().getTime());
        String subTimeStr = subTime.toString();
        String subtime2 = "";
        for (int i=0;i<subTimeStr.length();++i) {
            if (subTimeStr.substring(i,i+1).equals(":") || subTimeStr.substring(i,i+1).equals(" ")) {
                continue;
            }
            subtime2 += subTimeStr.substring(i,i+1);
        }
        String subInfo = ""+stu.getStuId()+"_"+subtime2;

        Map<String, Object> mp = Client.onlineIDE(timeLimit, memoryLimit, language, subInfo, code, input);
        return mp;
    }
}
