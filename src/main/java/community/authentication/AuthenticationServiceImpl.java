package community.authentication;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import community.exception.ExceptionMsg;
import community.exception.UnauthorizedException;
import community.user.entity.User;
import community.user.repository.JpaUserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private final JpaUserRepository userRepository;
	
	private static final String HeaderKey = "AUTHORIZATION";
	private static final String HeaderValue = "BASIC ";

	@Autowired
	public AuthenticationServiceImpl(JpaUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User authenticate(String token) {
		try {
			String[] split = token.split(" ");
			String type = split[0];
			String credential = split[1];
			if ("Basic".equalsIgnoreCase(type)) {
				String decoded = new String(Base64Utils.decodeFromString(credential));
				String[] emailAndPassword = decoded.split(":");
				User user = userRepository.findByEmailAndPassword(emailAndPassword[0], emailAndPassword[1]);
				if (user == null) {
					throw new UnauthorizedException(ExceptionMsg.credentialExceptionMessage);
				} else {
					return user;
				}
			} else {
				throw new UnauthorizedException(type);
			}
		} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
			throw new UnauthorizedException(ExceptionMsg.credentialExceptionMessage);
		} catch (NullPointerException ex) {
			throw new NullPointerException(ExceptionMsg.nullTokenMessage);
		}
	}

	@Override
	public HttpHeaders setResponseHeader(User user) {
		HttpHeaders responseHeaders = new HttpHeaders();
		String token = user.getEmail() + ":" + user.getPassword();
		byte[] encoded = Base64.encodeBase64(token.getBytes());
		log.info(new String(encoded));
		responseHeaders.set(HeaderKey, HeaderValue + new String(encoded));
		
		return responseHeaders;
	}
}