package com.t2307m.group1.prjsem2backend.controllers;

import com.t2307m.group1.prjsem2backend.model.Notification;
import com.t2307m.group1.prjsem2backend.model.ResponseObject;
import com.t2307m.group1.prjsem2backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/")
    public ResponseEntity<ResponseObject> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        if (notifications.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "No notifications found", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query all notifications successfully", notifications)
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getNotificationById(@PathVariable int id) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        return notification.map(value -> ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("ok", "Query notification by ID successfully", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("Failed", "Notification not found", "")
                ));
    }

    @PostMapping("/")
    public ResponseEntity<ResponseObject> createNotification(@RequestBody Notification notification) {
        try {
            Notification newNotification = notificationService.createNotification(notification);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Create notification successfully!", newNotification)
            );
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Create Notification error!", "")
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateNotification(@PathVariable int id, @RequestBody Notification notificationUpdate) {
        try {
            Notification updatedNotification = notificationService.updateNotification(id, notificationUpdate);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update notification successfully!", updatedNotification)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Update Notification Error!", "")
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteNotification(@PathVariable int id) {
        try {
            notificationService.deleteNotification(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete notification successfully!", "")
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("Failed", "Delete notification error!", "")
            );
        }
    }
}
