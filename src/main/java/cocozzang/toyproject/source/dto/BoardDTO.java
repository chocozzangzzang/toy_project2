package cocozzang.toyproject.source.dto;

import cocozzang.toyproject.source.entity.BoardEntity;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    private Long boardId;
    private String title;
    private String writer;
    private String content;
    private String regTime;
    private String modTime;

    public static BoardDTO entityToDTO(BoardEntity boardEntity) {
        return BoardDTO.builder()
                .boardId(boardEntity.getId())
                .title(boardEntity.getTitle())
                .writer(boardEntity.getWriter())
                .content(boardEntity.getContent())
                .regTime(boardEntity.getRegDate())
                .modTime(boardEntity.getModDate())
                .build();
    }
}
