package com.bruteforces.demo.controller;

import com.bruteforces.demo.entity.Admin;
import com.bruteforces.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 管理端主页
     * @param request
     * @param modelAndView
     * @return
     */
    @GetMapping("/admin")
    public String adminPage(HttpServletRequest request, ModelAndView modelAndView) {
        HttpSession session = request.getSession(true);
        Admin admin = (Admin)session.getAttribute("admin");
        if (admin==null) {
            return "redirect:/admin/Login";
        }
        return "AdminEnd/PAGE_adminIndex";
    }

    /**
     * 管理员登录
     * @param request
     * @param mv
     * @return
     */
    @GetMapping("/admin/Login")
    public String adminLoginPage(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession(true);
        Admin admin = (Admin)session.getAttribute("admin");
        if (admin==null) {
            return "AdminEnd/login";
        }
        return "redirect:/admin";
    }
    @PostMapping("/admin/Login")
    public String adminLogin(@RequestParam(required=false) String adminUsername, String adminPassword, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Admin res = adminService.findAdminByUsername(adminUsername);
        if (res==null || !adminPassword.equals(res.getAdminPassword())) {
            return "redirect:/admin/Login";
        }
        session.setMaxInactiveInterval(24*60*60);
        session.setAttribute("admin", res);
        session.setAttribute("adExist", 1);
        System.out.println("?");
        return "redirect:/admin";
    }

    @GetMapping("/admin/Logout")
    public String adminLogout(HttpServletRequest request, ModelAndView mv) {
        HttpSession session = request.getSession(true);
        session.setAttribute("admin", null);
        session.setAttribute("adExist", 0);
        return "redirect:/admin";
    }
    /**
     * 该方法暂时没用
     * @param adminUsername
     * @param adminPassword
     * @param newPassword
     * @return
     */
    @RequestMapping("/admin/rePassword")
    @ResponseBody
    public String rePassword(@RequestParam(required=false) String adminUsername, String adminPassword, String newPassword) {
        int res = adminService.rePassword(adminUsername, adminPassword, newPassword);
        if (res>0) {
            return "修改成功";
        }
        return "修改失败（检查用户名和原密码）";
    }
}
