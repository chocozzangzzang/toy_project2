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

        commentRepository.save(commentEntity);
    }

    public void commentDelete(Long cid) {
        commentRepository.deleteById(cid);
    }

    public CommentDTO getComment(Long cid) {

        CommentEntity commentEntity = commentRepository.findById(cid).get();
        CommentDTO commentDTO = CommentDTO.entityToDTO(commentEntity);

        return commentDTO;
    }

    public void commentUpdate(CommentDTO commentDTO) {

        CommentEntity commentEntity = new CommentEntity();

        commentEntity.setId(commentDTO.getId());
        commentEntity.setBid(commentDTO.getBid());
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setComment(commentDTO.getComment());
        commentEntity.setCommRegTime(commentDTO.getCommRegTime());
        commentEntity.setCommModTime(commentDTO.getCommModTime());

        System.out.println(commentEntity);
        commentRepository.save(commentEntity);
    }

    public void commentDeleteByBid(Long bid) {
        commentRepository.deleteByBid(bid);
    }

}
