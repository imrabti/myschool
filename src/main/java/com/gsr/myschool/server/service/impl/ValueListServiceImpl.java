package com.gsr.myschool.server.service.impl;

import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.server.business.valuelist.ValueList;
import com.gsr.myschool.server.repos.ValueListRepos;
import com.gsr.myschool.server.service.ValueListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ValueListServiceImpl implements ValueListService {
    @Autowired
    private ValueListRepos valueListRepos;

    @Override
    public void addValueList(ValueList valueList) {
        valueListRepos.save(valueList);
    }

    @Override
    public void deleteValueList(Long id) {
        valueListRepos.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValueList> findAll() {
        return valueListRepos.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ValueList> findByValueTypeCode(ValueTypeCode valueTypeCode) {
        return valueListRepos.findByValueTypeCode(valueTypeCode);
    }
}
