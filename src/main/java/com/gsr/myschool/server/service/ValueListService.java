package com.gsr.myschool.server.service;

import com.gsr.myschool.common.shared.type.ValueTypeCode;
import com.gsr.myschool.server.business.valuelist.ValueList;

import java.util.List;

public interface ValueListService {
    void addValueList(ValueList valueList);

    void deleteValueList(Long id);

    List<ValueList> findAll();

    List<ValueList> findByValueTypeCode(ValueTypeCode valueTypeCode);
}
