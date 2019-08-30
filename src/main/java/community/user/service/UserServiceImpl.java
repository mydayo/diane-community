package community.user.service;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import community.exception.BadRequestParameterException;
import community.exception.ExceptionMsg;
import community.exception.RequestTargetIsNullException;
import community.exception.UnauthorizedException;
import community.user.entity.User;
import community.user.repository.JpaUserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	JpaUserRepository jpaUserRepository;
	
	private static final String HeaderKey = "AUTHORIZATION";
	private static final String HeaderValue = "BASIC ";
	
	@Override
	public User addUser(User user) throws Exception {
		if(!validateUser(user)) {
			throw new BadRequestParameterException(ExceptionMsg.exceptionMessageWithNullValue);
		} else if (!validateEmail(user.getEmail())) {
			throw new BadRequestParameterException(ExceptionMsg.exceptionMessageWithNotValidEmail);
		}
		
		return jpaUserRepository.save(user);
	}
	
	@Override
	public User updateUser(String id, User user) throws Exception {
		if (!validateEmail(user.getEmail())) {
			throw new BadRequestParameterException(ExceptionMsg.exceptionMessageWithNotValidEmail);
		}
		User updateUser = jpaUserRepository.findById(id).get();
		updateUser.setEmail(user.getEmail());
		updateUser.setPassword(user.getPassword());
		
		jpaUserRepository.save(updateUser);
		
		return jpaUserRepository.findById(id).get();
	}
	
	@Override
	public void deleteUser(String id) throws Exception{
			User deleteUser = jpaUserRepository.findById(id).get();
			jpaUserRepository.deleteById(deleteUser.getId());
	}
	
	@Override
	public boolean emailDuplicationCheck(String email) throws Exception {
		if (jpaUserRepository.findByEmail(email) != null) {
			return true;
		} 
		return false;
	}
	
	@Override
	public User loginUser(User user) throws Exception {
		User userDB = getUser(user.getEmail());
		if (user.getPassword().equals(userDB.getPassword())) {
			HttpHeaders responseHeaders = new HttpHeaders();
			String token = user.getEmail() + ":" + user.getPassword();
			byte[] encoded = Base64.encodeBase64(token.getBytes());
			responseHeaders.set(HeaderKey, HeaderValue + new String(encoded));
			return userDB;
		} else {
			throw new UnauthorizedException();
		}
	}
	
	@Override
	public List<User> getAllUser(String email) throws Exception {
		if (validateAdminUser(email)) {
			return jpaUserRepository.findAll();
		} else {
			throw new NullPointerException();
		}
	}

	public User getUser(String email) throws Exception {
		User user = jpaUserRepository.findByEmail(email);
		if (user == null) {
			throw new RequestTargetIsNullException(ExceptionMsg.userIsNullExceptionMessage);
		}
		return user;
	}
	
	private boolean validateEmail(String email) throws Exception {
		Pattern p = Pattern.compile(ExceptionMsg.emailRegex);
		return p.matcher(email).find();
	}
	
	
	private boolean validateUser(User user) throws Exception{
		return (user.getEmail() != null && user.getPassword() != null) ? true : false;
	}
	
	private boolean validateAdminUser(String email) throws Exception {
		return jpaUserRepository.findByEmail(email).isAdmin();
	}
}
