package community.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import community.authentication.AuthenticationService;
import community.user.entity.User;
import community.user.service.UserService;
import io.swagger.annotations.Api;

@Api("User System")
@RestController
@RequestMapping("/community/user")
public class UserRestController {

	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationService authenticationService;

	@RequestMapping(value = "/registeration", method = RequestMethod.POST)
	public User addUser(@RequestBody User user) throws Exception {
		return userService.addUser(user);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable String id, @RequestBody User user) throws Exception {
		return userService.updateUser(id, user);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String id) throws Exception {
		userService.deleteUser(id);
	}
	
	@RequestMapping(value = "/getAllUserList", method = RequestMethod.GET)
	public List<User> getAllUser(@RequestAttribute User user) throws Exception {
		return userService.getAllUser(user.getEmail());
	}
	
	@RequestMapping(value = "/emailDuplicationCheck", method = RequestMethod.POST)
	public boolean emailDuplicationCheck(@RequestParam(value = "email") String email) throws Exception {
		return userService.emailDuplicationCheck(email);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<User> login(@RequestBody User user) throws Exception {
		return  new ResponseEntity<User>(userService.loginUser(user), authenticationService.setResponseHeader(user), HttpStatus.ACCEPTED);
	}
}
