package com.parking.smart.sp_member_api.biz.common.mapper;

import com.parking.smart.sp_member_api.biz.member.dto.JoinDto;
import com.parking.smart.sp_member_api.biz.member.dto.MemberDto;
import com.parking.smart.sp_member_api.biz.member.entity.Member;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-25T18:20:07+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member from(JoinDto joinDto) {
        if ( joinDto == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.memberId( joinDto.getMemberId() );
        member.password( joinDto.getPassword() );
        member.name( joinDto.getName() );
        member.age( joinDto.getAge() );

        return member.build();
    }

    @Override
    public MemberDto from(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto memberDto = new MemberDto();

        memberDto.setMemberId( member.getMemberId() );
        memberDto.setPassword( member.getPassword() );
        memberDto.setName( member.getName() );
        memberDto.setAge( member.getAge() );
        memberDto.setRole( member.getRole() );

        return memberDto;
    }
}
