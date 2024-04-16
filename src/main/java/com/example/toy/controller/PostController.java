package com.example.toy.controller;


import com.example.toy.dto.Post;
import com.example.toy.service.PostService;
import com.example.toy.util.PagingUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PostController {

    @Autowired
    PostService postService;


    @GetMapping(value = "/") // localhost/로 접속
    public String index() {return "index"; }



    @GetMapping(value = "/write") // localhost/write
    public String write() {
        return "post/write";
    }

    @PostMapping(value = "/insert")
    public String insertPost(Post post, HttpSession session){
        try {

            // 1. 세션에서 사용자  member_id 가져오기
            Object memberNo = session.getAttribute("member_no");

            if (memberNo == null) {
                return "redirect:/login"; // 세션만료시 로그인 페이지로 이동
            } else {
                post.setMemberNo((int)memberNo); // insert 하기전 memberId 값 넣어줌
                postService.insertPost(post); // 2. 포스트에 insert 해주는 서비스 호출
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/list";
    }


}
