package com.parking.smart.sp_member_api.biz.member.repository;

import com.parking.smart.sp_member_api.biz.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberId(String memberId);

    boolean existsByMemberId(String memberId);

}

