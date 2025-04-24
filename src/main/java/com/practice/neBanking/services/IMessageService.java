package com.practice.neBanking.services;

import com.practice.neBanking.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IMessageService {
    Message getMessageById(UUID id);
    Page<Message> findAllMessages(Pageable pageable);
    Page<Message> findAllMessagesByCustomer(Pageable pageable, UUID customerId);
}
