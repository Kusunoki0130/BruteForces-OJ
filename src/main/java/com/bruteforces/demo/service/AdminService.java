package com.bruteforces.demo.service;

import com.bruteforces.demo.dao.AdminMapper;
import com.bruteforces.demo.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired(required = false)
    private AdminMapper adminMapper;

    public Admin findAdminById(int adminId) {
        return adminMapper.selectAdminByAdminId(adminId);
    }

    public Admin findAdminByUsername(String adminUsername) {
        return adminMapper.selectAdminByAdminUsername(adminUsername);
    }

    public int rePassword(String adminUsername, String adminPassword, String newPassword) {
        Admin admin = adminMapper.selectAdminByAdminUsername(adminUsername);
        if (admin==null) {
            return 0;
        }
        admin.setAdminPassword(newPassword);
        return adminMapper.updateAdmin(admin);
    }
}
