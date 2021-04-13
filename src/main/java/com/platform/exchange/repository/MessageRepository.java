package com.platform.exchange.repository;

import com.platform.exchange.model.Message;
import com.platform.exchange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    List<Message> findAllByFromInAndToInOrderByDate(Collection<User> from, Collection<User> to);
}
