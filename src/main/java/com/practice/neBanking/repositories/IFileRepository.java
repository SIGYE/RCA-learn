package com.practice.neBanking.repositories;

import com.practice.neBanking.models.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IFileRepository extends JpaRepository<File, UUID> {
    File getFileByName(String fileName);
}
