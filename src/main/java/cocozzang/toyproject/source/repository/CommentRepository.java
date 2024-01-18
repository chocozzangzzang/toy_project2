package cocozzang.toyproject.source.repository;

import cocozzang.toyproject.source.dto.CommentDTO;
import cocozzang.toyproject.source.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findAllByBidOrderByIdDesc(Long bid);

    @Transactional
    @Modifying
    @Query(value = "delete from commententity ce where ce.bid = :bid", nativeQuery = true)
    void deleteByBid(@Param("bid") Long bid);

}
