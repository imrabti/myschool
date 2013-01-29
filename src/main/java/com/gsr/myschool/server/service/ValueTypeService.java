package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.valuelist.ValueType;

import java.util.List;

public interface ValueTypeService {
    void updateValueType(ValueType valueType);

    List<ValueType> findAll();
}
