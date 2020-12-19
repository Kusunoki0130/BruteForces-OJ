package com.bruteforces.demo.dao;

import com.bruteforces.demo.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {

    Admin selectAdminByAdminId(int adminId);

    Admin selectAdminByAdminUsername(String adminUsername);

    int updateAdmin(Admin admin);
}
