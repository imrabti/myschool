package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.lov.ValueList;
import com.gsr.myschool.server.business.lov.ValueType;
import com.gsr.myschool.server.exception.ServiceException;

import java.util.List;

public interface ValueTypeService {
    void add(ValueType toPersist);

    ValueType findByName(String name);

    List<ValueType> findAll();

    void delete(ValueType toDelete) throws ServiceException;

    List<ValueType> getChildren(Long id);

    List<ValueList> getValues(Long id);
}
