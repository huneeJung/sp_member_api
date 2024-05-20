package com.parking.smart.sp_member_api.biz.auth.repository;

import com.parking.smart.sp_member_api.biz.auth.entity.RefreshTokenHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenHistoryRepository extends JpaRepository<RefreshTokenHistory,Long> {

    Optional<RefreshTokenHistory> findByRefreshToken(String refreshToken);

}
