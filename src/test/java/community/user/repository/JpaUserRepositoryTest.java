package community.user.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import community.user.entity.User;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("production")
@Rollback(value=false)
public class JpaUserRepositoryTest {

	@Autowired
	JpaUserRepository jpaUserRepository;
	
	@Test
	public void checkAdminUserByIdTest() {
		String email = "22ab67a7-f1f6-4bde-a12b-d2273d5bbbca";
		
		assertTrue(jpaUserRepository.findByEmail(email).isAdmin());
	}
	
	@Test
	public void checkNotAdminUserByIdTest() {
		String email = "22ab67a7-f1f6-4bde-a12b-d2273d5bbbca";
		
		assertFalse(jpaUserRepository.findByEmail(email).isAdmin());
	}
	
	@Test
	public void findAllUserTest() {
		List<User> list = jpaUserRepository.findAll();
		int count = (int) jpaUserRepository.count();
		
		assertThat(list.size(), is(equalTo(count)));
	}
}
