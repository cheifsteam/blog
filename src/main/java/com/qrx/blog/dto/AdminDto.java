package com.qrx.blog.dto;

/**
 * @author 屈燃希
 * @version 1.0
 * @project
 */
public class AdminDto {

        private Integer id;

        private String adminName;

        private String password;

        private String salt;

        private String img;
        private String newPassword;
        private String passwordConfirm;
        private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getAdminName() {
            return adminName;
        }

        public void setAdminName(String adminName) {
            this.adminName = adminName == null ? null : adminName.trim();
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password == null ? null : password.trim();
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt == null ? null : salt.trim();
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img == null ? null : img.trim();
        }

}
