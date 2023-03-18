package com.clonecoding.myboxclone.controller;

import com.clonecoding.myboxclone.dto.MemberReqDTO;
import com.clonecoding.myboxclone.dto.MemberResDTO;
import com.clonecoding.myboxclone.dto.TokenDTO;
import com.clonecoding.myboxclone.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("signup")
    public MemberResDTO signup(@RequestBody MemberReqDTO memberReqDTO) {
        return authService.signup(memberReqDTO);
    }

    @PostMapping("login")
    public TokenDTO login() {
        return null;
    }
}
