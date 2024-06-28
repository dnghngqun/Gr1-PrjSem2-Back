package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Notification;
import com.t2307m.group1.prjsem2backend.repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class NotificationService {

    private final NotificationRepository NotificationRepository;

    @Autowired
    public NotificationService(com.t2307m.group1.prjsem2backend.repositories.NotificationRepository notificationRepository) {
        NotificationRepository = notificationRepository;
    }

    public List<Notification> getAllNotifications() { //lấy ra các thôgn báo
        return NotificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(int id) { //Lấy thông báo cụ thể theo ID của nó
        return NotificationRepository.findById(id);
    }

    @Transactional
    public Notification createNotification(Notification notification) { //Tạo một thông báo mới
        return NotificationRepository.save(notification);
    }

    @Transactional
    public Notification updateNotification(int id, Notification notificationUpdate) { //Cập nhật một thông báo hiện có
        Optional<Notification> NotificationOpt = NotificationRepository.findById(id);
        if (NotificationOpt.isEmpty()) {
            throw new RuntimeException("Notification not exist!");
        }

        Notification notification = NotificationOpt.get();
        notification.setMessage(notificationUpdate.getMessage());
        notification.setStatus(notificationUpdate.getStatus());
        return NotificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(int id) { //Xóa một thông báo theo ID của nó
        if (!NotificationRepository.existsById(id)) {
            throw new RuntimeException("Notification not exist!");
        }
        NotificationRepository.deleteById(id);
    }

}

