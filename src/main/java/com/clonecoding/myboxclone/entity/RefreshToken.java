package com.clonecoding.myboxclone.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "refresh_token")
@Getter
public class RefreshToken {
    @Id
    private String refreshTokenKey;

    @Column(nullable = false)
    private String refreshTokenValue;

    public void updateValue(String token) {
        this.refreshTokenKey = token;
    }

    @Builder
    public RefreshToken(String refreshTokenKey, String refreshTokenValue) {
        this.refreshTokenKey = refreshTokenKey;
        this.refreshTokenValue = refreshTokenValue;
    }
}
