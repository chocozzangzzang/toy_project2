package cocozzang.toyproject.source.dto;

import cocozzang.toyproject.source.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;
    private Long bid;
    private String commentWriter;
    private String comment;
    private String commRegTime;
    private String commModTime;

    public static CommentDTO entityToDTO(CommentEntity commentEntity) {
        return CommentDTO.builder()
                .id(commentEntity.getId())
                .bid(commentEntity.getBid())
                .commentWriter(commentEntity.getCommentWriter())
                .comment(commentEntity.getComment())
                .commRegTime(commentEntity.getCommRegTime())
                .commModTime(commentEntity.getCommModTime())
                .build();
    }

}
