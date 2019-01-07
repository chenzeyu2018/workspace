package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.tedu.store.entity.User;


/**
 * @Mapper  可以使用启动类来解决 在TeduStoreApplication中加@MapperScan("cn.tedu.store.mapper")解决后者可以一次解决所有接口文件
 * 如果前者就是要在所有的接口上使用
 * @author Tiberius
 *
 */
public interface  UserMapper {
	
	/**
	 * 插入用户数据，但是插入之前请使用findByUsername进行用户是否重复判断
	 * @param user    用户数据
	 * @return   收影响的行数
	 */
	Integer inserUser(User user);
	
	
	/**
	 * 根据用户名查询用户是否存在
	 * @param username   用户名
	 * @return   匹配的用户数据，如果没有匹配到，则返回null
	 */
	User findByUsername(String username);
	
	
	/**
	 * 查询全部用户数据
	 * @return    有则返回List<User>集合，没有则是控
	 */
	List<User> findall();
	
	
	
	/**
	 * 根据id查询pasword和salt
	 * @return   返回
	 */
	User selectFromId(Integer id);
	
	
	/**
	 * 对用户密码进行修改并且最后修改用户名和时间
	 * @param password    新的密码
	 * @param username    操作用户
	 * @param modifiedTime    操作时间
	 * @param id          被修改用户id
	 * @return           影响行数
	 */
	Integer updatePassword(@Param("password")String password,@Param("modifiedUser")String modifiedUser,
			@Param("modifiedTime")Date modifiedTime,@Param("id")Integer id);
	
	

	/**
	 * 修改用户资料不包含用户名、密码、头像
	 * @param user
	 * @return
	 */
	Integer updataInfo(User user);
	
	/**
	 * 用户头像上传
	 * @param id    用户id
	 * @param avatar  用户头像地址
	 * @param modifiedUser   操作用户名
	 * @param modifiedTime   操作时间
	 * @return
	 */
	Integer updateAvatar(@Param("id")Integer id,@Param("avatar")String avatar,
			@Param("modifiedUser")String modifiedUser,@Param("modifiedTime")Date modifiedTime);
}
