package com.clonecoding.myboxclone.jwt;

import com.clonecoding.myboxclone.dto.ResponseBodyDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import springfox.documentation.service.Encoding;
import springfox.documentation.service.Response;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer";
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {


        if(request.getServletPath().startsWith("/auth")) {
            filterChain.doFilter(request, response);
        } else {
            final String token = resolveToken(request);

            if(StringUtils.hasText(token)) {
                final TokenStatus flag = tokenProvider.validateToken(token);

                log.debug("flag in doFilterInternal = {}", flag);
                if(flag.equals(TokenStatus.VALID_TOKEN)) {
                    this.setAuthentication(token);
                    filterChain.doFilter(request, response);
                } else {
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(
                            getInvalidTokenResponse(flag)
                    );
                }
            }
        }
    }

    private void setAuthentication(String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            log.debug("bearerToken = {}", bearerToken);
            String result = bearerToken.substring(BEARER_PREFIX.length()+1);
            log.debug("bearerToken -> result = {}", result);
            return result;
        }
        return null;
    }

    private String getInvalidTokenResponse(TokenStatus status) {
        try {
            return new ObjectMapper().writeValueAsString(
                    ResponseBodyDTO.builder()
                            .message("토큰이 만료되었습니다.")
                            .data(status)
                            .build()
            );
        } catch (JsonProcessingException e) {
            log.debug("Exception in getInvalidTokenResponse -> {}", e.getMessage());
            return status.getAbbreviation();
        }
    }
}
