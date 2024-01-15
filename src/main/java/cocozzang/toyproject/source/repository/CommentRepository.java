package cocozzang.toyproject.source.repository;

import cocozzang.toyproject.source.dto.CommentDTO;
import cocozzang.toyproject.source.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByBidOrderByIdDesc(Long bid);

}
