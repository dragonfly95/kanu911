package com.example.kanu911.controller;


import com.example.kanu911.auth.JwtPropertiesUtils;
import com.example.kanu911.auth.LoginMember;
import com.example.kanu911.service.MailDto;
import com.example.kanu911.service.MailUtils;
import com.example.kanu911.vo.MemberVO;
import com.example.kanu911.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {


    /**
     * LoginMyInterceptor에서 토큰없이 접속가능한 url을 정의합니다 
     */
    @Autowired
    private MailUtils mailUtils;

    /**
     * 로그인 회원의 토큰정보를 가져옵니다 
     * LoginMemberArgumentResolver.java에서 토큰값으로 회원정보를 생성하여
     * @LoginMember MemberVO memberVO 파라메타에 값을 전달합니다 
     * 
     * @param memberVO
     * @return
     */
    @RequestMapping(value = "/admin")
    public ResponseEntity<ResponseVO> main(@LoginMember MemberVO memberVO) {
        System.out.println("memberVO = " + memberVO);
        return new ResponseEntity<>(new ResponseVO("ok"), HttpStatus.OK);
    }


    /**
     * 로그인 페이지입니다 
     * 토큰이 없다면 로그인으로 forward 시킵니다 
     * @return
     */
    @GetMapping(value = "/admin/login")
    public ResponseEntity<ResponseVO> login() {
        return new ResponseEntity<>(new ResponseVO("login 필요합니다"), HttpStatus.OK);
    }


    /**
     * 로그인 처리합니다 
     * 토큰을 생성하여 json으로 전달합니다 
     * @return
     */
    @PostMapping(value = "/admin/login")
    public Map<String, String> loginProcess() {

        String userId = "test1";
        String userName = "kanu911";
        String userRole = "role_user";
        String token = JwtPropertiesUtils.create(userId, userName, userRole);
        Map<String, String> map = new HashMap();
        map.put("token", token);
        return map;
    }

    /**
     * 
     * 아래와 같이 포스트로 전달하면 
     * @RequestBody MemberVO memberVO 변수에 값이 담기게 됩니다 
     * {
     *     "email":"dbdyd@naver.com",
     *     "userName":"홍길동",
     *     "userId": "dbdyd",
     *     "password":"12#$"
     * }
     */
    @PostMapping(value = "/admin/memberJoin")
    public ResponseVO memberJoin(@RequestBody MemberVO memberVO) throws MessagingException {

        MailDto mailDto = new MailDto(memberVO.getEmail(), "회원가입 축하합니다","회원가입 축하내용 ~~");
        mailUtils.mailSend(mailDto);

        return new ResponseVO("회원가입 축하합니다");
    }
}
