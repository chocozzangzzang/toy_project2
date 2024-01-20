package cocozzang.toyproject.source.repository;

import cocozzang.toyproject.source.entity.AttachedFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<AttachedFileEntity, Long> {

    @Query(value = "select * from attachedfileentity afe where afe.bid = :bid", nativeQuery = true)
    List<AttachedFileEntity> findByBid(Long bid);

}
