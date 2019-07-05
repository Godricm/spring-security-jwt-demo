package org.buding.springsecurityjwtdemo.security;

import org.buding.springsecurityjwtdemo.exception.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: spring-security-jwt-demo
 * @author: miaochen
 * @create: 2019-07-05 10:32
 * @description:我们应该使用OncePerRequestFilter，因为我们正在执行一个数据库调用，这样做没有意义不止一次
 **/
public class JwtTokenFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token=jwtTokenProvider.resolveToken(httpServletRequest);

        try {
            if(token!=null&&jwtTokenProvider.vlidateToken(token)){
                Authentication authentication=jwtTokenProvider.getAuthentication(token);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (CustomException e) {
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(e.getHttpStatus().value(),e.getMessage());
            return;
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
