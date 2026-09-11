package com.MyProject.mediationplatform;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"test", "teamMember", "clinks"})
class IntegrationPlatformApplicationTests {

	@Test
	void contextLoads() {
	}

}
