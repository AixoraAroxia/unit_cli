package com.unitTestGenerator.persistence.repositories;

import com.unitTestGenerator.persistence.model.Data;
import com.unitTestGenerator.persistence.model.DataPojo;

import java.util.List;

public interface IDaoService {

    public void update(DataPojo data);
    public void save(DataPojo data);
    public List<DataPojo> findAll();
    public DataPojo findById(Long id);
    public List<DataPojo> findById2(Long id);
    public List<DataPojo> findByName(String name);
    public void delete(DataPojo data);

}
