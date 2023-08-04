package com.example.cmd.global.Neis;

import com.example.cmd.domain.controller.dto.response.UserIdResonse;
import com.example.cmd.domain.entity.User;
import com.example.cmd.domain.repository.UserRepository;
import com.example.cmd.domain.service.exception.user.UserNotFoundException;
import com.example.cmd.domain.service.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NeisApiService {

    private final UserFacade userFacade;
    private final UserRepository userRepository;

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

    /*
    //시간표를 불러올 때 학생의 반에 맞는 시간표를 가져올 수 있게 학번만 반환하는 api
    public UserIdResonse getUserId() {
        User currentUser = userFacade.getCurrentUser();
        Optional<User> userList = userRepository.findByEmail(currentUser.getEmail());
        if (userList.isEmpty()) {
            throw UserNotFoundException.EXCEPTION;
        }//현재 유저의 아이디만 가져오게함
        return new UserIdResonse(currentUser.getClassId());
    }*/
}

