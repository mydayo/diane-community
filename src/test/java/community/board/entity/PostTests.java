package community.board.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import community.board.repository.JpaPostRepository;
import community.user.entity.User;
import community.user.repository.JpaUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("production")
//@Transactional
public class PostTests {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	JpaPostRepository postRepo;
	
	@Autowired
	JpaUserRepository userRepo;
	
	User testUser;
	Post post;
	
	@Before
	public void userSetUp() {
		User user = new User();
		user.setEmail("dianekim@mz.co.kr");
		user.setPassword("1234abcd");
		user.setAdmin(true);
		testUser = userRepo.save(user);
	}
	
	@Ignore
	@Test
	public void postSaveTest() {
		post = new Post();
		post.setTitle("hello");
		post.setContents("this is contents of my post");
		post.setUser(testUser);
		
		assertThat(postRepo.save(post).getTitle(), is("hello"));
	}
	
	@Test 
	public void postUpdateTest() {
		User user = userRepo.findByEmail("mydayo@naver.com");
		user.setPassword("updatedPassword");
		
		assertThat(userRepo.save(user).getPassword(), is("updatedPassword"));
	}
	
	@Ignore
	@Test
	public void postCreationWithoutUserExceptionTest() {
		thrown.expect(DataIntegrityViolationException.class);

		post = new Post();
		post.setTitle("hello");
		post.setContents("this is contents of my post");
		postRepo.save(post);
	}
	
	@Ignore
	@Test
	public void findPostsByUserIdCheck() {
		post = new Post();
		post.setTitle("hello");
		post.setContents("this is contents of my post");
		post.setUser(testUser);
		postRepo.save(post);	
//		List<Post> postList = postRepo.findPostsByUserId(testUser.getId());
		
//		assertThat(postList.get(postList.size()-1).getUser().getEmail(), is(equalTo("dianekim@mz.co.kr")));
	}
}

