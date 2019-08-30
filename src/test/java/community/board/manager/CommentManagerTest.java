package community.board.manager;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
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
import community.board.setup.BoardSetUp;
import community.user.entity.User;
import community.vo.Board;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("production")
@Rollback(value=false)
public class CommentManagerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Mock
	JpaCommentRepository commentRepo;
	
	@InjectMocks
	CommentManagerImpl commentManager;

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
		board.setType("comment");
		board.setId(commentSet.get(0).getId());
		board.setContents(commentSet.get(0).getContents());
	}
	
	@Test
	public void updateCommentTest() throws Exception {
		when(commentRepo.findById(commentSet.get(0).getId())).thenReturn(Optional.of(commentSet.get(0)));
		when(commentRepo.save((Comment) commentSet.get(0))).thenReturn((Comment) commentSet.get(0));
		
		assertTrue(commentManager.updateBoard(board).getContents() == commentSet.get(0).getContents());
	}

	@Test
	public void updateCommentWhenCommentIsNullTest() throws Exception {
		thrown.expect(NoSuchElementException.class);
		
		Board board = new Board();
		board.setContents(null);
		board.setId(null);
		commentManager.updateBoard(board);
	}
}
