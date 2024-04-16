package com.example.toy.controller;



import com.example.toy.dto.Member;
import com.example.toy.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MemberController {
    @Autowired
    MemberService memberService;


    // --------- 로그인 !!! ---------
    @GetMapping(value="/login")
    public String login() {
        return "member/login";
    }

    @PostMapping(value="/login")
    public String loginMember(Member member, HttpSession session) { //로그인 처리
        //1. 사용자가 입력한 로그인 데이터터와 DB에 저장된 데이터가 맞는지 비교
        try {
            Member loginMember = memberService.loginMember(member);

            //2. 데이터가 일치하면 (로그인 성공 시) index 페이지로 이동
            if(loginMember != null) {
                // 로그인 성공 시, 로그인한 사람의 name과 member_no를 세션에 저장
                // .setAttribute(키, 값) -> 세션에 값을 저장
                session.setAttribute("name", loginMember.getName());
                session.setAttribute("member_no", loginMember.getMemberNo());
                session.setAttribute("balance", loginMember.getBalance());
                System.out.println(loginMember.getMemberNo());
                return "redirect:/";
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "member/login"; //3. 로그인 실패 시, login 페이지로 이동
    }

    // --------- 로그아웃 !!! ---------
    @GetMapping( value = "/logout" ) // localhost/logout
    public  String logoutMember(HttpSession session){
        // 세션에 저장된 name 과 member_no 삭제
        session.removeAttribute("name");
        session.removeAttribute("member_no");
        session.removeAttribute("balance");


        return "redirect:/"; // 로그아웃 성공시 index 페이지로 redirect
    }


    // --------- 잔액 !!! ---------
    @GetMapping(value = "/charge")
    public String chargeBalance2(Model model, HttpSession session) {
        int balance = (int) session.getAttribute("balance");
        model.addAttribute("balance", balance);
        return "member/charge";
    }

    @PostMapping(value = "/charge")
    public String chargeBalance(@RequestParam("charge") int charge, HttpSession session) {
        try {
            int memberNo = (int) session.getAttribute("member_no");
            Member member = new Member();
            member.setMemberNo(memberNo);
            member.setCharge(charge);
            memberService.chargeBalance(member);

            // 세션의 balance 업데이트
            int currentBalance = (int) session.getAttribute("balance");
            int updatedBalance = currentBalance + charge;
            session.setAttribute("balance", updatedBalance);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/charge"; // 리다이렉트하여 GET 요청으로 charge 페이지를 다시 요청
    }
}