package com.example.cmd.domain.controller.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@NoArgsConstructor
@Getter
public class NotificationWriteRequest
{
    private String title;
    private String contents;
}
