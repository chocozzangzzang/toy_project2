package cocozzang.toyproject.source.service;

import cocozzang.toyproject.source.dto.CustomUserDetails;
import cocozzang.toyproject.source.entity.UserEntity;
import cocozzang.toyproject.source.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userdata = userRepository.findByUsername(username);

        if(userdata != null) {
            // id가 제대로 전달되는지 체크
            System.out.println("userdata id : " + userdata.getUsername());
            return new CustomUserDetails(userdata);
        }
        // id가 없는 경우 체크
        System.out.println("There is any id");
        return null;
    }
}
