package com.xwdx.config;

import com.xwdx.filter.JwtLoginFilter;
import com.xwdx.filter.JwtVerifyFilter;
import com.xwdx.handler.MyAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author Json
 * @date 2021/10/29 15:08
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 允许访问
                .antMatchers("/jwt/login").permitAll()
                .anyRequest().authenticated() // 其他请求拦截
                .and()
                .csrf().disable() //关闭csrf
                .addFilter(new JwtLoginFilter(super.authenticationManager()))
                .addFilter(new JwtVerifyFilter(super.authenticationManager()))
                .exceptionHandling()
                .authenticationEntryPoint(myAuthenticationEntryPoint) // 自定义未登录返回
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //禁用session
    }

}
