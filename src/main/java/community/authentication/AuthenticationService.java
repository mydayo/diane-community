package community.authentication;

import org.springframework.http.HttpHeaders;

import community.user.entity.User;

public interface AuthenticationService {
	User authenticate(String token);
	HttpHeaders setResponseHeader(User user);
}
