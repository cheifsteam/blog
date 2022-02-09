package com.qrx.blog.controller.admin;

import com.qrx.blog.dto.AdminDto;
import com.qrx.blog.dto.PageDto;
import com.qrx.blog.service.AdminService;
import com.qrx.blog.util.AjaxResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
@RestController
public class AdminController {
   @Resource
   public AdminService adminService;
   @PostMapping("/admin/admin/add")
   public AjaxResult addAdmin(@RequestBody AdminDto adminDto){
      adminService.addAdmin(adminDto);
      return AjaxResult.success();
   }
   @DeleteMapping("/admin/admin/delete/{id}")
   public AjaxResult deleteAdmin(@PathVariable Integer id)
   {
      adminService.deleteAdmin(id);
      return AjaxResult.success();
   }
   @PostMapping("/admin/admin/getAll")
   public AjaxResult getAllAdmin(@RequestBody PageDto pageDto)
   {
      adminService.getAllAdmins(pageDto);
      return AjaxResult.success(pageDto);
   }
   @PostMapping("/admin/admin/login")
   public AjaxResult AdminLogin(@RequestBody AdminDto adminDto)
   {
      String token = adminService.AdminLogin(adminDto);
      return AjaxResult.success("操作成功",token);

   }
   @PostMapping("/admin/admin/signOut")
   public AjaxResult AdminSignOut()
   {
      adminService.signOut();
      return AjaxResult.success("登出成功");
   }
   @PostMapping("/admin/admin/updatePassword")
   public AjaxResult UpdatePassword(@RequestBody  AdminDto adminDto){
      adminService.updatePassword(adminDto);
      return AjaxResult.success("修改密码成功");
   }
   @PostMapping("/admin/admin/updateNickname")
   public AjaxResult UpdateNickname(@RequestBody  AdminDto adminDto)
   {
      adminService.updateNickname(adminDto);
      return AjaxResult.success("修改呢称成功");
   }
}
