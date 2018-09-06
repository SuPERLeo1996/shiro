package com.Leo.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * @Auther: Leo
 * @Date: 2018/9/6 15:28
 * @Description:
 */
public class RolesOrFilter extends AuthorizationFilter{
    @Override
    protected boolean isAccessAllowed(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest,servletResponse);
        String[] roles =(String[]) o;
        if(roles == null || roles.length == 0){
            return true;
        }
        for (String role : roles){
            if(subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
