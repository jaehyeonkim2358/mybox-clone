package com.clonecoding.myboxclone.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupReqDTO {
    private String email;
    private String name;
    private String password;
}
