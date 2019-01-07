package cn.tedu.store.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.tedu.store.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Alluser {

	@Autowired
	private UserMapper userMapper;
	
	
	
	@Test
	public void AllUser() {
		List<User> findall = userMapper.findall();
		System.out.println(findall);
		for (User user : findall) {
			System.out.println(user);
		}
	
	}
	
}
