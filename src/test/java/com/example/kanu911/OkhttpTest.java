package com.example.kanu911;

import com.example.kanu911.vo.MemberVO;
import com.google.gson.Gson;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class OkhttpTest {

    @Test
    public void post_토큰생성() throws IOException {
        String url = "http://localhost:8080/admin/login";
        OkHttpClient client = new OkHttpClient();

        MemberVO memberVO = new MemberVO();
        memberVO.setUserId("111111");
        memberVO.setUserName("222222222");

        Gson gson = new Gson();
        String json = gson.toJson(memberVO);

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MediaType.parse("application/json"), json))
                .build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();

        System.out.println("result = " + result);
    }


    @Test
    public void get_토큰이용한_정보가져오기() throws IOException {

        String url = "http://localhost:8080/admin";
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0MSIsInJvbGUiOiJyb2xlX3VzZXIiLCJ1c2VyTmFtZSI6ImthbnU5MTEiLCJleHAiOjE2MDg1NjEzMzksInVzZXJJZCI6InRlc3QxIn0.KsWJtmYlhwqbWfnFVyQdjHgAZ5i8sPhU0kdax3ZylRNXRs2IB4L0Io7c-wo5J_yrpQQdsNIpV978k9JVZ_Z-EA";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().addHeader("Authorization", "Bearer " + token)
                .url(url).build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();

        System.out.println("result = " + result);

    }


}
