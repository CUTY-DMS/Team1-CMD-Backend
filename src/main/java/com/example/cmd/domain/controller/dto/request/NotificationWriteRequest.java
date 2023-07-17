package com.example.cmd.domain.controller.dto.request;


import com.example.cmd.domain.entity.Noti;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NotificationWriteRequest
{
    private String title;
    private Noti noti;
    private String contents;

}
