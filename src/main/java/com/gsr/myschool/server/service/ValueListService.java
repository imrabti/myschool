package com.gsr.myschool.server.service;

import com.gsr.myschool.server.business.lov.ValueList;

import java.util.List;

public interface ValueListService {
    void add(ValueList valueList);

    ValueList find(Long id);

    void add(String value, Long parentId, Long defLovId);

    List<ValueList> findAll();

    List<ValueList> findByValueTypeName(String defLovName);

    ValueList findByValueAndValueTypeName(String value, String valueTypeName);

    public List<ValueList> findByValueTypeParentName(String valueTypeParentName);

    void delete(Long id);
}
