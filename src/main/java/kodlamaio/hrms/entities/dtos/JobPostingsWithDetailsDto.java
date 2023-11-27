package kodlamaio.hrms.entities.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPostingsWithDetailsDto {
	private Long jobPostingId;
	private Long userId;
	private String 	companyName;
	private int titleId;
	private String jobTitleName;
	private int cityId;
	private String cityName;
	private LocalDateTime publicationDate;
	private LocalDate applicationDeadline;
	private int availablePosition;
	private String jobSummary;
	private String jobDescription;
}
