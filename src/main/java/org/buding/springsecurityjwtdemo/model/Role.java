package org.buding.springsecurityjwtdemo.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @program: spring-security-jwt-demo
 * @author: miaochen
 * @create: 2019-07-05 09:44
 * @description:
 **/
public enum  Role  implements GrantedAuthority {
    ROLE_ADMIN,ROLE_CLIENT;

    @Override
    public String getAuthority() {
        return name();
    }
}
