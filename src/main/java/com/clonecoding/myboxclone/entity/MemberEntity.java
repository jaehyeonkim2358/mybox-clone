package com.clonecoding.myboxclone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class MemberEntity {
    @JsonIgnore
    @Column(name = "member_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long member_id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Builder
    public MemberEntity(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
