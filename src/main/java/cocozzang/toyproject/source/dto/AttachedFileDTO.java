package cocozzang.toyproject.source.dto;

import lombok.Data;

@Data
public class AttachedFileDTO {

    private Long bid;
    private String originalFileName;
    private String storedFileName;
}
