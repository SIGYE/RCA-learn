package com.practice.neBanking.standalone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class FileStorageService {
    @Value("${uploads.directory}")
    private String root;

    @Value("${uploads.directory.customer_profiles}")
    private String userProfilesFolder;

    @Value("${uploads.directory.docs}")
    private String docsFolder;

    @Bean
    public void init() {

    }
}
