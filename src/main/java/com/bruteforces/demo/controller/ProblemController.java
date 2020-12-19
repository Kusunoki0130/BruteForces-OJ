package com.bruteforces.demo.controller;

import com.bruteforces.demo.entity.Problem;
import com.bruteforces.demo.entity.Student;
import com.bruteforces.demo.service.ContestService;
import com.bruteforces.demo.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class ProblemController {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private ContestService contestService;

    // ClientEnd

    @GetMapping("/OnlineIDE")
    public String onlineIDE(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession(true);
        Student stu = (Student)session.getAttribute("stu");
        if (stu==null) {
            return "redirect:/login";
        }
        return "ClientEnd/OnlineIDE/onlineIDE";
    }

    @GetMapping("/ProblemList")
    public String problemListPage(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession(true);
        Student stu = (Student)session.getAttribute("stu");
        if (stu==null) {
            return "redirect:/login";
        }
        List<Problem> rawList = problemService.getAll();
        List<Problem> proList = new LinkedList<Problem>();
        for (Problem prob : rawList) {
            if (prob.getIsHidden()==0) {
                proList.add(prob);
            }
        }
        request.setAttribute("list", proList);
        return "ClientEnd/Problem/problemList";
    }

    @GetMapping("/Problem")
    public String problemDetailPage(HttpServletRequest request, ModelAndView mv, @RequestParam int proId) {
        Problem pro = problemService.findProblemById(proId);
        request.setAttribute("pro", pro);
        return "ClientEnd/Problem/problemDetail";
    }


    // AdminEnd

    @GetMapping("/admin/ProblemList")
    public String adminProblemListPage(HttpServletRequest request, ModelAndView mv) {
        List<Problem> proList = problemService.getAll();
        request.setAttribute("list", proList);
        return "AdminEnd/Problem/problemList";
    }

    @PostMapping("/admin/ProblemHidden")
    public void adminProblemHidden(@RequestParam("proList0")List<Integer> proList0, @RequestParam("proList1")List<Integer> proList1) {
        for (int i : proList0) {
            problemService.setHiddenFalse(i);
        }
        for (int i : proList1) {
            problemService.setHiddenTrue(i);
        }
    }

    @GetMapping("/admin/problem")
    public String adminproblemDetailPage(HttpServletRequest request, ModelAndView mv, @RequestParam int proId) {
        Problem pro = problemService.findProblemById(proId);
        request.setAttribute("pro", pro);
        return "AdminEnd/Problem/problemDetail";
    }

    @GetMapping("/admin/addProblem")
    public String addProlbemPage(HttpServletRequest request, ModelAndView mv) {
        return "AdminEnd/Problem/addProblem";
    }
    @PostMapping("/admin/addProblem")
    @ResponseBody
    public int addProlbem(HttpServletRequest request, ModelAndView mv,
                          @RequestParam String proTitle,
                          @RequestParam String proInfo,
                          @RequestParam String proInput,
                          @RequestParam String proOutput,
                          @RequestParam String proInputSample,
                          @RequestParam String proOutputSample,
                          @RequestParam int proTimeLimit,
                          @RequestParam int proMemoryLimit,
                          @RequestParam int proDataNum,
                          @RequestParam int isHidden) {
        HttpSession session = request.getSession(true);
        int res = problemService.addNewProblem(proTitle, proInfo, proInput, proOutput, proInputSample, proOutputSample, proTimeLimit, proMemoryLimit, proDataNum, "0", isHidden);
        System.out.println(res);
        System.out.println(proInfo);
        Problem pro = (Problem) problemService.findProblemById(res);
        System.out.println(pro.getProInfo());
        session.setAttribute("proId", res);
        return res;
    }

    @GetMapping("/admin/addProblemData")
    public String addProblemDataPage(HttpServletRequest request, ModelAndView mv, @RequestParam int proId) {
        request.setAttribute("proId", proId);
        Problem pro = (Problem) problemService.findProblemById(proId);
        request.setAttribute("proDataNum", pro.getProDataNum());
        return "AdminEnd/Problem/addProblemData";
    }
    @PostMapping("/admin/addProblemData")
    @ResponseBody
    public Map<String, Object> addProblemData(HttpServletRequest request, ModelAndView mv, @RequestParam(value="proData",required = true) MultipartFile[] proData) throws IOException {
        HttpSession session = request.getSession(true);
        int proId = (int)session.getAttribute("proId");
        Properties address = new Properties();
        address.load(new ClassPathResource("address.properties").getInputStream());
        // 标准答案路径
        for (int i=0;i<proData.length;++i) {
            String ansAddr = address.getProperty("ansAddr");
            String fileName = proData[i].getOriginalFilename();
            String filePath = ansAddr + "/" + proId;
            File localFile = new File(filePath);
            System.out.println(filePath);
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            String path = filePath + "/" + fileName;
            try {
                File saveFile = new File(path);
                proData[i].transferTo(saveFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Map<String, Object> json = new HashMap<String, Object>();
        json.put("message","上传成功");
        return json;
    }
}
