package com.practice.neBanking.serviceImpl;

import com.practice.neBanking.exceptions.ResourceNotFoundException;
import com.practice.neBanking.models.Customer;
import com.practice.neBanking.models.Message;
import com.practice.neBanking.repositories.IMessageRepository;
import com.practice.neBanking.services.IMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {
    private final IMessageRepository messageRepository;

    @Override
    public Message getMessageById(UUID id){
        return this.messageRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Message", "id", id.toString()));
    }
    @Override
    public Page<Message> findAllMessages(Pageable pageable){
        return this.messageRepository.findAll(pageable);
    }
    @Override
    public Page<Message> findAllMessagesByCustomer(Pageable pageable, UUID customerId){
        return this.messageRepository.findAllByCustomerId(pageable, customerId);
    }
}
