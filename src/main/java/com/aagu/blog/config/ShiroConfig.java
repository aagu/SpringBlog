package com.aagu.blog.config;

import com.aagu.blog.Realm.AdminRealm;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    /**
     * 创建Realm
     * @return AdminRealm
     */
    @Bean
    public AdminRealm createAdminRealm() {
        AdminRealm realm = new AdminRealm();
        realm.setCacheManager(new MemoryConstrainedCacheManager());
        // TODO 密码加密
        //realm.setCredentialsMatcher(new HashedCredentialsMatcher("MD5"));
        return realm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(AdminRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    /**
     * 实例化过滤器
     *
     * @param securityManager securityManager
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setSecurityManager(securityManager);
        /*
         * 下面就是进行页面的认证操作的过滤器
         * 过滤的条件
         */
        Map<String, String> map = new LinkedHashMap<>(1);
//        map.put("/admin/login", "anon");
//        map.put("/admin/login-from", "anon");
//        map.put("/admin/logout", "logout");
//
//        map.put("/admin**", "authc");
//
//        filter.setLoginUrl("/admin/login");
//        //filter.setSuccessUrl("/admin/index");
//        filter.setUnauthorizedUrl("/unauthorized");

        //这一步是讲设置的过滤规则添加到
        filter.setFilterChainDefinitionMap(map);
        return filter;
    }

    /**
     * 开启注解代理功能
     *
     * @param securityManager securityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /**
     * 为了使注解生效
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        return new DefaultAdvisorAutoProxyCreator();
    }
}
