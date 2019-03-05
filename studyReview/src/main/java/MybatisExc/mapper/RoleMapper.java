package MybatisExc.mapper;

import MybatisExc.entity.SysRole;

import java.util.List;

public interface RoleMapper {

    /**
     * 根据用户id查询有的角色信息,同时嵌套查询角色对应的权限信息
     */
    List<SysRole> getSysRoleListByUserId(Long userId);
}
