package com.practice.neBanking.repositories;

import com.practice.neBanking.models.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMessageRepository extends JpaRepository<Message, UUID>{
    Page<Message> findAllByCustomerId(Pageable pageable, UUID customerId);
}
