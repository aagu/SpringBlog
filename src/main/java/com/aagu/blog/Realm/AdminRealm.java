package com.aagu.blog.Realm;

import com.aagu.blog.Dao.UserDao;
import com.aagu.blog.Models.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminRealm extends AuthorizingRealm {
    @Autowired
    UserDao userDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        User user = userDao.getByName(userToken.getUsername());
        if (user == null) {
            throw new UnknownAccountException("没有此用户");
        }

        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }
}
