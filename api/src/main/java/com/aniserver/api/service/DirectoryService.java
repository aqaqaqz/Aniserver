package com.aniserver.api.service;

import com.aniserver.api.dao.DirectoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectoryService {
    @Autowired
    DirectoryDao directoryDao;
}
