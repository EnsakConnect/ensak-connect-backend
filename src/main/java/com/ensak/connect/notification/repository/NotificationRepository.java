package com.ensak.connect.notification.repository;

import com.ensak.connect.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
