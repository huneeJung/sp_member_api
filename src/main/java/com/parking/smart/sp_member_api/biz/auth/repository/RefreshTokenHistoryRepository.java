package com.parking.smart.sp_member_api.biz.auth.repository;

import com.parking.smart.sp_member_api.biz.auth.entity.RefreshTokenIssueHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenHistoryRepository extends JpaRepository<RefreshTokenIssueHistory, Long> {

    Optional<RefreshTokenIssueHistory> findByRefreshToken(String refreshToken);

}
