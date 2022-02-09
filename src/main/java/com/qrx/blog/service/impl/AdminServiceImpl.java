package com.qrx.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qrx.blog.domain.Admin;
import com.qrx.blog.domain.AdminExample;
import com.qrx.blog.dto.AdminDto;
import com.qrx.blog.dto.PageDto;
import com.qrx.blog.excpetion.MyException;
import com.qrx.blog.mapper.AdminMapper;
import com.qrx.blog.service.AdminService;
import com.qrx.blog.util.copyUtils.BeanCopyUtils;
import com.qrx.blog.util.jwt.WebTokenUtil;
import com.qrx.blog.util.security.Encrypt;
import com.qrx.blog.util.security.SecurityRandom;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.List;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;
    private AdminExample adminExample;
    @Resource
    private WebTokenUtil webTokenUtil;
    @Resource
    public HttpSession httpSession;
    //添加管理员
    @Override
    public void addAdmin(AdminDto adminDto) {
        Admin admin = BeanCopyUtils.copyBean(adminDto, Admin.class);
        if (getAdminByName(admin.getAdminName())!=null) {

            throw new MyException("该管理员已经存在");

        }
        else {
            admin.setSalt(SecurityRandom.getRandom());
            String encryptPassword = Encrypt.MD5Encrypt(admin.getPassword()+admin.getSalt());
            admin.setPassword(encryptPassword);
            adminMapper.insert(admin);
        }

    }
    @Override
    //删除管理员
    public void deleteAdmin(Integer id)
    {
        adminMapper.deleteByPrimaryKey(id);
    }
    @Override
    //获取所有管理员
    public void getAllAdmins(PageDto pageDto)
    {

        PageHelper.startPage(pageDto.getPage(), pageDto.getPageSize());
        adminExample=new AdminExample();
        List<Admin> admins = adminMapper.selectByExample(adminExample);
        PageInfo<Admin> pageInfo=new PageInfo<>(admins);
        List<AdminDto> adminDtos = BeanCopyUtils.copyListProperties(admins,AdminDto::new);
        pageDto.setTotal((int) pageInfo.getTotal());
        pageDto.setData(adminDtos);
    }
    @Override
    public String AdminLogin(AdminDto adminDto){
        Admin admin = getAdminByName(adminDto.getAdminName());
        if (admin==null){
            throw new MyException("账号错误");
        }
        String s=Encrypt.MD5Encrypt(adminDto.getPassword()+admin.getSalt());
        if(!(admin.getPassword().equals(s)))
        {
            throw new MyException("密码错误");
        }
        String token=createWebToken(admin);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Set-Token", token);
        return token;

    }
    @Override
    public void signOut(){
        Integer userid = (Integer) httpSession.getAttribute("id");
        webTokenUtil.deleteToken(userid);
    }
    @Override
    public void updatePassword(AdminDto adminDto){
        Admin admin = adminMapper.selectByPrimaryKey(adminDto.getId());
        if (admin==null)
        {
            throw new MyException("管理员不存在");
        }
        String s=Encrypt.MD5Encrypt(admin.getPassword()+admin.getSalt());
        if(!(admin.getPassword().equals(s)))
        {
            throw new MyException("密码错误");
        }
        if (admin.getPassword().equals(Encrypt.MD5Encrypt(admin.getSalt()+adminDto.getNewPassword())))
        {
            throw new MyException("新密码与旧密码一致");
        }
        if (!(adminDto.getNewPassword().equals(adminDto.getPasswordConfirm())))
        {
            throw new MyException("两次输入密码不一致");
        }
        //重设混淆盐
        admin.setSalt(SecurityRandom.getRandom());
        String encryptPassword=Encrypt.MD5Encrypt(adminDto.getNewPassword()+admin.getSalt());
        admin.setPassword(encryptPassword);
        adminMapper.updateByPrimaryKeySelective(admin);
    }
    @Override
    public void updateNickname(AdminDto adminDto){
        Integer id = (Integer) httpSession.getAttribute("id");
        Admin admin = adminMapper.selectByPrimaryKey(id);
        if (admin==null)
        {
            throw new MyException("管理员不存在");
        }
        admin.setAdminName(adminDto.getAdminName());
        admin.setNickname(adminDto.getNickname());
        adminMapper.updateByPrimaryKeySelective(admin);
    }
    public Admin getAdminByName(String name)
    {
        adminExample=new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria().andAdminNameEqualTo(name);
        adminExample.or(criteria);

        List<Admin> admins = adminMapper.selectByExample(adminExample);
        if (admins.isEmpty())
        {
            return null;
        }
        return admins.get(0);
    }
    public String createWebToken(Admin admin){
        Instant now = Instant.now();
        String secretKey= WebTokenUtil.getSecretKey();
        String token = webTokenUtil.create(secretKey, String.valueOf(admin.getId()), now, 5);
        return token;
    }
}
