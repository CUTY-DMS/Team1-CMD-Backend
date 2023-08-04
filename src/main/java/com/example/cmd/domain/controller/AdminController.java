package com.example.cmd.domain.controller;

import com.example.cmd.domain.controller.dto.request.*;
import com.example.cmd.domain.controller.dto.response.*;
import com.example.cmd.domain.entity.Admin;
import com.example.cmd.domain.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("signup")
    public void signup(@RequestBody @Valid AdminSignupRequest adminSignupRequest) {
        adminService.adminSignup(adminSignupRequest);
    }

    @PostMapping("login")
    public TokenResponse login(@RequestBody @Valid LoginRequest loginRequest){
        return adminService.adminLogin(loginRequest);
    }

    @PostMapping("write")
    public void write(@RequestBody @Valid NotificationWriteRequest notificationWriteRequest) {
        adminService.write(notificationWriteRequest);
    }

    @DeleteMapping("delete")
    public void delete(@RequestBody NotificationDeleteRequest notificationDeleteRequest) {
        adminService.delete(notificationDeleteRequest);
    }

    @PatchMapping("fix")
    public void fix(@RequestBody NotificationFixRequest notificationFixRequest) {
        adminService.fix(notificationFixRequest);
    }

    @GetMapping("student/list")
    public List<UserListResponse> studentList(){
        return adminService.getStudentList();
    }

    @GetMapping("student/{userEmail}")
    public UserInfoResponse student(@PathVariable String userEmail){
        return adminService.student(userEmail);
    }

    @PatchMapping("infoChange")
    public void infoChange(@RequestBody AdminInfoChangeRequest adminInfoChangeRequest){
        adminService.adminInfoChange(adminInfoChangeRequest);
    }

    @GetMapping("myPage")
    public Admin myPage(){
        return adminService.adminInfo();
    }

    @PatchMapping("password/change")
    public void passwordChange(@RequestBody @Valid PasswordChangeRequest passwordChangeRequest){
        adminService.passwordChange(passwordChangeRequest);
    }

/*
    @GetMapping("/mailCheck")
    public String mailCheck(String email){
        System.out.println("이메일 인즈 요청");
        System.out.println("이메일 인증 이매일:"+email);
        adminService.
    }*/

    @GetMapping("/allNoti")
    public List<NotificationListResponse> findNotification() {
        return adminService.getNotification();
    }

    @GetMapping("/classNoti")
    public List<ClassNotificationListResponse> findClassNotification(@RequestBody @Valid ClassIdRequest classIdRequest) {
        return adminService.getClassNotification(classIdRequest);
    }

    @GetMapping("/teacherNoti")
    public List<NotificationListResponse> findAdminNotification() {
        return adminService.findAdminNotification();
    }

    @PostMapping("/schedule")
    public void writeSchedule(@RequestBody @Valid ScheduleWriteRequest scheduleWriteRequest) {
        adminService.writeSchedule(scheduleWriteRequest);
    }

    @DeleteMapping("/schedule/{scheduleId}")
    public void deleteSchedule(@PathVariable Long scheduleId) {
        adminService.deleteSchedule(scheduleId);
    }

    @GetMapping("/schedule")
    public List<ScheduleResponse> getSchedule(@RequestParam(name = "month") Long month){
        return adminService.getSchedule(month);
    }
}


