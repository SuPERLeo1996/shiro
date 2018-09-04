package com.Leo.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Auther: Leo
 * @Date: 2018/9/4 15:25
 * @Description:
 */
public class JdbcRealmTest {
    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://localhost/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
    }


    @Test
    public void testAuthentication(){

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        //2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("LEO","123456");
        subject.login(token);

        System.out.println("isAuthenticate:" + subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermission("user:delete");

    }
}
