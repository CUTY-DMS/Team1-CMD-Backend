package com.example.cmd.domain.controller.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

    private String accessToken;

    private String key;

    //private String refreshToken;

}
