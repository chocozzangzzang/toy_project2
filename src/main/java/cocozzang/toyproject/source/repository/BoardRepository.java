package cocozzang.toyproject.source.repository;

import cocozzang.toyproject.source.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

}
