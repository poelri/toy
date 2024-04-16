package com.example.toy.dao;

import com.example.toy.dto.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    public Member loginMember(Member member) throws Exception;

    public void chargeBalance(Member member) throws Exception;

}
