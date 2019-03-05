package MybatisSqlTest;

import MybatisExc.Util.SqlsessionUtil;
import MybatisExc.entity.SysPrivilege;
import MybatisExc.entity.SysRole;
import MybatisExc.mapper.PrivilegeMapper;
import MybatisExc.mapper.RoleMapper;
import MybatisExc.mapper.UserMapper;
import MybatisExc.mapper.UserRolePrgVoMapper;
import MybatisExc.sysvo.UserRolePrgVo;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @Description 用户权限角色测试类
 * @Author pengrj
 * @CreateDate 2018-05-17 上午 9:41
 * @Version 1.0
 */
public class OtherMapperTest {


    private static PrivilegeMapper privilegeMapper;

    private static RoleMapper roleMapper;

    private static UserRolePrgVoMapper userRolePrgVoMapper;

    static {
        try {
            privilegeMapper= SqlsessionUtil.getMapperByClass(PrivilegeMapper.class);
            roleMapper=SqlsessionUtil.getMapperByClass(RoleMapper.class);
            userRolePrgVoMapper=SqlsessionUtil.getMapperByClass(UserRolePrgVoMapper.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过角色id 查询角色所具有的所有权限
     */
    @Test
    public void getsysPrivilegeListByRoleId(){
        List<SysPrivilege> sysPrivilegeList=privilegeMapper.getsysPrivilegeListByRoleId((long)1);
        SqlsessionUtil.closeSession();
    }

    /**
     * 根据用户id查询有的角色信息,同时嵌套查询角色对应的权限信息
     */
    @Test
    public void getSysRoleListByUserId(){
        List<SysRole> sysRoleList=roleMapper.getSysRoleListByUserId((long) 1);
        //启动延迟加载
        for(SysRole sysRole:sysRoleList){
            sysRole.getPrivilegeList();
        }
        SqlsessionUtil.closeSession();
    }

    /**
     * 查询用户角色权限视图信息
     */
    @Test
    public void getsysuserInfoVoList(){
        List<UserRolePrgVo> userRolePrgVoList=userRolePrgVoMapper.SysPrivilegeVoList();
        SqlsessionUtil.closeSession();
    }



}
