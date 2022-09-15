package co.id.ahm.sdlog;

import co.id.ahm.sdlog.config.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SdlogApplicationTests {

	@Test
	void contextLoads() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Assertions.assertNotNull(sessionFactory);
	}

}
