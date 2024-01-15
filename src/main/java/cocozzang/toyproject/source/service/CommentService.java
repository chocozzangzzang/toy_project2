package cocozzang.toyproject.source.service;

import cocozzang.toyproject.source.dto.CommentDTO;
import cocozzang.toyproject.source.entity.CommentEntity;
import cocozzang.toyproject.source.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<CommentDTO> boardComment(Long bid) {
        List<CommentDTO> commentDTOList = new ArrayList<>();

        List<CommentEntity> commentEntityList = commentRepository.findAllByBidOrderByIdDesc(bid);

        for(int i = 0; i < commentEntityList.size(); i++) {
            commentDTOList.add(CommentDTO.entityToDTO(commentEntityList.get(i)));
        }

        return commentDTOList;
    }

    public void commentWrite(CommentDTO commentDTO) {

        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setBid(commentDTO.getBid());
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setComment(commentDTO.getComment());
        commentEntity.setCommRegTime(commentDTO.getCommRegTime());
        commentEntity.setCommModTime(commentDTO.getCommModTime());

        this.commentRepository.save(commentEntity);
    }

}