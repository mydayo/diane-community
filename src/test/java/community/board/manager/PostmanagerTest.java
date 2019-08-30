package community.board.manager;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import community.board.repository.JpaCommentRepository;
import community.board.repository.JpaPostRepository;
import community.board.setup.BoardSetUp;
import community.exception.BadRequestParameterException;
import community.user.entity.User;
import community.vo.Board;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("production")
@Rollback(value=true)
public class PostmanagerTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Mock
	JpaPostRepository postRepo;
	
	@Mock
	JpaCommentRepository commentRepo;
	
	@InjectMocks
	PostManagerImpl postManager;
	
	User userSet;
	Post postSet;
	List<Comment> commentSet;
	Board board;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Before
	public void entitySetUp() {
		userSet = BoardSetUp.userSetUp();
		postSet = BoardSetUp.postSetUp();
		commentSet = BoardSetUp.commentSetUp();
		
		board = new Board();
		board.setType("post");
		board.setId(postSet.getId());
		board.setTitle(postSet.getTitle());
		board.setContents(postSet.getContents());
	}

	@Ignore
	@Test
	public void addPostTest() throws Exception {
		when(postRepo.save(postSet)).thenReturn(postSet);

		assertThat(postManager.addPost(postSet).getContents(), is(postSet.getContents()));
		verify(postRepo, atLeastOnce()).save(postSet);
	}
	
	@Ignore
	@Test
	public void addPostWhenTitleIsNullTest() throws Exception {
		thrown.expect(BadRequestParameterException.class);
		postSet.setTitle(null);
		
		when(postRepo.save(postSet)).thenReturn(postSet);
		
		postManager.addPost(postSet);
		
		verify(postRepo, never()).save(postSet);
	}

	@Ignore
	@Test
	public void addPostWhenLengthOfTitleIsOver30Test() throws Exception {
		thrown.expect(BadRequestParameterException.class);
		postSet.setTitle("1234567890123456789012345678901234567890");
		
		when(postRepo.save(postSet)).thenReturn(postSet);
		
		postManager.addPost(postSet);
		
		verify(postRepo, never()).save(postSet);
	}
	
	@Ignore
	@Test
	public void getPostListTest() {
		List<Post> postList = new ArrayList<>();
		postList.add(postSet);
		
		when(postRepo.findAll()).thenReturn(postList);
		assertThat(postManager.getPostList().get(0).getContents(), is(postSet.getContents()));
		verify(postRepo, atLeastOnce()).findAll();
	}
	
	@Ignore
	@Test
	public void getPostTest() {
		when(postRepo.findById(postSet.getId())).thenReturn(Optional.of(postSet));
		when(commentRepo.findCommentsByPostId(postSet.getId())).thenReturn(commentSet);
		
		Post post = (Post) postManager.getPost(postSet.getId()).get("post");
		
		assertTrue(post.getContents() == postSet.getContents());
	}
	
	@Ignore
	@Test
	public void getPostWhenPostIsNotPresentTest() {
		thrown.expect(NullPointerException.class);
		when(postRepo.findById(postSet.getId())).thenReturn(null);
		
		postManager.getPost(postSet.getId()).get("post");
		
		verify(commentRepo, never()).findCommentsByPostId(postSet.getId());
	}
	
	@Ignore
	@Test
	public void getPostWhenPostIsPresentAndCommentIsNullTest() {
		when(postRepo.findById(postSet.getId())).thenReturn(Optional.of(postSet));
		when(commentRepo.findCommentsByPostId(postSet.getId())).thenReturn(null);
		
		postManager.getPost(postSet.getId());
		assertNull(postManager.getPost(postSet.getId()).get("comment"));
	}
	
	@Ignore
	@Test
	public void getPostWhenPostIsPresentAndCommentIsPresent() {
		when(postRepo.findById(postSet.getId())).thenReturn(Optional.of(postSet));
		when(commentRepo.findCommentsByPostId(postSet.getId())).thenReturn(commentSet);
		
		postManager.getPost(postSet.getId());
		assertNotNull(postManager.getPost(postSet.getId()).get("comment"));
	}
	
	@Test
	public void updatePostWhenTitleIsNull() throws Exception {
		thrown.expect(BadRequestParameterException.class);
		
		board.setTitle(null);
		postManager.updateBoard(board);
	}
	
	@Test
	public void updatePostWhenTitleLengthIsOverThirty() throws Exception {
		thrown.expect(BadRequestParameterException.class);
		
		board.setTitle("12345678901234567890123456789012345");
		postManager.updateBoard(board);
	}
	
	@Test
	public void updateBoard() throws Exception {
		when(postRepo.findById(postSet.getId())).thenReturn(Optional.of(postSet));
		when(postRepo.save(postSet)).thenReturn(postSet);
		
		assertTrue(postManager.updateBoard(board).getTitle() == postSet.getTitle());
	}
}
