package com.example.toy.controller;


import com.example.toy.dto.Reservation;
import com.example.toy.service.ReservationService;
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
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    @Autowired
    PagingUtil pagingUtil;



    @GetMapping(value = "/view")
    public String view(HttpServletRequest request, Model model) {
        try {
            int reservationId = Integer.parseInt(request.getParameter("reservationId"));
            String pageNum = request.getParameter("pageNum");

            // 1. 게시물 데이터 가져오기
            Reservation reservation = reservationService.getReadReservation(reservationId);

            // 2. 가져온 게시물이 없다면
            if (reservation == null) {
                return "redirect:/list?pageNum=" + pageNum;
            }

            model.addAttribute("reservation", reservation);
            model.addAttribute("pageNum", pageNum);
        } catch (NumberFormatException e) {
            // 요청 파라미터가 잘못된 경우 처리
            e.printStackTrace();
            return "error"; // 에러 페이지로 리다이렉트 또는 에러 메시지를 반환할 수 있음
        } catch (Exception e) {
            // 그 외의 예외 처리
            e.printStackTrace();
            return "error"; // 에러 페이지로 리다이렉트 또는 에러 메시지를 반환할 수 있음
        }

        return "reservation/view";
    }


    // --------- 예약하기!!! ---------

    @GetMapping(value = "/reservation")
    public String reservation() {
        return "reservation/reservation";
    }

    // 예약 생성 요청 처리
    @PostMapping(value = "/createReservation")
    public String createReservation(Reservation reservation, HttpSession session) {
        try {
            Object memberNo = session.getAttribute("member_no");
            if (memberNo == null) {
                return "redirect:/reservation";
            } else {
                reservation.setMemberNo((int) memberNo);
                reservationService.createReservation(reservation);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/list";
    }

    @RequestMapping(value = "/list",
            method = {RequestMethod.GET, RequestMethod.POST})
    public String list(HttpSession session, HttpServletRequest request, Model model) {
        try {
            String pageNum = request.getParameter("pageNum");
            pagingUtil.setCurrentPage(1); // 페이지 번호 항상 1로 우선 초기화

            //현재 페이지의 값을 바꿔준다.
            if(pageNum != null) pagingUtil.setCurrentPage(Integer.parseInt(pageNum));

            int memberNo = (int) session.getAttribute("member_no");
//            String searchKey = request.getParameter("searchKey"); //검색키워드
//            String searchValue = request.getParameter("searchValue"); //검색어
//
//            if(searchValue == null) {
//                //검색어가 없다면
//                searchKey = "people"; //검색 키워드의 디폴트는 subejct
//                searchValue = ""; //검색어의 디폴트는 디폴트는 빈문자열
//            } else {
//                searchValue = URLDecoder.decode(searchValue,"UTF-8");
//            }


            Map map = new HashMap();
            map.put("memberNo", memberNo);
//            map.put("searchKey", searchKey);
//            map.put("searchValue",searchValue);

            //1. 전체 게시물의 갯수를 가져온다(페이징 처리시 필요)
            int dataCount = reservationService.getDataCount(map);

            //2. 페이징 처리를 한다(준비 단계)
            // numPerPage: 페이지당 보여줄 게시물 목록의 갯수
            pagingUtil.resetPaging(dataCount, 5);

            map.put("start", pagingUtil.getStart()); // 1 6 11 16..
            map.put("end", pagingUtil.getEnd()); // 5 10 15 20..

            //3. 페이징 처리할 리스트를 가지고 온다.
            List<Reservation> lists = reservationService.getReservationList(map);

            String listUrl = "/list";

            String articleUrl = "/view?pageNum=" + pagingUtil.getCurrentPage();


            //5. 페이징 버튼을 만들어준다.
            //◀이전 1 2 3 4 5 다음▶
            String pageIndexList = pagingUtil.pageIndexList(listUrl);

            model.addAttribute("lists", lists); //DB에서 가져온 전체 게시물리스트
            model.addAttribute("articleUrl", articleUrl); //상세페이지로 이동하기 위한 url
            model.addAttribute("pageIndexList", pageIndexList); //페이징 버튼
            model.addAttribute("dataCount", dataCount); //게시물의 전체 개수
//            model.addAttribute("searchKey", searchKey); //검색키워드
//            model.addAttribute("searchValue", searchValue); //검색어

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "reservation/list";
    }


    @GetMapping(value = "/rewrite") // localhost/rewrite
    public String rewrite(HttpServletRequest request, Model model) {
        try {

            int reservationId = Integer.parseInt(request.getParameter("reservationId"));
            String pageNum = request.getParameter("pageNum");
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");

            // 게시물 데이터 가져오기
            Reservation reservation = reservationService.getReadReservation(reservationId);

            // 게시물이 없으면 list 페이지로 이동
            if(reservation == null) return "redirect:/list?pageNum=" + pageNum;

            String param = "pageNum="+pageNum;
//
//            if(searchValue != null && !searchValue.equals("")) {
//                searchValue = URLDecoder.decode(searchValue, "UTF-8");
//                //검색어가 있다면
//                param += "&searchKey=" + searchKey;
//                param += "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8"); //컴퓨터의 언어로 인코딩
//            }

            model.addAttribute("reservation",reservation);
            model.addAttribute("params",param);
            model.addAttribute("pageNum",pageNum);
            model.addAttribute("searchKey",searchKey);
            model.addAttribute("searchValue",searchValue);



        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "reservation/rewrite";
    }


    @DeleteMapping(value = "/delete/{reservationId}")
    public @ResponseBody ResponseEntity deleteReservation(@PathVariable("reservationId") int reservationId, HttpSession session) {
        try {
            reservationService.deleteReservation(reservationId);

            Object memberNo = session.getAttribute("member_no");

            if (memberNo == null) {
                return new ResponseEntity<String>("삭제권한이 없습니다.", HttpStatus.UNAUTHORIZED);
            } else {
                reservationService.deleteReservation(reservationId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("삭제실패. 관리자에게 문의하세요.", HttpStatus.BAD_REQUEST);
        }

        // ResponseEntity<첫번째 매개변수의 타입>(result결과, response상태코드)
        // HttpStatus.OK일때는 ajax의 success함수로 결과가 출력됩니다.
        return new ResponseEntity<Integer>(reservationId, HttpStatus.OK);
    }
}
