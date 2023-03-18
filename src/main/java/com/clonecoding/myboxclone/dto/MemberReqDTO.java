package com.clonecoding.myboxclone.dto;

import com.clonecoding.myboxclone.entity.MemberEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberReqDTO {
    private String email;
    private String name;
    private String password;

    public MemberEntity toMemberEntity(PasswordEncoder passwordEncoder) {
        return MemberEntity.builder()
                .email(this.email)
                .name(this.name)
                .password(passwordEncoder.encode(this.password))
                .build();
    }
}
