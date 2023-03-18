package com.clonecoding.myboxclone.service;

import com.clonecoding.myboxclone.dto.MemberReqDTO;
import com.clonecoding.myboxclone.dto.MemberResDTO;
import com.clonecoding.myboxclone.entity.MemberEntity;
import com.clonecoding.myboxclone.entity.MemberRepository;
import com.clonecoding.myboxclone.exception.MemberException;
import com.clonecoding.myboxclone.exception.MemberExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements AuthInterface{
    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public MemberResDTO signup(MemberReqDTO memberReqDTO) {
        if (memberRepository.existsByEmail(memberReqDTO.getEmail())) {
            throw new MemberException(MemberExceptionType.DUPLICATE_USER);
        }

        MemberEntity member = memberReqDTO.toMemberEntity(passwordEncoder);
        log.debug("member = {}", member);

        return MemberResDTO.of(memberRepository.save(member));
    }
}
