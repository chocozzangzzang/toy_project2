package cocozzang.toyproject.source.service;

import cocozzang.toyproject.source.dto.JoinDTO;
import cocozzang.toyproject.source.entity.UserEntity;
import cocozzang.toyproject.source.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {

        // DB 검증
        boolean isUserExist = userRepository.existsByUsername(joinDTO.getUsername());
        if(isUserExist) {

            return;
        }

        // DTO -> Entity
        UserEntity userData = new UserEntity();

        userData.setUsername(joinDTO.getUsername());
        userData.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        userData.setRole("ROLE_USER");

        userRepository.save(userData);
    }

}
