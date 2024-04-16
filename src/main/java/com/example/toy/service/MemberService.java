package com.example.toy.service;

import com.example.toy.dto.Member;

public interface MemberService {
    Member loginMember(Member member) throws Exception;

    public void chargeBalance(Member member) throws Exception;
}
