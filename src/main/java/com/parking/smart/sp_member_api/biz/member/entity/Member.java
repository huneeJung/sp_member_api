package com.parking.smart.sp_member_api.biz.member.entity;

import com.parking.smart.sp_member_api.biz.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEMBER")
public class Member extends CommonEntity {

    @Column(name = "MEMBER_ID", unique = true)
    private String memberId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private String age;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

}


