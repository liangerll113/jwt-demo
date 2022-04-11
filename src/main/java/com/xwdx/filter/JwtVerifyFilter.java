package com.xwdx.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xwdx.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求校验
 *
 * @author Json
 * @date 2021/11/6 14:34
 */
public class JwtVerifyFilter extends BasicAuthenticationFilter {

    public JwtVerifyFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        if (token == null) {
            noJwtToken(response);
            return;
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        // 用户可以访问的资源名称（或者说用户所拥有的权限） 注意：必须"ROLE_"开头
        authorities.add(new SimpleGrantedAuthority("user:resource"));
        try {
            String loginName = JwtUtil.parseToken(token);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                    (loginName, token, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e) {
            errorToken(response);
            return;
        }
        chain.doFilter(request, response);

    }

    public void noJwtToken(HttpServletResponse response) throws IOException {
        //登录失败时，返回json格式进行提示
        Map<String, Object> map = new HashMap<String, Object>(4);
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 这里还有其他的 异常 。。 比如账号锁定  过期 等等。。。
        map.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        map.put("message", "jwt token is null");

        response.getWriter().write(JSON.toJSONString(map));
        response.getWriter().flush();
        response.getWriter().close();
    }

    public void errorToken(HttpServletResponse response) throws IOException {
        //登录失败时，返回json格式进行提示
        Map<String, Object> map = new HashMap<String, Object>(4);
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 这里还有其他的 异常 。。 比如账号锁定  过期 等等。。。
        map.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        map.put("message", "jwt token is error");

        response.getWriter().write(JSON.toJSONString(map));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
