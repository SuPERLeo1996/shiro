package com.Leo.test;

import com.Leo.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Auther: Leo
 * @Date: 2018/9/5 10:07
 * @Description:
 */
public class CustomRealmTest {
    @Test
    public void testAuthentication() {
        CustomRealm customRealm = new CustomRealm();

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("LEO", "123456");

        //UsernamePasswordToken token = new UsernamePasswordToken("LEO","123456");
        subject.login(token);

        System.out.println("isAuthenticate:" + subject.isAuthenticated());

        //subject.checkRole("admin");

        //subject.checkPermissions("user:add","user:delete");

    }

}
