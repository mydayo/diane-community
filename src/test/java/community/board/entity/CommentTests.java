package community.board.entity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

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

import community.board.repository.JpaCommentRepository;
import community.board.repository.JpaPostRepository;
import community.user.entity.User;
import community.user.repository.JpaUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("production")
//@Transactional
public class CommentTests {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Autowired
	JpaCommentRepository commentRepo;
	
	@Autowired
	JpaPostRepository postRepo;
	
	@Autowired
	JpaUserRepository userRepo;
	
	User testUser;
	Post testPost;
	Comment comment;
	
	@Before
	public void userSetUp() {
		User user = new User();
		user.setEmail("dayoung@naver.com");
		user.setPassword("1234abcd");
		user.setAdmin(true);
		testUser = userRepo.save(user);
	}
	
	@Before
	public void postSetUp() {
		Post post = new Post();
		post.setTitle("hello");
		post.setContents("this is contents of my post");
		post.setUser(testUser);
		testPost = postRepo.save(post);
	}
	
	@Ignore
	@Test
	public void createCommentTest() {
		comment = new Comment();
		comment.setContents("this is my comment");
		comment.setPost(testPost);
		comment.setUser(testUser);
		commentRepo.save(comment);
	}
	
	@Ignore
	@Test
	public void commentCreationWithoutUserExceptionTest() {
		thrown.expect(DataIntegrityViolationException.class);		

		comment = new Comment();
		comment.setContents("this is my comment");
		comment.setPost(testPost);
		commentRepo.save(comment);
	}
	
	@Ignore
	@Test
	public void findCommentsByPostIdCheck() {
		comment = new Comment();
		comment.setContents("this is my comment");
		comment.setPost(testPost);
		comment.setUser(testUser);
		commentRepo.save(comment);
		
		List<Comment> commentList = commentRepo.findCommentsByPostId(testPost.getId());
		
		assertThat(commentList.get(commentList.size()-1).getPost().getTitle(), is(equalTo("hello")));
	}
	
}
