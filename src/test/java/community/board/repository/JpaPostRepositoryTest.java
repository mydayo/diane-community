package community.board.repository;

import static org.hamcrest.CoreMatchers.equalTo;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import community.board.entity.Post;
import community.user.entity.User;
import community.user.repository.JpaUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("production")
@Rollback(value=false)
public class JpaPostRepositoryTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	JpaPostRepository jpaPostRepository;
	
	@Autowired
	JpaUserRepository jpaUserRepository;
	
	User user;
	
	@Before
	public void usetSetUp() {
		user = jpaUserRepository.findByEmail("dayoung@naver.com");
	}
	
	
	@Ignore
	@Test
	public void addPostTest() {
		Post post = new Post();
		post.setContents("contents11111");
		post.setTitle("title");
		post.setUser(user);
		Post savedPost = jpaPostRepository.save(post);
		
		assertThat(post.getTitle(), is(equalTo(savedPost.getTitle())));
	}
	
	@Ignore
	@Test
	public void addPostWithoutUserTest() {		
		Post post = new Post();
		post.setContents("contents");
		post.setTitle("title");
		Post savedPost = jpaPostRepository.save(post);
		
		assertThat(savedPost.getTitle(), is(savedPost.getTitle()));
	}
	
	
	@Ignore
	@Test
	public void getPost() {
		assertThat(jpaPostRepository.findAll().get(0).getTitle(), is(equalTo("titddddddle")));
	}
	
}
