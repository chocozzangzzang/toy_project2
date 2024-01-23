package cocozzang.toyproject.source.dto;

import cocozzang.toyproject.source.entity.NoticeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDTO {

    private Long nid;
    private String noticeWriter;
    private String noticeTitle;
    private String noticeContent;
    private String regTime;
    private String modTime;

    public static NoticeDTO entityToDTO(NoticeEntity noticeEntity) {
        return NoticeDTO.builder()
                .nid(noticeEntity.getId())
                .noticeWriter(noticeEntity.getNoticeWriter())
                .noticeTitle(noticeEntity.getNoticeTitle())
                .noticeContent(noticeEntity.getNoticeContent())
                .regTime(noticeEntity.getRegTime())
                .modTime(noticeEntity.getModTime())
                .build();
    }
}
