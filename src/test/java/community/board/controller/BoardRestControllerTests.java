package community.board.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import community.board.entity.Comment;
import community.board.repository.JpaPostRepository;
import community.board.service.BoardServiceImpl;
import community.user.entity.User;
import community.user.repository.JpaUserRepository;
import community.user.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("production")
@Rollback(value=false)
public class BoardRestControllerTests {

	@Autowired
	private WebApplicationContext was;
	
	@InjectMocks
	BoardRestController controller;
	
	@Mock
	BoardServiceImpl boardService;
	
	@Mock
	UserServiceImpl userService;
	
	@Autowired
	JpaUserRepository userRepo;
	
	@Mock
	JpaPostRepository postRepo;
	
	private MockMvc mockMvc;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		// mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(was).build();
	}
	
	@Test
	public void addPostTest() throws Exception {
		String url = "/community/board/post";
		
		this.mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)            
				.content("{\"contents\": \"hello\","
						+ "\"title\": \"this is title\","
						+ "\"user\": { \"id\": \"2d88babf-306f-41a4-a364-01b1e08dee2a\"}}"))
				.andExpect(status().isOk())
                .andDo(print());
	}
	
	@Ignore
	@Test
	public void addCommnetTest() throws JsonProcessingException, Exception {
		Comment comment = new Comment();
		User user = new User();
		user.setEmail("dianekim@mz.co.kr");
		comment.setContents("commentContents");
//		comment.setPost(boardService.getPost("f63053a9-e9fc-457a-aef0-4e5a7cecfcdd").get("post"));
		comment.setUser(userService.getUser(user.getEmail()));
		
		String url = "/community/board/comment";
		this.mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)            
				.content(mapper.writeValueAsString(comment)))
				.andExpect(status().isOk())
                .andDo(print());
	}
	
	@Ignore
	@Test
	public void getPostTest() throws Exception {
		mockMvc.perform(get("/community/board/post/" + "f63053a9-e9fc-457a-aef0-4e5a7cecfcdd")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}
	
	@Ignore
	@Test
	public void getAllPostTest() throws Exception {
		mockMvc.perform(get("/community/board/post/all")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}
	
	@Ignore
	@Test
	public void deletePost() throws Exception {
		mockMvc.perform(delete("/community/board/post/" + "8dc832bf-4918-4f16-a96e-5e07c4e61e7c")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}
	
	@Ignore
	@Test
	public void updateComment() throws Exception {
		mockMvc.perform(put("/community/board/comment")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"contents\":\"123\"}"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}
}
