package com.mosan.mosan.rest.jwtoken.configurations;

import com.mosan.mosan.rest.jwtoken.dao.domain.User;
import com.mosan.mosan.rest.jwtoken.dao.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Slf4j
@Service
public class JpaUserDetailsService  implements UserDetailsService {
    private final IUserRepository userRepository;

    public JpaUserDetailsService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Bad credentials"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonLocked(),
                user.getCredentialsNonExpired(),
                user.getAccountNonExpired(),
                user.getAuthorities()
        );
    }
}