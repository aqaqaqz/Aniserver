package com.aniserver.api.service;

import com.aniserver.api.dao.BatchDao;
import com.aniserver.api.model.Batch;
import com.aniserver.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchService extends BaseService {
    @Autowired
    BatchDao batchDao;

    public List<Batch> getAllBatchList() throws Exception {
        return batchDao.getAllBatchList();
    }
}
