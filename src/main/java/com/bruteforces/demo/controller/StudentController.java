package com.bruteforces.demo.controller;

import com.bruteforces.demo.entity.Student;
import com.bruteforces.demo.service.StudentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;



    @GetMapping("/")
    public String aaa(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Student stu = (Student)session.getAttribute("stu");
        session.setMaxInactiveInterval(24*60*60);
        if (stu!=null) {
            session.setAttribute("stuExist",1);
        }
        else {
            session.setAttribute("stuExist",0);
        }
        return "ClientEnd/PAGE_stuIndex";

    }

    @GetMapping("/register")
    public String registerPage(HttpServletRequest request) {
        return "ClientEnd/register";
    }
    @PostMapping("/register")
    public String register(@RequestParam(required=false) String stuUsername, String stuPassword, String stuEmail, String stuPhone) {
        Student stu = studentService.findStudentByUsername(stuUsername);
        if (stu==null) {
            int res = studentService.addNewStudent(stuUsername, stuPassword, stuEmail, stuPhone);
            if (res>0) {
                return "ClientEnd/PAGE_stuIndex";
            }
        }
        return "redirect:/register";
    }

    /**
     * 客户端学生登录
     * @param request
     * @param mv
     * @return
     */
    @GetMapping("/login")
    public String loginPage(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession(true);
        Student stu = (Student) session.getAttribute("stu");
        if (stu==null) {
            return "ClientEnd/login";
        }
        return "redirect:/";
    }
    @PostMapping("/login")
    public String login(@RequestParam(required=false) String stuUsername, String stuPassword, HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession(true);
        System.out.println(stuUsername + " " + stuPassword);
        Student res = studentService.findStudentByUsername(stuUsername);
        if (res==null || !stuPassword.equals(res.getStuPassword())) {
            mv.setViewName("failed");
            return "ClientEnd/login";
        }
        mv.setViewName("index");
        session.setMaxInactiveInterval(24*60*60);
        session.setAttribute("stu", res);
        session.setAttribute("stuExist",1);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession(true);
        session.setAttribute("stu", null);
        session.setAttribute("stuExist",0);
        return "redirect:/";
    }

//    /**
//     * 客户端主页
//     * @param request
//     * @param mv
//     * @return
//     */
//    @GetMapping("/index")
//    public String indexPage(HttpServletRequest request, ModelAndView mv) {
//        HttpSession session = request.getSession(true);
//        Student stu = (Student)session.getAttribute("stu");
//        if (stu==null) {
//            return "redirect:/login";
//        }
//        return "index";
//    }

    /**
     * 修改密码，暂时没用
     * @param stuUsername
     * @param stuPassword
     * @param newPassword
     * @return
     */
    @RequestMapping("/rePassword")
    @ResponseBody
    public String rePassword(@RequestParam(required=false) String stuUsername, String stuPassword, String newPassword) {
        int res = studentService.rePassword(stuUsername, stuPassword, newPassword);
        if (res>0) {
            return "修改成功";
        }
        return "修改失败（检查用户名和原密码）";
    }
}
