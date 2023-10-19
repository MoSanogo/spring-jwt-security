package com.mosan.mosan.rest.jwtoken.configurations;

import com.mosan.mosan.rest.jwtoken.dao.domain.User;
import com.mosan.mosan.rest.jwtoken.dao.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.stereotype.Component;
import java.util.UUID;
@Component("permission")
public class Permission {
    @Autowired
    private IUserRepository userRepository;
    public boolean decide(MethodSecurityExpressionOperations operations, String permission, UUID userId) {
        if (operations.getAuthentication() == null) return false;
        if (operations.getAuthentication().getPrincipal() instanceof org.springframework.security.core.userdetails
                .User user) {
            //It would be nicer to extend rg.springframework.security.core.userdetails.User to add Id or just implements
            //UserDetails and CredentialsContainer interface to add id property and return it from UserDetailsService.
            var candidate = userRepository.findByUsername(user.getUsername()).orElseThrow();
            var authorities = operations.getAuthentication().getAuthorities();
            return authorities.stream().anyMatch(auth ->auth
                    .getAuthority().equals(permission))
                    && candidate.getId().toString()
                    .equals(userId.toString());

        }
        return false;



    }
}