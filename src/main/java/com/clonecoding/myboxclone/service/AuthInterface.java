package com.clonecoding.myboxclone.service;

import com.clonecoding.myboxclone.dto.MemberReqDTO;
import com.clonecoding.myboxclone.dto.MemberResDTO;

public interface AuthInterface {
    MemberResDTO signup(MemberReqDTO signupReqDTO);
}
