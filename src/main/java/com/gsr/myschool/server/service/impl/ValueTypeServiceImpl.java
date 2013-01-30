package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.server.business.valuelist.ValueType;
import com.gsr.myschool.server.repos.ValueTypeRepos;
import com.gsr.myschool.server.service.ValueTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ValueTypeServiceImpl implements ValueTypeService {
    @Autowired
    private ValueTypeRepos valueTypeRepos;

    @Override
    public void updateValueType(ValueType valueType) {
        ValueType current = valueTypeRepos.findOne(valueType.getId());
        current.setParent(valueType.getParent());
        current.setRegex(valueType.getRegex());
        current.setSystem(valueType.getSystem());
        valueTypeRepos.save(current);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValueType> findAll() {
        return valueTypeRepos.findAll();
    }
}
