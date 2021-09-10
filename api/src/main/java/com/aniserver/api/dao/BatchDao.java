package com.aniserver.api.dao;

import com.aniserver.api.dao.base.BaseDao;
import com.aniserver.api.model.Batch;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class BatchDao extends BaseDao {
    public List<Batch> getAllBatchList() throws IOException {
		return getSession().selectList("batch.getAllBatchList");
	}
}
