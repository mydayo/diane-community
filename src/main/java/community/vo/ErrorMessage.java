package community.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorMessage {
	private String requestUri;
	private String LocalDateTime;
	private String message;
}
