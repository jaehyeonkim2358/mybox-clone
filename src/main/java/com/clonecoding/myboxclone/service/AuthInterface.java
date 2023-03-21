package com.clonecoding.myboxclone.service;

import com.clonecoding.myboxclone.dto.LoginReqDTO;
import com.clonecoding.myboxclone.dto.MemberReqDTO;
import com.clonecoding.myboxclone.dto.MemberResDTO;
import com.clonecoding.myboxclone.dto.TokenDTO;

public interface AuthInterface {
    MemberResDTO signup(MemberReqDTO signupReqDTO);
    TokenDTO login(LoginReqDTO loginReqDTO);
}
