package community.vo;

import java.util.List;

import lombok.Data;

@Data
public class ResultFormat {
	String Result;
	int Status;
	List<?> data;
}
