package com.aniserver.api.service;

import com.aniserver.api.dao.DirectoryDao;
import com.aniserver.api.exception.EmptyParamException;
import com.aniserver.api.model.db.Directory;
import com.aniserver.api.service.base.BaseService;
import com.aniserver.api.util.Const;
import com.aniserver.api.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DirectoryService extends BaseService {

    @Value("${system.default.file_path}")
    private String defPath;

    @Value("${system.default.down_path}")
    private String downPath;

    @Autowired
    DirectoryDao directoryDao;

}
