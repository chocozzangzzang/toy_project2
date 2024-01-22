package cocozzang.toyproject.source.service;

import cocozzang.toyproject.source.dto.NoticeDTO;
import cocozzang.toyproject.source.entity.NoticeEntity;
import cocozzang.toyproject.source.repository.NoticeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public void noticeWrite(NoticeDTO noticeDTO) {
        NoticeEntity noticeEntity = new NoticeEntity();

        noticeEntity.setNoticeTitle(noticeDTO.getNoticeTitle());
        noticeEntity.setNoticeContent(noticeDTO.getNoticeContent());
        noticeEntity.setRegTime(noticeDTO.getRegTime());
        noticeEntity.setModTime(noticeDTO.getModTime());

        noticeRepository.save(noticeEntity);
    }

    public List<NoticeDTO> getAllNotices() {

        List<NoticeEntity> noticeEntities = noticeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<NoticeDTO> noticeDTOList = new ArrayList<>();

        for (NoticeEntity noticeEntity : noticeEntities) {
            NoticeDTO noticeDTO = NoticeDTO.entityToDTO(noticeEntity);
            noticeDTOList.add(noticeDTO);
        }

        return noticeDTOList;
    }

}
