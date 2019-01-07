package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTestCase {
	@Autowired
	private IUserservice iUserservice;

	/**
	 * #{id},#{username},#{password},#{salt}.#{gender},
#{phone},#{email},#{avatar},#{isDelete},#{createdUser},
#{createdTime},#{modifiedUser},#{modifiedTime}
	 */
	
	@Test
	public void iseruser() {
		try {
			
			User user = new User();
			String nameString = "孙子";
			user.setUsername(nameString);
			user.setPassword("123456");
			user.setGender(0);
			user.setPhone("12345678901");
			user.setEmail(nameString+"@tedu.cn");
			user.setAvatar("dasdsadas");
			user.setIsDelete(0);
			
			
			
			 iUserservice.reg(user);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		

	}
	
	
	
	@Test
	public void login() {
		try {
			String username= "java";
			String password = "123456";
			iUserservice.login(username, password);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}
	
	
	@Test
	public void updaInfo() {
		User user = new User();
		user.setId(9);
		user.setPhone("15974858888");
		iUserservice.updaInfo(user);
		
	}
	
	@Test
	public void updaAvatar() {
		Integer id= 9;
		String avatar = "kjhj.png";
		iUserservice.updateAvatar(id, avatar);
		System.out.println("结束");
	}
	
}
