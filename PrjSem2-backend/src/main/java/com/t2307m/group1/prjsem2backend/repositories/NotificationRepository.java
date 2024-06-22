package com.t2307m.group1.prjsem2backend.repositories;

import com.t2307m.group1.prjsem2backend.model.Account;
import com.t2307m.group1.prjsem2backend.model.Notification;
import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.security.Timestamp;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
//    Optional<Notification> findByAccount(Account account);
//    Optional<List<Notification>> findByMessage(String message);
//    Optional<List<Notification>> findByDateSent(Timestamp dateSent);
//    Optional<List<Notification>> findByStatus(int status);

    java.util.Optional<Notification> findById(int id);
}
