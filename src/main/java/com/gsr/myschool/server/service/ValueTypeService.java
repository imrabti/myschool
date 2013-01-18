package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.lov.ValueList;
import com.gsr.myschool.server.business.lov.ValueType;
import com.gsr.myschool.server.exception.ServiceException;

import java.util.List;

public interface ValueTypeService {
    public void add(ValueType toPersist);

    public ValueType findByName(String name);

    public List<ValueType> findAll();

    public void delete(ValueType toDelete) throws ServiceException;

    public List<ValueType> getChildren(Long id);

    public List<ValueList> getValues(Long id);
}
