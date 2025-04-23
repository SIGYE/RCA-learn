package com.practice.neBanking.services;

import com.practice.neBanking.models.File;
import com.practice.neBanking.exceptions.InvalidFileException;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IFileService {
    File getById(UUID id);
    File create(MultipartFile document, String directory);
    boolean delete(UUID id);
    String getFileExtension(String fileName);
    String handleFileName(String fileName, UUID id) throws InvalidFileException;
    boolean isValidExtension(String fileName) throws InvalidFileException;
    File getByName(String fileName);

}
