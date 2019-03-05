package tk.mybatis.simple.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSObject;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;
import tk.mybatis.simple.type.Enabled;

public class UserMapperTest extends BaseMapperTest {

	Logger logger= LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void testSelectById(){
		//获取 sqlSession
		SqlSession sqlSession = getSqlSession();
		try {
			//获取 UserMapper 接口
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//调用 selectById 方法，查询 id = 1 的用户
			SysUser user = userMapper.selectById(1l);
			//user 不为空
			Assert.assertNotNull(user);
			//userName = admin
			Assert.assertEquals("admin", user.getUserName());
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAll(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//调用 selectAll 方法查询所有用户
			List<SysUser> userList = userMapper.selectAll();
			//结果不为空
			Assert.assertNotNull(userList);
			//用户数量大于 0 个
			Assert.assertTrue(userList.size() > 0);
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	/*查询的结果包含用户的角色信息及用户信息*/
	@Test
	public void testSelectRolesByUserId(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//调用 selectRolesByUserId 方法查询用户的角色
			List<SysRole> roleList = userMapper.selectRolesByUserId(1L);
			//结果不为空
			Assert.assertNotNull(roleList);
			//角色数量大于 0 个
			Assert.assertTrue(roleList.size() > 0);
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}

	//根据用户id和角色有效性查询用户的所有角色信息
	@Test
	public void testSelectRolesByUserIdAndRoleEnabled(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//调用 selectRolesByUserIdAndRoleEnabled 方法查询用户的角色
			List<SysRole> roleList = userMapper.selectRolesByUserIdAndRoleEnabled(1L, 1);
			//结果不为空
			Assert.assertNotNull(roleList);
			//角色数量大于 0 个
			Assert.assertTrue(roleList.size() > 0);
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	//根据用户bean和角色bean查询用户的所有角色信息
	@Test
	public void testSelectRolesByUserAndRole(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//调用 selectRolesByUserIdAndRoleEnabled 方法查询用户的角色
			SysUser user = new SysUser();
			user.setId(1L);
			SysRole role = new SysRole();
			role.setEnabled(Enabled.enabled);
			List<SysRole> userList = userMapper.selectRolesByUserAndRole(user, role);
			//结果不为空
			Assert.assertNotNull(userList);
			//角色数量大于 0 个
			Assert.assertTrue(userList.size() > 0);
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testInsert(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//创建一个 user 对象
			SysUser user = new SysUser();
			user.setUserName("testqwe");
			user.setUserPassword("123456789");
			user.setUserEmail("testqwe@mybatis.tk");
			user.setUserInfo("test insertinfo");
			//正常情况下应该读入一张图片存到 byte 数组中
			user.setHeadImg(new byte[]{1,2,3});
			user.setCreateTime(new Date());
			//将新建的对象插入数据库中，特别注意，这里的返回值 result 是执行的 SQL 影响的行数
			int result = userMapper.insert(user);
			//手动提交
			sqlSession.commit();
			//只插入 1 条数据
			Assert.assertEquals(1, result);
			//id 为 null，我们没有给 id 赋值，并且没有配置回写 id 的值
			Assert.assertNull(user.getId());
		} finally {
			//为了不影响数据库中的数据导致其他测试失败，这里选择回滚
			//由于默认的 sqlSessionFactory.openSession() 是不自动提交的，
			//因此不手动执行 commit 也不会提交到数据库
			//sqlSession.rollback();
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	
	@Test
	// 使用 JDBC 方式返回主键自增的值  仅支持主键自增的数据库
	public void testInsert2(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//创建一个 user 对象
			SysUser user = new SysUser();
			user.setUserName("xiaoming");
			user.setUserPassword("123456");
			user.setUserEmail("xiaoming@mybatis.tk");
			user.setUserInfo("test insert getId");
			user.setHeadImg(new byte[]{1,2,3});
			user.setCreateTime(new Date());
			int result = userMapper.insert2(user);
			//只插入 1 条数据
			Assert.assertEquals(1, result);
			//因为 id 回写，所以 id 不为 null
			logger.debug("插入用户的返回id:"+user.getId());

			
		} finally {
			sqlSession.commit();
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}

	@Test
	// 使用 selectKey标签 返回主键的值 支持主键自增和通过序列生成(oracle)
	public void testInsertByselectKey(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//创建一个 user 对象
			SysUser user = new SysUser();
			user.setUserName("xiaohong");
			user.setUserPassword("123456798");
			user.setUserEmail("xiaohong@mybatis.tk");
			user.setUserInfo("testInsertByselectKey");
			user.setHeadImg(new byte[]{1,2,3});
			user.setCreateTime(new Date());
			int result = userMapper.insert3(user);
			//只插入 1 条数据
			Assert.assertEquals(1, result);
			//因为 id 回写，所以 id 不为 null
			logger.debug("插入用户的返回id:"+user.getId());


		} finally {
			sqlSession.commit();
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}

	//INSERT 动态插入列中使用 if
	@Test
	public void testInsert2Selective(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//创建一个 user 对象
			SysUser user = new SysUser();
			user.setUserName("test-selective");
			user.setUserPassword("123456");
			user.setUserInfo("test info");
			user.setCreateTime(new Date());
			//插入数据库
			userMapper.insert2(user);
			//获取插入的这条数据
			user = userMapper.selectById(user.getId());
			Assert.assertEquals("test@mybatis.tk", user.getUserEmail());
			
		} finally {
			sqlSession.rollback();
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testUpdateById(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//从数据库查询 1 个 user 对象
			SysUser user = userMapper.selectById(1017L);
			//修改用户名
			user.setUserName("update_test");
			//修改邮箱
			user.setUserEmail("update_test@mybatis.tk");
			//更新数据，特别注意，这里的返回值 result 是执行的 SQL 影响的行数
			int result = userMapper.updateById(user);
			//只更新 1 条数据
			Assert.assertEquals(1, result);
			//根据当前 id 查询修改后的数据
			user = userMapper.selectById(1017L);
			//查看修改后的信息
			logger.debug(JSONObject.toJSONString(user));

		} finally {
			//为了不影响数据库中的数据导致其他测试失败，这里选择回滚
			//由于默认的 sqlSessionFactory.openSession() 是不自动提交的，
			//因此不手动执行 commit 也不会提交到数据库
			sqlSession.commit();
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testDeleteById(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//从数据库查询 1 个 user 对象，根据 id = 1 查询
			SysUser user1 = userMapper.selectById(1L);
			//现在还能查询出 user 对象
			Assert.assertNotNull(user1);
			//调用方法删除
			Assert.assertEquals(1, userMapper.deleteById(1L));
			//再次查询，这时应该没有值，为 null
			Assert.assertNull(userMapper.selectById(1L));
			
			//使用 SysUser 参数再做一遍测试，根据 id = 1001  查询
			SysUser user2 = userMapper.selectById(1001L);
			//现在还能查询出 user 对象
			Assert.assertNotNull(user2);
			//调用方法删除，注意这里使用参数为 user2
			Assert.assertEquals(1, userMapper.deleteById(user2));
			//再次查询，这时应该没有值，为 null
			Assert.assertNull(userMapper.selectById(1001L));
			//使用 SysUser 参数再做一遍测试
		} finally {
			//为了不影响数据库中的数据导致其他测试失败，这里选择回滚
			//由于默认的 sqlSessionFactory.openSession() 是不自动提交的，
			//因此不手动执行 commit 也不会提交到数据库
			sqlSession.rollback();
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	//根据动态条件查询用户信息
	@Test
	public void testSelectByUser(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//只查询用户名时
			SysUser query = new SysUser();
			query.setUserName("ad");
			List<SysUser> userList = userMapper.selectByUser(query);
			Assert.assertTrue(userList.size() > 0);
			//只查询用户邮箱时
			query = new SysUser();
			query.setUserEmail("test@mybatis.tk");
			userList = userMapper.selectByUser(query);
			Assert.assertTrue(userList.size() > 0);
			//当同时查询用户名和邮箱时
			query = new SysUser();
			query.setUserName("ad");
			query.setUserEmail("test@mybatis.tk");
			userList = userMapper.selectByUser(query);
			//由于没有同时符合这两个条件的用户，查询结果数为 0
			Assert.assertTrue(userList.size() == 0);
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}

	//choose用法:通过用户id或者用户名查找用户信息
	@Test
	public void testSelectByIdOrUserName(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//只查询用户名时
			SysUser query = new SysUser();
			query.setId(1L);
			query.setUserName("admin");
			SysUser user = userMapper.selectByIdOrUserName(query);
			Assert.assertNotNull(user);
			//当没有 id 时
			query.setId(null);
			user = userMapper.selectByIdOrUserName(query);
			Assert.assertNotNull(user);
			//当 id 和 name 都为空时
			query.setUserName(null);
			user = userMapper.selectByIdOrUserName(query);
			Assert.assertNull(user);
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	//根据动态条件更新用户信息
	@Test
	public void testUpdateByIdSelective(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//从数据库查询 1 个 user 对象
			SysUser user = new SysUser();
			//更新 id = 1 的用户
			user.setId(1L);
			//修改邮箱
			user.setUserEmail("test@mybatis.tk");
			//将新建的对象插入数据库中，特别注意，这里的返回值 result 是执行的 SQL 影响的行数
			int result = userMapper.updateByIdSelective(user);
			//只更新 1 条数据
			Assert.assertEquals(1, result);
			//根据当前 id 查询修改后的数据
			user = userMapper.selectById(1L);
			//修改后的名字保持不变，但是邮箱变成了新的
			Assert.assertEquals("admin", user.getUserName());
			Assert.assertEquals("test@mybatis.tk", user.getUserEmail());
		} finally {
			//为了不影响数据库中的数据导致其他测试失败，这里选择回滚
			sqlSession.rollback();
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}

	//根据用户 id 集合查询
	@Test
	public void testSelectByIdList(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<Long> idList = new ArrayList<Long>();
			idList.add(1L);
			idList.add(1001L);
			//业务逻辑中必须校验 idList.size() > 0
			List<SysUser> userList = userMapper.selectByIdList(idList);
			Assert.assertEquals(2, userList.size());
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}

	//foreach 实现批量插入 并返回自增的主键
	/*这个功能首先要求数据库主键值为自增类型， 同时还要求该数据库提供 JDBC
	动可以支持返回批量插入的主键值 JDBC 提供了接口 但并不是所有数据库都完美实现了该
	接口），因此到目前为止 可以完美支持该功能的仅有 MySQL 数据库*/
	@Test
	public void testInsertList(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//创建一个 user 对象
			List<SysUser> userList = new ArrayList<SysUser>();
			for(int i = 0; i < 2; i++){
				SysUser user = new SysUser();
				user.setUserName("test" + i);
				user.setUserPassword("123456");
				user.setUserEmail("test@mybatis.tk");
				userList.add(user);
			}
			//将新建的对象批量插入数据库中，特别注意，这里的返回值 result 是执行的 SQL 影响的行数
			int result = userMapper.insertList(userList);
			Assert.assertEquals(2, result);
			for(SysUser user : userList){
				System.out.println(user.getId());
			}
		} finally {
			//为了不影响数据库中的数据导致其他测试失败，这里选择回滚
			sqlSession.rollback();
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	// foreach 实现动态 UPDATE
	@Test
	public void testUpdateByMap(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//从数据库查询 1 个 user 对象
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", 1L);
			map.put("user_email", "test@mybatis.tk");
			map.put("user_password", "12345678");
			//更新数据
			userMapper.updateByMap(map);
			//根据当前 id 查询修改后的数据
			SysUser user = userMapper.selectById(1L);
			Assert.assertEquals("test@mybatis.tk", user.getUserEmail());
		} finally {
			//为了不影响数据库中的数据导致其他测试失败，这里选择回滚
			sqlSession.rollback();
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserAndRoleById(){
		//获取 sqlSession
		SqlSession sqlSession = getSqlSession();
		try {
			//获取 UserMapper 接口
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//特别注意，在我们测试数据中，id = 1L 的用户有两个角色
			//由于后面覆盖前面的，因此只能得到最后一个角色
			//我们这里使用只有一个角色的用户（id = 1001L）
			//可以用 selectUserAndRoleById2 替换进行测试
			SysUser user = userMapper.selectUserAndRoleById(1001L);
			//user 不为空
			Assert.assertNotNull(user);
			//user.role 也不为空
			Assert.assertNotNull(user.getRole());
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserAndRoleByIdSelect(){
		//获取 sqlSession
		SqlSession sqlSession = getSqlSession();
		try {
			//获取 UserMapper 接口
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			//特别注意，在我们测试数据中，id = 1L 的用户有两个角色
			//由于后面覆盖前面的，因此只能得到最后一个角色
			//我们这里使用只有一个角色的用户（id = 1001L）
			SysUser user = userMapper.selectUserAndRoleByIdSelect(1001L);
			//user 不为空
			Assert.assertNotNull(user);
			//user.role 也不为空
			System.out.println("调用 user.equals(null)");
			user.equals(null);
			System.out.println("调用 user.getRole()");
			Assert.assertNotNull(user.getRole());
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAllUserAndRoles(){
		//获取 sqlSession
		SqlSession sqlSession = getSqlSession();
		try {
			//获取 UserMapper 接口
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = userMapper.selectAllUserAndRoles();
			System.out.println("用户数：" + userList.size());
			for(SysUser user : userList){
				System.out.println("用户名：" + user.getUserName());
				for(SysRole role: user.getRoleList()){
					System.out.println("角色名：" + role.getRoleName());
					for(SysPrivilege privilege : role.getPrivilegeList()){
						System.out.println("权限名：" + privilege.getPrivilegeName());
					}
				}
			}
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAllUserAndRolesSelect(){
		//获取 sqlSession
		SqlSession sqlSession = getSqlSession();
		try {
			//获取 UserMapper 接口
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectAllUserAndRolesSelect(1L);
			System.out.println("用户名：" + user.getUserName());
			for(SysRole role: user.getRoleList()){
				System.out.println("角色名：" + role.getRoleName());
				for(SysPrivilege privilege : role.getPrivilegeList()){
					System.out.println("权限名：" + privilege.getPrivilegeName());
				}
			}
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserById(){
		//获取 sqlSession
		SqlSession sqlSession = getSqlSession();
		try {
			//获取 UserMapper 接口
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setId(1L);
			userMapper.selectUserById(user);
			Assert.assertNotNull(user.getUserName());
			System.out.println("用户名：" + user.getUserName());
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserPage(){
		//获取 sqlSession
		SqlSession sqlSession = getSqlSession();
		try {
			//获取 UserMapper 接口
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userName", "ad");
			params.put("offset", 0);
			params.put("limit", 10);
			List<SysUser> userList = userMapper.selectUserPage(params);
			Long total = (Long) params.get("total");
			System.out.println("总数:" + total);
			for(SysUser user : userList){
				System.out.println("用户名：" + user.getUserName());
			}
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}
	
	@Test
	public void testInsertAndDelete(){
		//获取 sqlSession
		SqlSession sqlSession = getSqlSession();
		try {
			//获取 UserMapper 接口
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			
			SysUser user = new SysUser();
			user.setUserName("test1");
			user.setUserPassword("123456");
			user.setUserEmail("test@mybatis.tk");
			user.setUserInfo("test info");
			//正常情况下应该读入一张图片存到 byte 数组中
			user.setHeadImg(new byte[]{1,2,3});
			//插入数据
			userMapper.insertUserAndRoles(user, "1,2");
			Assert.assertNotNull(user.getId());
			Assert.assertNotNull(user.getCreateTime());
			//可以执行下面的 commit 后查看数据库中的数据
			//sqlSession.commit();
			//测试删除刚刚插入的数据
			userMapper.deleteUserById(user.getId());
		} finally {
			//不要忘记关闭 sqlSession
			sqlSession.close();
		}
	}

}
