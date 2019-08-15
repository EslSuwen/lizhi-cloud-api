package com.example;

import com.example.common.utils.IdGen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {
	@Resource
	IdGen idGen;

	@Value("${mycloud.env}")
	private String env;

	@Test
	public void contextLoads() {
		System.out.println(idGen.nextId());
		System.out.println(env);
	}
}

