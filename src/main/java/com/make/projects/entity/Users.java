package com.make.projects.entity;

import com.make.projects.entity.Common.CommonDate;
import com.make.projects.entity.enumType.Provider;
import com.make.projects.entity.enumType.Role;
import lombok.*;

import javax.persistence.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Users extends CommonDate {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String email;

    //private String password;

    private String specialty;

    private String imgUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role roles;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Provider provider;


}
