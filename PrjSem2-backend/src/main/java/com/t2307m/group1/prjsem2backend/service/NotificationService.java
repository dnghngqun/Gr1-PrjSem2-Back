package com.t2307m.group1.prjsem2backend.service;

import com.t2307m.group1.prjsem2backend.model.Account;
import com.t2307m.group1.prjsem2backend.model.Notification;
import com.t2307m.group1.prjsem2backend.repositories.AccountRepository;
import com.t2307m.group1.prjsem2backend.repositories.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class NotificationService {

    @Autowired
    private NotificationRepository NotificationRepository;

    @Autowired
    private AccountRepository accountRepository;

    public List<Notification> getAllNotifications() { //lấy ra các thôgn báo
        return NotificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(int id) { //Lấy thông báo cụ thể theo ID của nó
        return NotificationRepository.findById(id);
    }

    @Transactional
    public Notification createNotification(int accountId, String message, int status) { //Tạo một thông báo mới
        Optional<Account> accountOpt = accountRepository.findById(accountId);
        if (!accountOpt.isPresent()) {
            throw new RuntimeException("Tài khoản không tồn tại");
        }

        Account account = accountOpt.get();
        Timestamp dateSent = new Timestamp(System.currentTimeMillis());

        Notification notification = new Notification(account, message, dateSent, status);
        return NotificationRepository.save(notification);
    }

    @Transactional
    public Notification updateNotification(int id, String message, int status) { //Cập nhật một thông báo hiện có
        Optional<Notification> NotificationOpt = NotificationRepository.findById(id);
        if (!NotificationOpt.isPresent()) {
            throw new RuntimeException("Thông báo không tồn tại");
        }

        Notification notification = NotificationOpt.get();
        notification.setMessage(message);
        notification.setStatus(status);
        notification.setUpdateAt(new Timestamp(System.currentTimeMillis()));

        return NotificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(int id) { //Xóa một thông báo theo ID của nó
        if (!NotificationRepository.existsById((long) id)) {
            throw new RuntimeException("Thông báo không tồn tại");
        }

        NotificationRepository.deleteById((long) id);
    }



}

