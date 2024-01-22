package cocozzang.toyproject.source.repository;

import cocozzang.toyproject.source.entity.AttachedFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FileRepository extends JpaRepository<AttachedFileEntity, Long> {

    @Query(value = "select * from attachedfileentity afe where afe.bid = :bid", nativeQuery = true)
    List<AttachedFileEntity> findByBid(Long bid);

    @Modifying
    @Transactional
    @Query(value = "delete from attachedfileentity afe where afe.bid = :bid", nativeQuery = true)
    void deleteByBid(Long bid);

}
