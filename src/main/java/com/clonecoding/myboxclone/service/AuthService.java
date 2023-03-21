package com.clonecoding.myboxclone.service;

import com.clonecoding.myboxclone.dto.LoginReqDTO;
import com.clonecoding.myboxclone.dto.MemberReqDTO;
import com.clonecoding.myboxclone.dto.MemberResDTO;
import com.clonecoding.myboxclone.dto.TokenDTO;
import com.clonecoding.myboxclone.entity.MemberEntity;
import com.clonecoding.myboxclone.entity.MemberRepository;
import com.clonecoding.myboxclone.entity.RefreshToken;
import com.clonecoding.myboxclone.entity.RefreshTokenRepository;
import com.clonecoding.myboxclone.exception.MemberException;
import com.clonecoding.myboxclone.exception.MemberExceptionType;
import com.clonecoding.myboxclone.jwt.CustomEmailPasswordAuthToken;
import com.clonecoding.myboxclone.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthInterface {
    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailService customUserDetailService;
    private final TokenProvider tokenProvider;

    @Override
    @Transactional
    public MemberResDTO signup(MemberReqDTO memberReqDTO) {
        log.debug("signup service !!!");
        if (memberRepository.existsByEmail(memberReqDTO.getEmail())) {
            throw new MemberException(MemberExceptionType.DUPLICATE_USER);
        }

        MemberEntity member = memberReqDTO.toMemberEntity(passwordEncoder);
        log.debug("member = {}", member);

        return MemberResDTO.of(memberRepository.save(member));
    }

    @Override
    @Transactional
    public TokenDTO login(LoginReqDTO loginReqDTO) {
        CustomEmailPasswordAuthToken customEmailPasswordAuthToken
                = new CustomEmailPasswordAuthToken(
                        loginReqDTO.getEmail(), loginReqDTO.getPassword()
        );

        Authentication authenticate = authenticationManager.authenticate(customEmailPasswordAuthToken);

        String email = authenticate.getName();
        MemberEntity member = customUserDetailService.getMemberEntity(email);
        String accessToken = tokenProvider.createAccessToken(email, member.getAuthorities());
        String refreshToken = tokenProvider.createRefreshToken(email, member.getAuthorities());

        log.debug("member = {}", member);

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .refreshTokenKey(email)
                        .refreshTokenValue(refreshToken)
                        .build()
        );

        return tokenProvider.createTokenDTO(accessToken, refreshToken);
    }
}
