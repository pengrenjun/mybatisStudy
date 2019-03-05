package MybatisExc.mapper;

import MybatisExc.entity.SysUser;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.cache.decorators.FifoCache;

import java.util.List;
import java.util.Map;

/*@CacheNamespace(
        eviction= FifoCache.class,
        flushInterval=60000,
        size=1024,
        readWrite=true
)*/
public interface UserMapper {

    /**
     * 通过用户id查找用户主信息
     */
    SysUser getSysUserById(Long id);

    /**
     * 查询所用用户信息
     */
    List<SysUser> getAllsysUserList();

    /**
     * 查询用户基本信息及其详细信息(一对一 使用 resultMap 配置一对一映射)
     */
    SysUser getSysUserInfoById(Long id);

    /**
     * 查询用户基本信息及其详细信息(使用 resultMap 的association 标签配置一对一映射)
     */
    SysUser getSysUserInfoByIdAssociation(Long id);

    /**
     * 查询用户基本信息及其详细信息(使用 association 标签的嵌套查询方式)
     */
    SysUser getSysUserInfoByIdAssocaion_Select(Long id);


    /**
     * 添加新的用户数据 返回创建后的主键
     */
     Long insertNewSysUser(SysUser sysUser);

    /**
     * foreach 实现 in 集合
     */
    List<SysUser> getSysusersByIds(@Param("ids") Long[] ids);

    /**
     *foreach 实现批量插入
     */
    int insertSysUsersList(@Param("sysUserList") List<SysUser> sysUserList);

    /**
     * foreach 实现动态 UPDATE 参数类型为Map
     */
    int updateSysUserList(@Param("userMap") Map<String,Object> userMap,@Param("id") Long id);


    /**
     *通过用户一部分信息 用户名,密码,id(用户作为传参)查找用户主信息列表数据
     */
    SysUser getSysUserByUserCondition(SysUser sysUser);



    /**
     * 通过用户id查询用户的基本信息及其所具有的所用角色信息
     */
    SysUser getSysuserInfoAndRolelistInfo(Long id);

    /**
     * 通过用户id查询用户的基本信息及其所具有的所用角色信息,权限信息(嵌套结果映射方式)
     */
    SysUser getSysuserInfoAndRoleAndSysPrivilegelistInfo(Long id);

    /**
     * 通过用户id查询用户的基本信息及其所具有的所用角色信息,权限信息(嵌套结果查询方式)
     */
    SysUser getSysuserRolePrgBySelect(Long id);





}
