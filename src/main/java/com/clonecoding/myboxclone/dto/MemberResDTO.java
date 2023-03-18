package com.clonecoding.myboxclone.dto;

import com.clonecoding.myboxclone.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResDTO {
    private String email;
    private String name;

    public static MemberResDTO of(MemberEntity memberEntity) {
        return MemberResDTO.builder()
                .email(memberEntity.getEmail())
                .name(memberEntity.getName())
                .build();
    }
}
