package cocozzang.toyproject.source.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BoardDTO {

    private Long id;
    private String writer;
    private String content;
    private LocalDateTime regTime;
    private LocalDateTime modTime;

}
