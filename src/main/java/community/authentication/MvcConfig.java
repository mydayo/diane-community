package community.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SuppressWarnings("deprecation")
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private AuthenticationInterceptor authenticationInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor)
				.addPathPatterns("/community/board/**")
				.addPathPatterns("/community/user/**")
				.excludePathPatterns("/community/user/login")
				.excludePathPatterns("/community/user/registeration")
				.excludePathPatterns("/community/user/emailDuplicationCheck");
	}
}