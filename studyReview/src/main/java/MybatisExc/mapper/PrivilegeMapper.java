package MybatisExc.mapper;

import MybatisExc.entity.SysPrivilege;

import java.util.List;

public interface PrivilegeMapper {

    /**
     * 通过角色id 查询角色所具有的所有权限
     */
    List<SysPrivilege>  getsysPrivilegeListByRoleId(Long roleId);

}
