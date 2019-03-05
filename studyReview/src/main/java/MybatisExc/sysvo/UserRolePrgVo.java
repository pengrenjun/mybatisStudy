package MybatisExc.sysvo;

import MybatisExc.Enum.Status;
import MybatisExc.entity.SysRole;
import MybatisExc.entity.SysUserInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description 用户, 角色,权限视图
 * @Author pengrj
 * @CreateDate 2018-05-17 下午 2:06
 * @Version 1.0
 */
public class UserRolePrgVo implements Serializable {

    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String userPassword;
    /**
     * 邮箱
     */
    private String userEmail;
    /**
     * 简介
     */
    private String userInfo;
    /**
     * 头像
     */
    private byte[] headImg;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 用户角色
     */
    private SysRole role;

    /**
     * 用户的角色集合
     */
    private List<SysRoleVo> roleVoList;

    /**
     *用户详细信息
     */
   // private SysUserInfo sysUserInfo;


    /**
     * 单据状态
     */
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public byte[] getHeadImg() {
        return headImg;
    }

    public void setHeadImg(byte[] headImg) {
        this.headImg = headImg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public List<SysRoleVo> getRoleVoList() {
        return roleVoList;
    }

    public void setRoleVoList(List<SysRoleVo> roleVoList) {
        this.roleVoList = roleVoList;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
