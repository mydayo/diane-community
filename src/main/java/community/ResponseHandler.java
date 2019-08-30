package community;

import java.time.LocalDateTime;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import community.exception.BadRequestParameterException;
import community.exception.RequestTargetIsNullException;
import community.exception.UnauthorizedException;
import community.vo.ErrorMessage;
import community.vo.ResultFormat;

@ControllerAdvice("community")
@RestController
public class ResponseHandler implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
		ResultFormat output = new ResultFormat();
		output.setResult((servletResponse.getStatus() < 300) ? "SUCCESS" : "FAILED");
		output.setStatus(servletResponse.getStatus());
		output.setData(Arrays.asList(body));
		return output;
	}
	
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public ErrorMessage handleUnauthorizedException(UnauthorizedException e, HttpServletRequest request, HttpServletResponse response) {
		e.printStackTrace();
		return new ErrorMessage(request.getRequestURI(), LocalDateTime.now().toString(), e.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadRequestParameterException.class)
	public ErrorMessage handleBadRequestException(BadRequestParameterException e, HttpServletRequest request, HttpServletResponse response) {
		e.printStackTrace();
		return new ErrorMessage(request.getRequestURI(), LocalDateTime.now().toString(), e.getMessage());
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RequestTargetIsNullException.class)
	public ErrorMessage handleRequestTargetIsNullException(RequestTargetIsNullException e, HttpServletRequest request, HttpServletResponse response) {
		e.printStackTrace();
		return new ErrorMessage(request.getRequestURI(), LocalDateTime.now().toString(), e.getMessage());
	}
	
	 @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	 @ExceptionHandler(RuntimeException.class)
	 @ResponseBody
	 public ErrorMessage handleThrowable(HttpServletRequest request, final Throwable e) {
		e.printStackTrace();
		return new ErrorMessage(request.getRequestURI(), LocalDateTime.now().toString(), e.getMessage());
     }
}