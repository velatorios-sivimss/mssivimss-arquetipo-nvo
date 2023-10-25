package com.imss.sivimss.ctrolpermisos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.imss.sivimss.arquetipo.ArquetipoApplication;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PermisosApplicationTests {

	@Test
	void contextLoads() {
		String result="test";
		ArquetipoApplication.main(new String[]{});
		assertNotNull(result);
	}

}
