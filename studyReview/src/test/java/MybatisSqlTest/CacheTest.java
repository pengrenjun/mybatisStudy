package MybatisSqlTest;

import MybatisExc.Util.SqlsessionUtil;
import MybatisExc.entity.SysPrivilege;
import MybatisExc.entity.SysRole;
import MybatisExc.entity.SysUser;
import MybatisExc.mapper.PrivilegeMapper;
import MybatisExc.mapper.RoleMapper;
import MybatisExc.mapper.UserMapper;
import MybatisExc.mapper.UserRolePrgVoMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @Description Mybatis一级二级缓存测试
 * @Author pengrj
 * @CreateDate 2018-05-17 下午 2:56
 * @Version 1.0
 */
public class CacheTest {
    private static UserMapper userMapper;

    private static PrivilegeMapper privilegeMapper;

    private static RoleMapper roleMapper;

    private static UserRolePrgVoMapper userRolePrgVoMapper;

    static {
        try {
            userMapper= SqlsessionUtil.getMapperByClass(UserMapper.class);

            userRolePrgVoMapper=SqlsessionUtil.getMapperByClass(UserRolePrgVoMapper.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //测试一级缓存

    /**
     * @Description 一级缓存存在于一个Sqlsession中,Mybatis会把执行的方法和参数通过算法生成缓存的键值
     * 一级缓存是和Sqlsession绑定的
     */
    @Test
    public void  testOneCache() throws IOException {

        //进行了数据库数据查询
        SysUser sysUserA=userMapper.getSysUserById((long)1);
        sysUserA.setUserName("testCache");
        //并没有进行数据库信息的查询
        SysUser sysUserB=userMapper.getSysUserById((long)1);
        System.out.println(sysUserB.getUserName());
        //sysUserA sysUserB是同一个数据信息
        Assert.assertEquals(sysUserA,sysUserB);
        SqlsessionUtil.closeSession();

        userMapper= SqlsessionUtil.getMapperByClass(UserMapper.class);
        SysUser sysUserC=userMapper.getSysUserById((long)1);
        SysUser sysUserD=userMapper.getSysUserById((long)1);
        Assert.assertEquals(sysUserC,sysUserD);
       // Assert.assertEquals(sysUserA,sysUserC);

    }


    //测试二级缓存
    @Test
    /**
     * @Description 二级缓存存在于sqlSessionFactory中 二级缓存需要配置在xml或者对应的接口中
     * 二级缓存是和命名空间绑定的
     * 效果:
     * a)所有select语句将会被缓存
     * b)所有insert delete update 语句会刷新缓存
     */
    public  void testTwoCache() throws IOException {
        privilegeMapper= SqlsessionUtil.getMapperByClass(PrivilegeMapper.class);

        //sysPrivilegeListA 是从数据库中查询出来的 sysPrivilegeListB  sysPrivilegeListC是从一级缓存中获取的
        List<SysPrivilege> sysPrivilegeListA =privilegeMapper.getsysPrivilegeListByRoleId((long) 1);

        List<SysPrivilege> sysPrivilegeListB =privilegeMapper.getsysPrivilegeListByRoleId((long) 1);

        List<SysPrivilege> sysPrivilegeListC =privilegeMapper.getsysPrivilegeListByRoleId((long) 1);

        //将查询的数据保存到二级缓存中
        SqlsessionUtil.closeSession();

        privilegeMapper= SqlsessionUtil.getMapperByClass(PrivilegeMapper.class);

        //从二级缓存中查询数据
        List<SysPrivilege> sysPrivilegeListD =privilegeMapper.getsysPrivilegeListByRoleId((long) 1);

        List<SysPrivilege> sysPrivilegeListE =privilegeMapper.getsysPrivilegeListByRoleId((long) 1);

        //sysPrivilegeListD sysPrivilegeListE因为读写缓存的缘故 两者都是反序列化得到的 两个不同实例

       //   Assert.assertEquals(sysPrivilegeListD,sysPrivilegeListE);

    }


    //测试Ehcache缓存
    @Test
    public  void testEhcache() throws IOException {
        roleMapper=SqlsessionUtil.getMapperByClass(RoleMapper.class);
        List<SysRole> sysRoleListA=  roleMapper.getSysRoleListByUserId((long) 1);
        List<SysRole> sysRoleListB=  roleMapper.getSysRoleListByUserId((long) 1);
        List<SysRole> sysRoleListC=  roleMapper.getSysRoleListByUserId((long) 1);
        SqlsessionUtil.closeSession();

        roleMapper=SqlsessionUtil.getMapperByClass(RoleMapper.class);
        //从二级缓存中查询数据
        List<SysRole> sysRoleListE =roleMapper.getSysRoleListByUserId((long) 1);

        List<SysRole> sysRoleListF =roleMapper.getSysRoleListByUserId((long) 1);
        SqlsessionUtil.closeSession();

    }


}
