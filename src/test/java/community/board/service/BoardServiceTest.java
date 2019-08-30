package community.board.service;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
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
import community.board.manager.BoardCommonManager;
import community.board.manager.CommentManager;
import community.board.manager.PostManager;
import community.board.repository.JpaCommentRepository;
import community.board.repository.JpaPostRepository;
import community.board.setup.BoardSetUp;
import community.user.entity.User;
import community.user.repository.JpaUserRepository;
import community.vo.Board;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("production")
@Rollback(value=false)
public class BoardServiceTest {
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Mock
	PostManager postManager;
	
	@Mock
	CommentManager commentManager;
	
	@Mock
	List<BoardCommonManager> listBoardManager;
	
	@InjectMocks
	BoardServiceImpl boardService;
	
	User userSet;
	Post postSet;
	List<Comment> commentSet;
//	Board board;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void entitySetUp() {
		userSet = BoardSetUp.userSetUp();
		postSet = BoardSetUp.postSetUp();
		commentSet = BoardSetUp.commentSetUp();
	}
	
	
}
