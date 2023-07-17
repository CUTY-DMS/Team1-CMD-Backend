package com.example.cmd.domain.repository;

import com.example.cmd.domain.entity.Admin;
import com.example.cmd.domain.entity.Noti;
import com.example.cmd.domain.entity.Notification;
import com.example.cmd.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification,Long> {

Optional<Notification> findByAdminAndDateTime(Admin admin, String dateTime);
Boolean existsByDateTime(String dateTime);
    List<Notification> findByNotiAndClassesAndGrade(Noti ClASS, Long classes, Long grade);
}
