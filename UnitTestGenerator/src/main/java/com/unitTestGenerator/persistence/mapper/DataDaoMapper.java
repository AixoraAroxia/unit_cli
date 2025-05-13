package com.unitTestGenerator.persistence.mapper;

import com.unitTestGenerator.ioc.anotations.Component;
import com.unitTestGenerator.ioc.anotations.Singleton;
import com.unitTestGenerator.persistence.model.Data;
import com.unitTestGenerator.persistence.model.DataPojo;
import com.unitTestGenerator.pojos.Project;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Component
@Singleton
public class DataDaoMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public DataDaoMapper() {
    }

    public List<Data> listPojoToListEntity(List<DataPojo> pojos) {
        return (pojos == null || pojos.isEmpty())
                ? Collections.emptyList()
                : pojos.stream().map(this::pojoToEntity).collect(Collectors.toList());
    }


    public List<DataPojo> listEntityToListPojo(List<Data> entities) {
        return (entities == null || entities.isEmpty())
                ? Collections.emptyList()
                : entities.stream().map(this::entityToPojo).collect(Collectors.toList());
    }


    public Data pojoToEntity(DataPojo pojo) {
        return Optional.ofNullable(pojo)
                .map(p -> modelMapper.map(p, Data.class))
                .orElse(null);
    }

    public DataPojo entityToPojo(Data entity) {
        return Optional.ofNullable(entity)
                .map(e -> modelMapper.map(e, DataPojo.class))
                .orElse(null);
    }


}
