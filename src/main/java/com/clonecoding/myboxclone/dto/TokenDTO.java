package com.clonecoding.myboxclone.dto;

public class TokenDTO {
    private String accessToken;
    private String refreshToken;

    /**
     * 토큰 정보가 담긴 Prefix
     * */
    private String grantType;

    @Override
    public String toString() {
        return "TokenDTO{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", grantType='" + grantType + '\'' +
                '}';
    }
}
