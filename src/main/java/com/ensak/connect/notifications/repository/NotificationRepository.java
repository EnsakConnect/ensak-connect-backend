package com.ensak.connect.notifications.repository;

import com.ensak.connect.notifications.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
