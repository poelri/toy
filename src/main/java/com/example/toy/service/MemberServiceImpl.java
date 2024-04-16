package com.example.toy.service;

import com.example.toy.dao.MemberDao;
import com.example.toy.dto.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberDao memberDao;


    // -- 로그인 --
    @Override
    public Member loginMember(Member member) throws Exception {
        return memberDao.loginMember(member);
    }

    // -- 잔액 --
    @Override
    public void chargeBalance(Member member) throws Exception {
        memberDao.chargeBalance(member);
    }
}


