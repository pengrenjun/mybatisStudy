package MybatisExc.sysvo;

import MybatisExc.entity.CreateInfo;
import MybatisExc.entity.SysUser;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description 角色视图类
 * @Author pengrj
 * @CreateDate 2018-05-17 下午 2:19
 * @Version 1.0
 */
public class SysRoleVo implements Serializable {
    /**
     * 角色ID
     */
    private Long id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 有效标志
     */
    private String enabled;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 角色包含的权限列表
     */
    List<SysPrivilegeVo> privilegeVoList;
    /**
     * 用户信息
     */
   // private SysUser user;
    /**
     * 创建信息
     */
  //  private CreateInfo createInfo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<SysPrivilegeVo> getPrivilegeVoList() {
        return privilegeVoList;
    }

    public void setPrivilegeVoList(List<SysPrivilegeVo> privilegeVoList) {
        this.privilegeVoList = privilegeVoList;
    }
}
