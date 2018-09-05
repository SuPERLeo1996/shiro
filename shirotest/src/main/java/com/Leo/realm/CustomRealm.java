package com.Leo.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: Leo
 * @Date: 2018/9/5 09:56
 * @Description:
 */
public class CustomRealm extends AuthorizingRealm {
    Map<String,String> userMap = new HashMap<>(16);

    {
        //userMap.put("LEO","123456");
        userMap.put("LEO","9197104839aeb583a462e6e0f11e6bb3");

        super.setName("customRealm");
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String)principalCollection.getPrimaryPrincipal();
        //从数据库或者缓存中获取角色数据
        Set<String> roles = getRolesByUserName(username);
        Set<String> permissions = getPermissionsByUserName(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permissions);
        simpleAuthorizationInfo.setRoles(roles);
        return null;
    }

    private Set<String> getPermissionsByUserName(String username) {
        Set<String> sets = new HashSet<>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }

    private Set<String> getRolesByUserName(String username) {
        Set<String> sets = new HashSet<>();
        sets.add("admin");
        sets.add("user");
        return sets;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.从主体传过来的认证信息中，获得用户名
        String username = (String) authenticationToken.getPrincipal();

        //2.通过用户名到数据库中获取凭证
        String password = getPasswordByUserName(username);
        if(password == null){
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo("LEO",password,"customRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("LEO"));
        return simpleAuthenticationInfo;
    }

    /**
     * 模拟数据库查询凭证
     * @param username
     * @return
     */
    private String getPasswordByUserName(String username) {
        return userMap.get(username);
    }

    public static void main(String[] arg){
        Md5Hash md5Hash = new Md5Hash("123456","LEO");
        System.out.println(md5Hash.toString());
    }

}
