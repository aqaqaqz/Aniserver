package com.aniserver.api.service;

import com.aniserver.api.model.Directory;
import com.aniserver.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryService extends BaseService {

    private List<Directory> directoryList = new ArrayList<>();

    @Value("${system.default.file_path}")
    private String defPath;

    @Value("${system.default.down_path}")
    private String downPath;
}
