package spring.todo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer=";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    private final UserDetailsServiceImpl userDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오기 -> 헤더검사 없으면 쿠키 검사
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        /*헤더에 값이 없다면 토큰 확인*/
        if (bearerToken == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    String name = cookie.getName();
                    String value = cookie.getValue();
                    if (name.equals(AUTHORIZATION_HEADER)) {
                        bearerToken = value;
                    }
                }
            }
        }

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 생성
    public String createToken(String id, Authority role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(id)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    /**
     * 토큰 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException exception) {
            log.info("INVALID JWT SIGNATURE, 유효하지 않은 JWT 서명");
        } catch (ExpiredJwtException e) {
            log.info("EXPIRED JWT TOKEN, 만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("UNSUPPORTED JWT TOKEN, 지원되지 않는 JWT 토큰");
        } catch (IllegalArgumentException e) {
            log.info("JWT CLAIMS IS EMPTY, 잘못된 JWT 토큰");
        }
        return false;
    }

    /**
     * 토큰에서 사용자 정보 가져오기
     */
    public Claims getMemberInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * 인증 객체 생성
     */
    public Authentication createAuthentication(String id) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(id);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
