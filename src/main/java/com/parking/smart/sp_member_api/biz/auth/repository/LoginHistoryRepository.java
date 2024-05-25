package com.parking.smart.sp_member_api.biz.auth.repository;

import com.parking.smart.sp_member_api.biz.auth.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
}
