package com.clonecoding.myboxclone.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "member_authority",
            joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")}
    )
    private Set<Authority> authorities = new HashSet<>();

    @Builder
    public MemberEntity(Long member_id, String email, String name, String password, Set<Authority> authorities) {
        this.member_id = member_id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }
}
