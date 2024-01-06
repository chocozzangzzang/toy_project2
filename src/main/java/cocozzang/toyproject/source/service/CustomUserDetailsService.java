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

            return new CustomUserDetails(userdata);

        }
        return null;
    }
}
