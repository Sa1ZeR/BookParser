package su.sa1zer.bookparser.payload.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class BookFilterRequest {

    private String title;
    private Long ISBN;
    @Positive
    private Integer year;
    private Boolean yearLower;
    @Positive
    private Integer pages;
    private Boolean pagesLower;
    private List<Long> authors;
    private List<Long> genres;
    private List<Long> tags;
//    private LocalDate createFrom;
//    private LocalDate createdTo;

    //pagination
    @Min(value = 0, message = "offset must be >= 0 ")
    private Integer offset;
    @Min(value = 0, message = "resultPerPage must be >= 0 ")
    private Integer resultPerPage;
}
