package com.parking.smart.sp_member_api.biz.common.mapper;

import com.parking.smart.sp_member_api.biz.member.dto.JoinDto;
import com.parking.smart.sp_member_api.biz.member.dto.MemberDto;
import com.parking.smart.sp_member_api.biz.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    Member from(JoinDto joinDto);

    MemberDto from(Member member);
}
