package com.example.cmd.domain.controller.dto.request;


import com.example.cmd.domain.entity.Noti;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Getter
public class NotificationWriteRequest
{
    @NotNull(message = "제목을 입력하세요")
    private String title;

    @NotNull(message = "공지를 선택하세요")
    private Noti noti;

    @NotNull(message = "내용을 입력하세요")
    private String contents;
    private Long classes;
    private Long grade;
}
