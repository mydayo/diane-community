package community.user.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import community.user.entity.User;
import community.user.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("production")
@Rollback(value=false)
public class UserRestControllerTests {
	
	@Autowired
	private WebApplicationContext was;
	
	@InjectMocks
	UserRestController controller;
	
	@Mock
	UserServiceImpl userService;
	
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		// mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(was).build();
	}
	
	@Ignore
	@Test
	public void testMockMvc() throws Exception {
	    mockMvc.perform(get("/"))
	            .andExpect(status().isOk());
	}
	@Ignore	
	@Test
	public void emailDuplicationCheckTest() throws Exception {
		  MvcResult result = mockMvc.perform(
				  post("/community/user/emailCheck/")
				  .param("email", "unregistered@naver.com")
				  ).andReturn();
		  
		  assertThat(result.getResponse().getContentAsString(), is(equalTo("false")));
	}
	@Ignore	
	@Test
	public void registerationTest() throws Exception {
		String url = "/community/user/registeration";
		User user = new User();
		user.setEmail("hello@hello.hello");
		user.setPassword("hello");
		String json = new Gson().toJson(user);
		
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	}
	
	@Ignore	
	@Test
	public void registerationWithoutPasswordTest() throws Exception {
		String url = "/community/user";
		
		MvcResult result = mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
				.content("{\"email\":\"dainddrldee@naver.com\"}"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertThat(response.getContentAsString(), is(equalTo("false")));
	}
	
//	UserRestController#login 내 session 설정해주는 부분 삭제하지 않으면 에러 발생하므로 주의!
	@Ignore	
	@Test
	public void loginTest() throws Exception {
		MvcResult result = mockMvc.perform(post("/community/user/login").contentType(MediaType.APPLICATION_JSON)
				.content("{\"email\":\"123@123.com\", \"password\":\"123\"}"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();	
		
		assertThat(result.getResponse().getContentAsString(), is(equalTo("true")));
	}
	@Ignore	
	@Test
	public void loginWithWrongPasswordTest() throws Exception {
		MvcResult result = mockMvc.perform(post("/community/user/login").contentType(MediaType.APPLICATION_JSON)
				.content("{\"email\":\"123@123.com\", \"password\":\"12\"}"))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();	
		
		assertThat(result.getResponse().getContentAsString(), is(equalTo("false")));
	}

	@Test
	public void updateUserTest() throws Exception {
		User user = new User();
		user.setId("19224af2-7e37-40ac-b4ab-dc42f9a339d8");
		user.setEmail("hello@hello.hello");
		user.setPassword("hello");
		
		String json = new Gson().toJson(user);
		
		MvcResult result = mockMvc.perform(put("/community/user/19224af2-7e37-40ac-b4ab-dc42f9a339d8").header("AUTHORIZATION", "BASIC aGloaUBoaS5jb206aGk=")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Ignore
	@Test
	public void deleteUserTest() throws Exception {
		MvcResult result = mockMvc.perform(delete("/community/user/" + "4235fbe2-2528-4c90-8e96-cc4a83ae1e47")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	@Ignore	
	@Test
	public void getAllUserByAdminUserTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/community/user/" + "41fb3bf9-27db-41af-b2fd-0341f85d5eef"+"/getAllUserList")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

	}
	@Ignore	
	@Test
	public void getAllUserByNotAdminUserTest() throws Exception {
		MvcResult result = mockMvc.perform(get("/community/user/" + "2d88babf-306f-41a4-a364-01b1e08dee2a"+"/getAllUserList")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andReturn();
	
		//이렇게 확인하는 게 맞는건지 체크 필요, null 이 내려오는 것을 체크해야 되는 경우인데, Not Found 에러를 내려보내는게 원래 목적
		assertThat(result.getResponse().getContentAsString(), is(""));
	}
} 
