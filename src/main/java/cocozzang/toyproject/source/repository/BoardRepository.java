package cocozzang.toyproject.source.repository;

import cocozzang.toyproject.source.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
