package com.sparta.delivery_app.common.security.jwt;

import com.sparta.delivery_app.common.exception.errorcode.JwtPropertiesErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.TokenNotFoundException;
import com.sparta.delivery_app.common.security.AuthenticationUserService;
import com.sparta.delivery_app.domain.user.adaptor.UserAdaptor;
import com.sparta.delivery_app.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthenticationUserService authenticationUserService;
    private final UserAdaptor userAdaptor;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, AuthenticationUserService authenticationUserService, UserAdaptor userAdaptor) {
        this.jwtUtil = jwtUtil;
        this.authenticationUserService = authenticationUserService;
        this.userAdaptor = userAdaptor;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String accessTokenValue = jwtUtil.getAccessTokenFromHeader(req);

        log.info("access token 검증");
        if (StringUtils.hasText(accessTokenValue) && jwtUtil.validateToken(req, accessTokenValue)) {
            log.info("refresh token 검증");

            String email = jwtUtil.getUserInfoFromToken(accessTokenValue).getSubject();
            String refreshToken = userAdaptor.queryUserByEmail(email).getRefreshToken();
            if (!refreshToken.isBlank()) {
                User user = userAdaptor.queryUserByEmail(email);

                if (isValidateUserAndToken(email, user)) {

                    log.info("Token 인증 완료");
                    Claims info = jwtUtil.getUserInfoFromToken(accessTokenValue);
                    setAuthentication(info.getSubject());
                }
            } else {
                log.error("유효하지 않는 Refersh Token");
                throw new TokenNotFoundException(JwtPropertiesErrorCode.TOKEN_NOT_FOUND);
            }
        }
        filterChain.doFilter(req, res);
    }

    private boolean isValidateUserAndToken(String email, User findUser) {
        return email.equals(findUser.getEmail());
    }


    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = authenticationUserService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}