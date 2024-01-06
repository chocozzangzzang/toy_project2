package cocozzang.toyproject.source.repository;

import cocozzang.toyproject.source.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String Username);

    UserEntity findByUsername(String username);

}
