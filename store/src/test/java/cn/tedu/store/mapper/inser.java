package cn.tedu.store.mapper;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import cn.tedu.store.entity.User;
import cn.tedu.store.service.impl.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class inser {

	@Autowired
	private UserMapper userMapper;

	/**
	 * #{id},#{username},#{password},#{salt}.#{gender},
#{phone},#{email},#{avatar},#{isDelete},#{createdUser},
#{createdTime},#{modifiedUser},#{modifiedTime}
	 */
	/**
	 * 插入用户数据
	 */
	@Test
	public void iseruser() {
		Date date = new Date();
		User user = new User();
		user.setUsername("未来");
		user.setPassword("123456");
		user.setSalt("7894561023");
		user.setGender(0);
		user.setPhone("12345678901");
		user.setEmail("dsada@tedu.cn");
		user.setAvatar("dasdsadas");
		user.setIsDelete(0);
		user.setCreatedUser("dd");
		user.setCreatedTime(date);
		user.setModifiedUser("dsds");
		user.setModifiedTime(date);
		
		
		User findByUsername = userMapper.findByUsername(user.getUsername());
		if (findByUsername == null) {
			Integer inserUser = userMapper.inserUser(user);
			System.out.println(inserUser);
		} else {
System.out.println("用户已有");
		}

	}
	
	
	/**
	 * 根据名字查询用户user
	 */
	@Test
	public void findUsername() {
		String username = "如来";
		User findByUsername = userMapper.findByUsername(username);
	for (int i = 0; i < 100; i++) {
		System.out.println(UUID.randomUUID());
		System.out.println("--------------------------------");
		
	}
		String md5Password =UUID.randomUUID().toString()+"jjhbdssadsajd"+UUID.randomUUID().toString();
		for (int i = 0; i <15; i++) {
			md5Password = DigestUtils.md5DigestAsHex(md5Password.getBytes())+i;
		}
	System.out.println(md5Password);
	
		System.out.println(findByUsername);
	}
	
	/**
	 * 查询全部用户
	 */
	@Test
	public void findAll() {
		List<User> findall = userMapper.findall();
		for (User user : findall) {
			System.out.println(user);
		}
	}
	
	
	/**
	 * 根据id查询用户password和salt
	 */
	@Test
	public void selectId() {
		Integer id= 2;
		User selectFromId = userMapper.selectFromId(id);
		selectFromId.setId(id);
		System.out.println(selectFromId);
	}
	
	
	

	
	/**
	 * 密码更新
	 */
	@Test
	public void updataPassword() {
		Integer id= 2;
		User selectFromId = userMapper.selectFromId(id);
		System.out.println(selectFromId);
		String salt = selectFromId.getSalt();
		String password = "123456";
		String md5password = new UserServiceImpl().getMd5password(password, salt);
		String modifiedUser = "Admin";
		Date modifiedTime = new Date();
		Integer updatePassword = userMapper.updatePassword(md5password, modifiedUser, modifiedTime, id);
		System.out.println(updatePassword);
	}
	
	
	@Test
	public void updaInfo() {
		
		User user = new User();
		user.setId(9);
		user.setGender(1);
		user.setPhone("15974859969");
		user.setEmail("test@tedu.cn");
		user.setModifiedUser("admin");
		Date modifiedTime = new Date();
		user.setModifiedTime(modifiedTime);
		Integer updataInfo = userMapper.updataInfo(user);
		System.out.println(updataInfo);
	}
	
	/**
	 * 头像
	 * 
	 */
	@Test
	public void updaAvatar() {
		Integer id=9;
		String avatar = "dsds/sdsds.png";
		String modifiedUser = userMapper.selectFromId(id).getUsername();
		Date modifiedTime = new Date();
		Integer updateAvatar = userMapper.updateAvatar(id, avatar, modifiedUser, modifiedTime);
		System.out.println(updateAvatar);
		
	}
}
