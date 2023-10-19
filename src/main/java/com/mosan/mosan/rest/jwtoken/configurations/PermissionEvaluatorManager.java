package com.mosan.mosan.rest.jwtoken.configurations;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.Map;

public class PermissionEvaluatorManager implements PermissionEvaluator {
    private static final PermissionEvaluator denyAll=new DenyAllPermissionEvaluator();
    private final Map<String,PermissionEvaluator>permissionEvaluatorMap;

    public PermissionEvaluatorManager(Map<String, PermissionEvaluator> permissionEvaluatorMap) {
        this.permissionEvaluatorMap = permissionEvaluatorMap;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
     PermissionEvaluator permissionEvaluator=permissionEvaluatorMap.get(targetDomainObject.getClass().getSimpleName());
     if(permissionEvaluator==null) permissionEvaluator=denyAll;
     return permissionEvaluator.hasPermission(authentication,targetDomainObject,permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        PermissionEvaluator permissionEvaluator=permissionEvaluatorMap.get(targetType);
        if(permissionEvaluator==null) permissionEvaluator=denyAll;
        return permissionEvaluator.hasPermission(authentication,targetId,targetType,permission);
    }
}
