package community.user.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import community.board.entity.Comment;
import community.board.entity.Post;
import community.board.setup.BoardSetUp;
import community.user.entity.User;
import community.user.repository.JpaUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("production")
@Rollback(value=false)
public class UserServiceTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Mock
	JpaUserRepository userRepo;
	
	@InjectMocks
	UserServiceImpl userService;
	
	User userSet;
	Post postSet;
	List<Comment> commentSet;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void EntitySetUp() {
		userSet = BoardSetUp.userSetUp();
		postSet = BoardSetUp.postSetUp();
		commentSet = BoardSetUp.commentSetUp();
	}
	
	@Ignore
	@Test
	public void emailDuplicationCheckTrueTest() throws Exception {
		String email = "mydayo@naver.com";

		assertTrue(userService.emailDuplicationCheck(email));
	}
	
	@Ignore
	@Test
	public void emailDuplicationCheckfalseTest() throws Exception {
		String email = "notexist@naver.com";
		
		assertFalse(userService.emailDuplicationCheck(email));
	}
	
	@Ignore
	@Test
	public void emailNullCheck() throws Exception {
		User user = null;
		when(userRepo.findByEmail("test@naver.com")).thenReturn(user);
		
		assertTrue(userService.emailDuplicationCheck("test@naver.com") == false);
	}
	
	@Ignore
	@Test
	public void addUserTest() throws Exception {
		when(userRepo.save(userSet)).thenReturn(userSet);
		
		assertTrue(userService.addUser(userSet).getEmail() == userSet.getEmail());
	}
	
	@Ignore
	@Test
	public void addUserWhenEmailIsNotValidTest() throws Exception {
		userSet.setEmail("dfdksfjlksjf@djflkdsjlfk");
		
		assertFalse(userService.addUser(userSet).getEmail() == userSet.getEmail());
	}
	
	@Ignore
	@Test
	public void addUserWhenEmailIsNotValidTest2() throws Exception {
		userSet.setEmail("dfdksfjl");
		
		assertFalse(userService.addUser(userSet).getEmail() == userSet.getEmail());
	}	
	
	@Ignore
	@Test
	public void addUserWhenEmailIsValidTest() throws Exception {
		userSet.setEmail("dfdksfjl@naver.com");
		
		assertTrue(userService.addUser(userSet).getEmail() == userSet.getEmail());
	}
	
	@Test
	public void loginWhenIdAndPasswordRightTest() throws Exception {
		String email = "123@123.com";
		
		User user = new User();
		user.setEmail(email);
		user.setPassword("123");
		
		when(userRepo.findByEmail(email)).thenReturn(user);
		userSet.setEmail(email);
		userSet.setPassword("123");
		
		assertThat(userService.loginUser(userSet).getEmail(), is(equalTo(email)));
	}
	
	@Test
	public void loginWhenPasswordIsWrongTest() throws Exception {
		User user = new User();
		user.setEmail("123@123.com");
		user.setPassword("123");
		
		when(userRepo.findByEmail("123@123.com")).thenReturn(user);
		userSet.setEmail("123@123.com");
		userSet.setPassword("wrongpassword");
		
		assertThat(userService.loginUser(userSet), is(nullValue()));
	}
	
	@Test
	public void loginWhenIdIsWrongTest() throws Exception {
		User user = new User();
		user.setEmail("12344444@123.com");
		user.setPassword("123");
		
		when(userRepo.findByEmail("12344444@123.com")).thenReturn(user);
		userSet.setEmail("12344444@123.com");
		userSet.setPassword("wrongpassword");
		
		assertThat(userService.loginUser(userSet), is(nullValue()));
	}
	
	@Test
	public void getAllUserByAdminUserTest() throws Exception {
		when(userRepo.findByEmail("3fjdkfjls@naver.com").isAdmin()).thenReturn(true);

		List<User> result = userService.getAllUser("3fjdkfjls@naver.com");
		assertThat(result, is(notNullValue()));
	}
	
	@Test
	public void getAllUserByNotAdminUserTest() throws Exception {
		when(userRepo.findByEmail("realTest@naver.com").isAdmin()).thenReturn(false);

		List<User> result = userService.getAllUser("realTest@naver.com");
		assertThat(result, is(nullValue()));
	}
}
