package cn.tedu.store.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class updatePassword {

	
	@Autowired
	private IUserservice iUserservice;
	
	
	
	@Test
	public void Password() {
		try {
			iUserservice.updatePassword(22, "123456", "654321");
			System.err.println("OK");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}
}
