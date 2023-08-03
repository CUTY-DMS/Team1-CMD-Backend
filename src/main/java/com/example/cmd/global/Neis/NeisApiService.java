package com.example.cmd.global.Neis;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NeisApiService {

    // neis api URL
    private static final String API_URL = "https://open.neis.go.kr/hub/hisTimetable";

    // API 호출 메서드
    public String callNeisApi() {
        // RestTemplate 객체 생성
        RestTemplate restTemplate = new RestTemplate();

        // neis api 요청에 필요한 파라미터 설정
        String atptOcdcScCode = "G10";
        String sdSchulCode = "7430310";
        String tiFromYmd = "20230703";
        String tiToYmd = "20230707";
        String type = "json";
        String key = "513aa74951a64b0793c9a0519e3e4bde";

        // neis api 호출 URL 생성
        String apiUrl = API_URL + "?ATPT_OFCDC_SC_CODE=" + atptOcdcScCode + "&SD_SCHUL_CODE=" + sdSchulCode
                + "&TI_FROM_YMD=" + tiFromYmd + "&TI_TO_YMD=" + tiToYmd + "&Type=" + type + "&KEY=" + key;

        // API 호출 및 응답 받기
        String response = restTemplate.getForObject(apiUrl, String.class);

        // 클라이언트로 보내기 위해 데이터 반환
        return response;
    }
}

