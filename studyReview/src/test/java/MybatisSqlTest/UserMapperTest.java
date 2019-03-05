package MybatisSqlTest;

import MybatisExc.Enum.Status;
import MybatisExc.Util.SqlsessionUtil;
import MybatisExc.entity.SysPrivilege;
import MybatisExc.entity.SysRole;
import MybatisExc.entity.SysUser;
import MybatisExc.entity.SysUserInfo;
import MybatisExc.mapper.UserMapper;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

/**
 * @Description 用户信息sql测试类
 * @Author pengrj
 * @CreateDate 2018-05-15 下午 2:31
 * @Version 1.0
 */
public class UserMapperTest {

    private static  UserMapper userMapper;

    static {
        try {
             userMapper= SqlsessionUtil.getMapperByClass(UserMapper.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过用户id查找用户主信息
     */
    @Test
    public void testSelectUserById(){
        SysUser sysUser=userMapper.getSysUserById((long) 1);
        System.out.println(sysUser.getUserName());
        System.out.println(sysUser.getCreateTime());
        SqlsessionUtil.closeSession();
    }

    /**
     * 查询所用用户信息
     */
    @Test
    public void selectAllsysUser(){
        List<SysUser> userList=userMapper.getAllsysUserList();
        SqlsessionUtil.closeSession();
    }


    /**
     * 查询用户基本信息及其详细信息(一对一 使用 resultMap 配置一对一映射)
     */
    @Test
    public void getSysUserInfoById(){
        SysUser sysUser=userMapper.getSysUserInfoById((long) 1);
        System.out.println(JSONObject.toJSONString(sysUser.getSysUserInfo()));
        SqlsessionUtil.closeSession();
    }

    /**
     * 查询用户基本信息及其详细信息(使用 resultMap 的association 标签配置一对一映射)
     */
    @Test
    public void getSysUserInfoByIdAssociation(){
        SysUser sysUser=userMapper.getSysUserInfoByIdAssociation((long) 1);
        SqlsessionUtil.closeSession();
    }

    /**
     * 查询用户基本信息及其详细信息(使用 association 标签的嵌套查询方式)
     */
    @Test
    public void getSysUserInfoByIdAssocaion_Select(){
        SysUser sysUser=userMapper.getSysUserInfoByIdAssocaion_Select((long) 1);

        //按需延迟加载用户的详细信息(触发某些方法或者调用get方法可以启动延迟加载)
        sysUser.toString();
        SysUserInfo sysUserInfo=sysUser.getSysUserInfo();
        SqlsessionUtil.closeSession();

    }

    /**
     * 新增用户信息并且返回自增主键
     */
    @Test
    public void insertNewSysUser(){
        SysUser sysUser=null;
        sysUser.setUserName("xiaogang");
        sysUser.setCreateTime(new Date());
        sysUser.setUserEmail("adf@qq.com");
        sysUser.setUserPassword("134523452");
        Long id=userMapper.insertNewSysUser(sysUser);
        SqlsessionUtil.commit();
        SqlsessionUtil.closeSession();
    }

    /**
     * foreach 实现 in 集合 实现用户查询
     */
    @Test
    public void getSysusersByIds(){
        Long[] ids=new Long[]{Long.valueOf(1), Long.valueOf(1006)};
        List<SysUser> sysUserList=userMapper.getSysusersByIds(ids);
        SqlsessionUtil.closeSession();
    }

    /**
     * foreach 实现批量插入
     */
    @Test
    public void insertSysUsersList(){
        SysUser sysUser1=new SysUser();
        sysUser1.setUserName("xiaoxiao");
        sysUser1.setUserPassword("1201234123");
        sysUser1.setCreateTime(new Date());

        SysUser sysUser2=new SysUser();
        sysUser2.setUserName("xiaolzi");
        sysUser2.setUserPassword("245245214352");
        sysUser2.setCreateTime(new Date());

        List<SysUser> sysUserList=new ArrayList<SysUser>();
        sysUserList.add(sysUser1);
        sysUserList.add(sysUser2);
        int count=userMapper.insertSysUsersList(sysUserList);
        SqlsessionUtil.closeSession();
    }

    /**
     *  foreach 实现动态 UPDATE 参数类型为Map
     */
    @Test
    public void updateSysUserList(){

        Map<String,Object> userMap=new HashMap<String,Object>();
        userMap.put("user_name","administrator");
        userMap.put("user_password",23423413);
        int a=userMapper.updateSysUserList(userMap, Long.valueOf(1));
        SqlsessionUtil.closeSession();
    }

    /**
     *通过用户一部分信息 用户名,密码,id(用户作为传参)查找用户主信息列表数据
     */
    @Test
    public void getSysUserByUserCondition(){
        SysUser sysUser=new SysUser();
        sysUser.setId((long)1);
        SysUser user=userMapper.getSysUserByUserCondition(sysUser);
        userMapper.getSysUserByUserCondition(sysUser);
        user.toString();
        SqlsessionUtil.closeSession();
    }


    /**
     * 通过用户id查询用户的基本信息及其所具有的所用角色信息(嵌套结果映射)
     *
     */
    @Test
    public void getSysuserInfoAndRolelistInfo(){
        SysUser sysUser=userMapper.getSysuserInfoAndRolelistInfo((long) 1);
        SqlsessionUtil.closeSession();
    }

    /**
     * 通过用户id查询用户的基本信息及其所具有的所用角色信息,权限信息(嵌套结果映射)
     */
    @Test
    public void getSysuserInfoAndRoleAndSysPrivilegelistInfo(){
        SysUser sysUser=userMapper.getSysuserInfoAndRoleAndSysPrivilegelistInfo((long) 1);
        SqlsessionUtil.closeSession();
    }

    /**
     * 通过用户id查询用户的基本信息及其所具有的所用角色信息,权限信息(嵌套查询方式)
     */
    @Test
    public void getSysuserRolePrgBySelect(){
        SysUser sysUser=userMapper.getSysuserRolePrgBySelect((long)1);

        //启动延迟加载
        List<SysRole> sysRoleList=sysUser.getRoleList();
        for(SysRole sysRole:sysRoleList){
            List<SysPrivilege> sysPrivilegeList=sysRole.getPrivilegeList();
        }


        SqlsessionUtil.closeSession();
    }

    /**
     * 测试单据状态枚举功能
     */
    @Test
    public void testStatusEnum(){
        SysUser sysUser=new SysUser();
        sysUser.setUserName("xiaomingming");
        sysUser.setUserPassword("2452452343546");
        sysUser.setCreateTime(new Date());
        sysUser.setUserInfo("test status");
        sysUser.setStatus(Status.NEW);
        Long id= userMapper.insertNewSysUser(sysUser);
        SqlsessionUtil.commit();
        SysUser sysUser1=userMapper.getSysUserById(id);
        SqlsessionUtil.closeSession();
    }


}
