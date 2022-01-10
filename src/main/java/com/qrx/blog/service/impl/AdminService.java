package com.qrx.blog.service.impl;

import com.qrx.blog.domain.Admin;
import com.qrx.blog.dto.AdminDto;
import com.qrx.blog.dto.PageDto;

import java.util.Map;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
public interface AdminService {
    /**
     * 添加管理员
     * @param adminDto
     * @return
     */
    void addAdmin(AdminDto adminDto);

    /**
     * 删除管理员
     * @param id
     * @return
     */
    void deleteAdmin(Integer id);

    /**
     * 修改管理员密码
     * @param adminDto
     */
    void updatePassword(AdminDto adminDto);

    /**
     * 分页获取管理员信息
     * @param pageDto
     * @return
     */
    void getAllAdmins(PageDto pageDto);

    /**
     * 管理员登录
     * @param adminDto
     */
    String AdminLogin(AdminDto adminDto);

    /**
     * 更改呢称
     * @param adminDto
     */
    void updateNickname(AdminDto adminDto);

    /**
     * 登出
     */
    void signOut();
}
