package com.clonecoding.myboxclone.controller;

import com.clonecoding.myboxclone.dto.SignupReqDTO;
import com.clonecoding.myboxclone.dto.SignupResDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
public class AuthController {
    @GetMapping("signup")
    public SignupResDTO signup(@RequestBody SignupReqDTO signupReqDTO) {
        return null;
    }
}
