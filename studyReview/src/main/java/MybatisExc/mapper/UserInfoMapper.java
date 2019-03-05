package MybatisExc.mapper;

import MybatisExc.entity.SysUserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserInfoMapper {
    /**
     * 根据主键id获取用户的详细信息
     * @param id
     * @return
     */
    SysUserInfo getSysUserInfoById(@Param("id") Long id);
}
