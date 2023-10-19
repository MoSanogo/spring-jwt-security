package com.mosan.mosan.rest.jwtoken.configurations;

import com.mosan.mosan.rest.jwtoken.dao.domain.Animal;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Set;

public class AnimalPermissionEvaluator implements  ITargetedPermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
    if(authentication!=null && targetDomainObject!=null && Animal.class.isAssignableFrom(targetDomainObject.getClass())){
            Animal domainObject=(Animal) targetDomainObject;
            var authorities=(Set<? extends GrantedAuthority>) authentication.getAuthorities();
            return authorities.stream().anyMatch(auth->auth.getAuthority().equals((String)permission))
                    && ((String) permission).contains(domainObject.getClass().getSimpleName());
  }
  return false;
}
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }

    @Override
    public String getTargetType() {
        return Animal.class.getSimpleName();
    }
}
