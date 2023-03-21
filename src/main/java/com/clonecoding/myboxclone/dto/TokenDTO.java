package com.clonecoding.myboxclone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TokenDTO {
    private String accessToken;
    private String refreshToken;
    private String grantType;       // 토큰 정보가 담긴 Prefix

    @Override
    public String toString() {
        return "TokenDTO{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", grantType='" + grantType + '\'' +
                '}';
    }
}
