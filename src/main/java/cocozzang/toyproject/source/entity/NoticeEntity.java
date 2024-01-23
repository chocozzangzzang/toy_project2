package cocozzang.toyproject.source.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class NoticeEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String noticeWriter;
        private String noticeTitle;
        private String noticeContent;
        private String regTime;
        private String modTime;

}
