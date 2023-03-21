package com.clonecoding.myboxclone.jwt;

import com.clonecoding.myboxclone.dto.TokenDTO;
import com.clonecoding.myboxclone.entity.Authority;
import com.clonecoding.myboxclone.exception.AuthException;
import com.clonecoding.myboxclone.exception.AuthExceptionType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Component
public class TokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private final long ACCESS_TOKEN_EXPIRE_TIME;
    private final long REFRESH_TOKEN_EXPIRE_TIME;
    private final Key key;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.access-token-expire-time}") long accessTime,
                         @Value("${jwt.refresh-token-expire-time}") long refreshTime) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        this.ACCESS_TOKEN_EXPIRE_TIME = accessTime;
        this.REFRESH_TOKEN_EXPIRE_TIME = refreshTime;
    }

    protected String createToken(String email, Set<Authority> auth, long tokenValid) {
        Claims claims = Jwts.claims().setSubject(email);

        claims.put(AUTHORITIES_KEY,
                auth.stream()
                        .map(Authority::getAuthorityName)
                        .collect(Collectors.joining(","))
                );

        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

        Date expire = new Date(now.getTime() + tokenValid);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(key, SignatureAlgorithm.ES512)
                .compact();
    }

    public String createAccessToken(String email, Set<Authority> auth) {
        return this.createToken(email, auth, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String createRefreshToken(String email, Set<Authority> auth) {
        return this.createToken(email, auth, REFRESH_TOKEN_EXPIRE_TIME);
    }

    public TokenDTO createTokenDTO(String accessToken, String refreshToken) {
        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .grantType(BEARER_TYPE)
                .build();
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if(claims.get(AUTHORITIES_KEY) == null
                || !StringUtils.hasText(claims.get(AUTHORITIES_KEY).toString())) {
            throw new AuthException(AuthExceptionType.NOT_FOUND_AUTHORITY);
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        authorities.forEach(o -> {
            log.debug("getAuthentication -> authorities = {}", o.getAuthority());
        });

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new CustomEmailPasswordAuthToken(principal, "", authorities);
    }

    public TokenStatus validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return TokenStatus.VALID_TOKEN;
        } catch (ExpiredJwtException e) {
            log.debug("만료된 JWT 토큰입니다.");
            return TokenStatus.EXPIRED_TOKEN;
        } catch (Exception e) {
            log.debug("잘못된 토큰입니다.");
            return TokenStatus.WRONG_TOKEN;
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
