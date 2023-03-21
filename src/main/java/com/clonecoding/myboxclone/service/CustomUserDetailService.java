package com.clonecoding.myboxclone.service;

import com.clonecoding.myboxclone.entity.Authority;
import com.clonecoding.myboxclone.entity.MemberEntity;
import com.clonecoding.myboxclone.entity.MemberRepository;
import com.clonecoding.myboxclone.exception.AuthException;
import com.clonecoding.myboxclone.exception.MemberException;
import com.clonecoding.myboxclone.exception.MemberExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws MemberException {
        log.debug("CustomUserDetailService -> email = {}", email);
        return memberRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(()-> new MemberException(MemberExceptionType.NOT_FOUND_USER));
    }

    @Transactional(readOnly = true)
    public MemberEntity getMemberEntity(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_USER));
    }

    private UserDetails createUserDetails(MemberEntity memberEntity) {
        List<SimpleGrantedAuthority> authList = memberEntity.getAuthorities()
                .stream()
                .map(Authority::getAuthorityName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        authList.forEach(o -> log.debug("authList -> {}", o.getAuthority()));

        return new User(
                memberEntity.getEmail(),
                memberEntity.getPassword(),
                authList
        );
    }
}
