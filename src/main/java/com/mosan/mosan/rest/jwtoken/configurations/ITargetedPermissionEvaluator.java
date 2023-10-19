package com.mosan.mosan.rest.jwtoken.configurations;

import org.springframework.security.access.PermissionEvaluator;

public interface ITargetedPermissionEvaluator  extends PermissionEvaluator {
    String getTargetType();
}
