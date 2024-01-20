package cocozzang.toyproject.source.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TempBoardDTO {

    private BoardDTO boardDTO;
    private boolean isAttached;

}
