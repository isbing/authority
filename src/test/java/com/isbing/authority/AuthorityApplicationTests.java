package com.isbing.authority;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorityApplicationTests {

	@Resource
	private UsersService usersService;

	@Test
	public void contextLoads() {
		User user = usersService.getById("11");
		System.out.println(user);
	}

}
