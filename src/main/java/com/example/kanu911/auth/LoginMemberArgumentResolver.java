package com.example.kanu911.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.kanu911.vo.MemberVO;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(LoginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String userId = null;
        String userName = null;

        String authentication = webRequest.getHeader(JwtPropertiesUtils.HEADER_STRING);
        String[] token10 = authentication.split(JwtPropertiesUtils.TOKEN_PREFIX);
        String token = token10[1];
        try {

            DecodedJWT jwt = JwtPropertiesUtils.verify(token);

            System.out.println("jwt.getId() = " + jwt.getId());
            System.out.println("jwt.getSubject() = " + jwt.getSubject());
            userId = jwt.getClaim("userId").asString();
            userName = jwt.getClaim("userName").asString();

        } catch (JWTVerificationException verificationEx) {
            throw new RuntimeException("token:: 유효하지 않은 token 사용으로 로그인정보를 가져오지 못했습니다 ");
        }

        Optional.ofNullable(userId).orElseThrow(() -> new RuntimeException("로그인정보가 없습니다"));
        MemberVO memberVO = new MemberVO();
        memberVO.setUserId(userId);
        memberVO.setUserName(userName);

        return memberVO;
    }

}
