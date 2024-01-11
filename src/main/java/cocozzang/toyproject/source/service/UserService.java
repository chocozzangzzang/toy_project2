package cocozzang.toyproject.source.service;

import cocozzang.toyproject.source.dto.UserDTO;
import cocozzang.toyproject.source.entity.UserEntity;
import cocozzang.toyproject.source.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO rtUser(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userEntity.getUsername());
        if (userEntity.getApikey() != null) {
            userDTO.setApikey(userEntity.getApikey());
        }

        return userDTO;
    }

}
