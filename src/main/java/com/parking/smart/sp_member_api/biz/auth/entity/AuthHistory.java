package com.parking.smart.sp_member_api.biz.auth.entity;

import com.parking.smart.sp_member_api.biz.common.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUTH_HISTORY")
public class AuthHistory extends CommonEntity {

    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "IP_ADRESS")
    private String ipAddress;

    @Column(name = "USE_YN")
    private Boolean useYN = false;

}
