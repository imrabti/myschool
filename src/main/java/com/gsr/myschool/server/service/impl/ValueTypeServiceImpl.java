package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.server.business.lov.ValueList;
import com.gsr.myschool.server.business.lov.ValueType;
import com.gsr.myschool.server.exception.ServiceException;
import com.gsr.myschool.server.repos.ValueTypeRepos;
import com.gsr.myschool.server.service.ValueTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValueTypeServiceImpl implements ValueTypeService {
    @Autowired
    private ValueTypeRepos valueTypeRepos;

    public void add(ValueType toPersist) {
        valueTypeRepos.save(toPersist);
    }

    @Override
    public ValueType findByName(String name) {
        return valueTypeRepos.findByName(name);
    }

    @Override
    public List<ValueType> findAll() {
        return valueTypeRepos.findAll();
    }

    @Override
    public void delete(ValueType toDelete) throws ServiceException {
        if (toDelete != null)
            if (toDelete.isSystem())
                throw new ServiceException();
            else
                valueTypeRepos.delete(toDelete);
        else
            throw new ServiceException();
    }

    @Override
    public List<ValueType> getChildren(Long id) {
        return valueTypeRepos.findByParentId(id);
    }

    @Override
    public List<ValueList> getValues(Long id) {
        ValueType defLov = valueTypeRepos.findOne(id);
        return new ArrayList<ValueList>();
    }
}
