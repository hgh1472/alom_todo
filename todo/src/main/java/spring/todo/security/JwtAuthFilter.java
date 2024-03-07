package spring.todo.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtUtil.resolveToken(request);
        log.info("URI = {}", request.getRequestURI());

        if (token != null) {
            if (!jwtUtil.validateToken(token)) {
                log.warn("JWT token 인증 실패");
                throw new IllegalArgumentException("JWT Token 인증 실패");
            }
            Claims info = jwtUtil.getMemberInfoFromToken(token);
            setAuthentication(info.getSubject());
        }
        log.info("jwt filter end");

        /*다음 필터 진행*/
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String id) {
        /*jwt 인증 성공 시 */
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(id);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }
}