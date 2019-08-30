package community.user.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import community.user.repository.JpaUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("production")
@Rollback(value=false)
//@Transactional
public class UserTests {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	JpaUserRepository userRepo;

	private User saveUser;
	
	@Before
	public void userSetUp() {
		User user = new User();
		user.setEmail("dianetestkim@naver.com");
		user.setPassword("1234abcd");
		user.setAdmin(true);
		saveUser = userRepo.save(user);
	}
	
	@Ignore
	@Test
	public void userSaveTest() {
		assertThat(saveUser.getEmail(), is("dianetestkim@naver.com"));
	}
	
	@Ignore
	@Test
	public void userCreationWithoutEmailExceptionTest() {
		thrown.expect(DataIntegrityViolationException.class);

		User user = new User(); 
		user.setPassword("1234abcd");
		saveUser = userRepo.save(user);
	}
	
	@Ignore
	@Test
	public void findByIdEmailTest() {
		User user = userRepo.findByEmail("3fjdkfjls@naver.com");
		assertTrue(user.getEmail().equals("3fjdkfjls@naver.com"));
	}
	
	@Ignore
	@Test
	public void findAdminByIdTest() {
		boolean result = userRepo.findByEmail("3fjdkfjls@naver.com").isAdmin();
		assertTrue(result == true);
	}
}
