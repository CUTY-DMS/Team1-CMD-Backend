package com.example.cmd.global.Neis;

import com.example.cmd.domain.controller.dto.response.UserIdResonse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NeisApiController {

    @Autowired
    private NeisApiService neisApiService;

    @GetMapping("/neisData")
    public String getNeisData() {
        // NeisApiService를 호출하여 데이터 받아오기
        String neisData = neisApiService.callNeisApi();

        // 클라이언트로 데이터 반환
        return neisData;
    }

    /*
    //학번만 반환하는 api
    @GetMapping("/classId")
    public UserIdResonse getUserId() {
        return neisApiService.getUserId();
    }*/
}
