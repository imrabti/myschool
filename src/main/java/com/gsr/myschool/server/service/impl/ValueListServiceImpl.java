package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.server.business.lov.ValueList;
import com.gsr.myschool.server.business.lov.ValueType;
import com.gsr.myschool.server.repos.ValueListRepos;
import com.gsr.myschool.server.repos.ValueTypeRepos;
import com.gsr.myschool.server.service.ValueListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ValueListServiceImpl implements ValueListService {
    @Autowired
    private ValueTypeRepos valueTypeRepos;
    @Autowired
    private ValueListRepos valueListRepos;

    @Override
    public void add(ValueList valueList) {
        ValueType defLov = valueTypeRepos.findOne(valueList.getValueType().getId());
        valueList.setValueType(defLov);
        if (valueList.getParent() != null) {
            ValueList parent = valueListRepos.findOne(valueList.getParent().getId());
            valueList.setParent(parent);
        }
        valueListRepos.save(valueList);
    }

    @Override
    public ValueList find(Long id) {
        return valueListRepos.findOne(id);
    }

    @Override
    public void add(String value, Long parentId, Long defLovId) {
        ValueList valueList = new ValueList();
        valueList.setValue(value);
        valueList.setParent(valueListRepos.findOne(parentId));
        valueList.setValueType(valueTypeRepos.findOne(defLovId));

        valueListRepos.save(valueList);
    }

    @Override
    public List<ValueList> findAll() {
        return valueListRepos.findAll();
    }

    @Override
    public List<ValueList> findByValueTypeName(String defLovName) {
        return valueListRepos.findByValueType_Name(defLovName);
    }

    @Override
    public ValueList findByValueAndValueTypeName(String value, String valueTypeName) {
        return valueListRepos.findByValueAndValueType_Name(value, valueTypeName);
    }

    @Override
    public List<ValueList> findByValueTypeParentName(String valueTypeParentName) {
        ValueType defLov = valueTypeRepos.findByName(valueTypeParentName);
        ValueType parent = defLov.getParent();
        if (parent != null)
            return valueListRepos.findByValueType_Name(parent.getName());
        else
            return new ArrayList<ValueList>();
    }

    @Override
    public void delete(Long id) {
        valueListRepos.delete(id);
    }
}
