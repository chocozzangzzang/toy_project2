package cocozzang.toyproject.source.service;

import cocozzang.toyproject.source.dto.AttachedFileDTO;
import cocozzang.toyproject.source.entity.AttachedFileEntity;
import cocozzang.toyproject.source.repository.FileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public void fileSave(AttachedFileDTO attachedFileDTO) {
        AttachedFileEntity attachedFileEntity = new AttachedFileEntity();
        attachedFileEntity.setBid(attachedFileDTO.getBid());
        attachedFileEntity.setOriginalFileName(attachedFileDTO.getOriginalFileName());
        attachedFileEntity.setStoredFileName(attachedFileDTO.getStoredFileName());
        fileRepository.save(attachedFileEntity);
    }

    public boolean isFileExists(Long bid) {
        List<AttachedFileEntity> attachedFileDTOList = fileRepository.findByBid(bid);
        if (attachedFileDTOList.size() == 0) return false;
        else return true;
    }

}
